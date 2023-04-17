package com.topawar.manage.common.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import com.tencent.cloud.cos.util.MD5;
import com.topawar.manage.exception.GlobalException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: YJ
 * @date: 2023/04/10 9:36
 */
@Slf4j
@Component
public class CosUtil {

    @Resource
    RedisUtil redisUtil;

    private static final String clientName = "https://seed-1306904200.cos.ap-shanghai.myqcloud.com/";

    private static final int durationSeconds = 7200;

    public void setTempSecretKeyAndSecretIdAndSessionToken() {
        TreeMap<String, Object> config = new TreeMap<>();
        try {
//            String secretId = System.getenv("secretId");
//            String secretKey = System.getenv("secretKey");
            String secretId = (String) redisUtil.get("cos:secretId");
            String secretKey = (String) redisUtil.get("cos:secretKey");
            config.put("secretId", secretId);
            config.put("secretKey", secretKey);
            config.put("durationSeconds", durationSeconds);
            config.put("bucket", "seed-1306904200");
            config.put("region", "ap-shanghai");
            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径
            // 列举几种典型的前缀授权场景：
            // 1、允许访问所有对象："*"
            // 2、允许访问指定的对象："a/a1.txt", "b/b1.txt"
            // 3、允许访问指定前缀的对象："a*", "a/*", "b/*"
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefixes", new String[]{
                    "*",
            });
            String[] allowActions = new String[]{
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分块上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload",
                    "name/cos:HeadObject"
            };
            config.put("allowActions", allowActions);
            Response response = CosStsClient.getCredential(config);
            redisUtil.set("cos:tmp_secretId", response.credentials.tmpSecretId, durationSeconds, TimeUnit.SECONDS);
            redisUtil.set("cos:tmp_secretKey", response.credentials.tmpSecretKey, durationSeconds, TimeUnit.SECONDS);
            redisUtil.set("cos:sessionToken", response.credentials.sessionToken, durationSeconds, TimeUnit.SECONDS);
            redisUtil.set("cos:bucket", "seed-1306904200", 0, TimeUnit.SECONDS);
            redisUtil.set("cos:region", "ap-shanghai", 0, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    public COSClient createCosClient() {
        if (!redisUtil.hasKey("cos:tmp_secretId") || !redisUtil.hasKey("cos:tmp_secretKey")) {
            setTempSecretKeyAndSecretIdAndSessionToken();
        }
        // 用户基本信息
        String tmpSecretId = (String) redisUtil.get("cos:tmp_secretId");   // 替换为 STS 接口返回给您的临时 SecretId
        String tmpSecretKey = (String) redisUtil.get("cos:tmp_secretKey");  // 替换为 STS 接口返回给您的临时 SecretKey
        String tmpSessionToken = (String) redisUtil.get("cos:sessionToken");  // 替换为 STS 接口返回给您的临时 SecretKey
        String region = (String) redisUtil.get("cos:region");

        // 1 初始化用户身份信息(secretId, secretKey)
//        COSCredentials cred = new BasicCOSCredentials(tmpSecretId, tmpSecretKey,tmpSessionToken);
        COSCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, tmpSessionToken);
        // 2 设置 bucket 区域,详情请参见 COS 地域 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成 cos 客户端
        return new COSClient(cred, clientConfig);
    }

    public String putCosObjectFile(MultipartFile multipartFile) {
        COSClient cosClient = createCosClient();
        String[] fileSuffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.");
        String putSuccessFileKey = null;
        try {
            byte[] fileBytes = multipartFile.getBytes();
            ByteArrayInputStream copyStream1 = new ByteArrayInputStream(fileBytes);
            String key = MD5.streamToMD5(copyStream1); //通过读取流获取md5
            putSuccessFileKey = key.concat(".").concat(fileSuffix[1]);
            String cloudFileUrl = fileIsExist(cosClient, putSuccessFileKey);
            if (StringUtils.isNotEmpty(cloudFileUrl)) {
                return cloudFileUrl;
            }
            ByteArrayInputStream copyStream2 = new ByteArrayInputStream(fileBytes);
            int fileLength = copyStream2.available();
            // 上传 object, 建议 20M 以下的文件使用该接口
            // 设置 x-cos-security-token header 字段
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setSecurityToken((String) redisUtil.get("cos:sessionToken"));
            objectMetadata.setContentLength(fileLength);
            PutObjectRequest putObjectRequest = new PutObjectRequest((String) redisUtil.get("cos:bucket"), putSuccessFileKey, copyStream2, objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // 成功：putobjectResult 会返回文件的 etag
            String etag = putObjectResult.getETag();
        } catch (CosServiceException e) {
            // 如果redis中存放的临时信息过期重新获取并创建连接
            log.error(e.getMessage());
            log.info("Regenerates the tmp_secret information");
            setTempSecretKeyAndSecretIdAndSessionToken();
            putCosObjectFile(multipartFile);
        } catch (CosClientException e) {
            //失败，抛出 CosClientException
            throw new GlobalException(e.getMessage());
        } catch (IOException e) {
            throw new GlobalException(e.getMessage());
        } finally {
            cosClient.shutdown();
        }
        assert putSuccessFileKey != null;
        return clientName + putSuccessFileKey;
    }


    public String fileIsExist(COSClient cosClient, String putSuccessFileKey) {
        try {
            boolean objectExists = cosClient.doesObjectExist((String) redisUtil.get("cos:bucket"), putSuccessFileKey);
            if (objectExists) {
                return clientName + putSuccessFileKey;
            }
        } catch (CosClientException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}

