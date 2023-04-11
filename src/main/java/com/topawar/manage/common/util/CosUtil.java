package com.topawar.manage.common.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import com.topawar.manage.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: YJ
 * @date: 2023/04/10 9:36
 */
public class CosUtil {

    private static String bucketName;
    private static String sessionToken;
    private static String clientName="https://seed-1306904200.cos.ap-shanghai.myqcloud.com/";

    public static Map<String, String> getTempSecretKeyAndSecretIdAndSessionToken() {
        TreeMap<String, Object> config = new TreeMap<>();
        HashMap<String, String> tempSecretResultMap = new HashMap<>();
        try {
            String secretId = System.getenv("secretId");
            String secretKey = System.getenv("secretKey");
            config.put("secretId", secretId);
            config.put("secretKey", secretKey);
            config.put("durationSeconds", 1800);
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
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);
            Response response = CosStsClient.getCredential(config);
            tempSecretResultMap.put("secretId", response.credentials.tmpSecretId);
            tempSecretResultMap.put("secretKey", response.credentials.tmpSecretKey);
            tempSecretResultMap.put("sessionToken", response.credentials.sessionToken);
            tempSecretResultMap.put("bucket", "seed-1306904200");
            tempSecretResultMap.put("region", "ap-shanghai");
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return tempSecretResultMap;
    }

    public static COSClient createCosClient() {
        Map<String, String> secretKeyAndSecretIdAndSessionToken = getTempSecretKeyAndSecretIdAndSessionToken();
        // 用户基本信息
        String tmpSecretId = secretKeyAndSecretIdAndSessionToken.get("secretId");   // 替换为 STS 接口返回给您的临时 SecretId
        String tmpSecretKey = secretKeyAndSecretIdAndSessionToken.get("secretKey");  // 替换为 STS 接口返回给您的临时 SecretKey
        String region = secretKeyAndSecretIdAndSessionToken.get("region");
        bucketName = secretKeyAndSecretIdAndSessionToken.get("bucket");
        sessionToken = secretKeyAndSecretIdAndSessionToken.get("sessionToken");  // 替换为 STS 接口返回给您的临时 Token

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(tmpSecretId, tmpSecretKey);
        // 2 设置 bucket 区域,详情请参见 COS 地域 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成 cos 客户端
        return new COSClient(cred, clientConfig);
    }

    public static String PutCosObjectFile(MultipartFile multipartFile) {
        COSClient cosClient = createCosClient();
        Map<String, String> secretKeyAndSecretIdAndSessionToken = getTempSecretKeyAndSecretIdAndSessionToken();
        String originalFilename = multipartFile.getOriginalFilename();
        try {
            InputStream fileInputStream = multipartFile.getInputStream();
            int fileLength = fileInputStream.available();
            // 上传 object, 建议 20M 以下的文件使用该接口
            // 设置 x-cos-security-token header 字段
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setSecurityToken(sessionToken);
            objectMetadata.setContentLength(fileLength);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, originalFilename, fileInputStream,objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // 成功：putobjectResult 会返回文件的 etag
            String etag = putObjectResult.getETag();
        } catch (CosServiceException e) {
            //失败，抛出 CosServiceException
           throw new GlobalException(e.getMessage());
        } catch (CosClientException e) {
            //失败，抛出 CosClientException
            throw new GlobalException(e.getMessage());
        }catch (IOException e) {
            throw new GlobalException(e.getMessage());
        }
        finally {
            cosClient.shutdown();
        }
        return clientName+originalFilename;
    }

}

