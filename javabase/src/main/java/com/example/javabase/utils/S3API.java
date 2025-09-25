package com.example.javabase.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class S3API {
    private static final String ACCESS_KEY = "U86VRLS7ALA39W34H199";
    private static final String SECRET_KEY = "1mYD4AwZQNVDzr5l5TCNAsTFhrLofZ20oNXVsI8I";
    private static final String ENDPOINT = "http://tcstore1.17usoft.com";
    //按需调整
    public static final long PART_SIZE = 5 * 1024 * 1024l;
    public static final String UPLOAD_PATH = "你本地文件所在目录";

    private static AmazonS3Client s3client = null;
    private static S3API instance = new S3API();

    public static void main(String[] args) {
        S3API s3API = new S3API();
        s3API.init();
        s3API.listObj("hotelvideo");
    }
    @PostConstruct
    public void init() {
        s3client = initClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
    }
    //你的业务逻辑调用S3API s3api = S3API.getInstance();
    public  S3API getInstance(){
        return this.instance;
    } 

    /**
     * 初始化客户端 withPathStyleAccess(true) bucket是请求参数而非域名前缀（bucket.域名）
     * ClientConfiguration中可以自定义超时时间等
     * * @param endPoint
     *
     * @param accessKey
     * @param secretKey
     * @return
     */
    private static AmazonS3Client initClient(String endPoint, String accessKey, String secretKey) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientCfg = new ClientConfiguration();
        clientCfg.setProtocol(endPoint.startsWith(Protocol.HTTPS.toString()) ? Protocol.HTTPS : Protocol.HTTP);
        S3ClientOptions s3ClientOptions = new S3ClientOptions().withPathStyleAccess(true);
        AmazonS3Client s3client = new AmazonS3Client(credentials, clientCfg);
        s3client.setEndpoint(endPoint);
        s3client.setS3ClientOptions(s3ClientOptions);
        return s3client;
    }

    /**
     * 根据bucket查询这个bucket根目录下的文件和拥有的子文件夹
      申请时需要勾选list 权限才可
     */
    public void listObj(String bucket) {
        ListObjectsRequest request = new ListObjectsRequest().withBucketName(bucket).withDelimiter("/").withMaxKeys(10);
        int count = 0;
        ObjectListing objects = s3client.listObjects(request);
        do {
	        for (String prefix : objects.getCommonPrefixes()) {
		        System.out.println((++count) + "    prefix:" + prefix);
	        }
            for (S3ObjectSummary summary : objects.getObjectSummaries()) {
                System.out.println((++count) + "    " + summary.getKey() + "\t" + summary.getSize() + "\t");
            }
            objects = s3client.listNextBatchOfObjects(objects);
            System.out.println("Next batch ......");
        } while (objects.isTruncated());
        for (S3ObjectSummary summary : objects.getObjectSummaries()) {
            System.out.println((++count) + "    " + summary.getKey() + "\t" + summary.getSize() + "\t");
        }
    }

    /**
     * 根据bucket和路径名查询该路径下所有的文件和拥有的子文件夹
     */
    public void listObj(String bucket, String prefix) {
        ListObjectsRequest request = new ListObjectsRequest().withBucketName(bucket).withDelimiter("/")
                .withPrefix(prefix).withMaxKeys(10);
        int count = 0;
        ObjectListing objects = s3client.listObjects(request);
        do {

	        for (String tmpPrefix : objects.getCommonPrefixes()) {
		        System.out.println((++count) + "    prefix:" + tmpPrefix);
	        }
            for (S3ObjectSummary summary : objects.getObjectSummaries()) {
                System.out.println((++count) + "    " + summary.getKey() + "\t" + summary.getSize() + "\t");
            }
            objects = s3client.listNextBatchOfObjects(objects);
            System.out.println("Next batch ......");
        } while (objects.isTruncated());
        for (S3ObjectSummary summary : objects.getObjectSummaries()) {
            System.out.println((++count) + "    " + summary.getKey() + "\t" + summary.getSize() + "\t");
        }
    }

    /**
     * 删除指定文件
     *
     * @param bucket
     * @param keysData ：指定文件名,如下面:
     *                 ["wlltest/123.jpg","wlltest/test/123.jpg","wlltest/test2/123.jpg"]
     */
    public void deleteData(String bucket, List<String> keysData) {
        if (!s3client.doesBucketExist(bucket)) {
            System.out.println("Bucket:" + bucket + " doesn't exist!");
            return;
        }
        long start = System.currentTimeMillis();
        for (String key : keysData) {
            if (s3client.doesObjectExist(bucket, key)) {
                s3client.deleteObject(bucket, key);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("删除完毕,UseTime:" + (end - start));
    }

    /**
     * 私有读bucket下，本地文件上传，默认为私有读。设置带有过期时间的url使用
     */
    public String getPrivateURByUploadFile(String bucket, String key, File localFile) {
        try {
            PutObjectResult putObjectResult = s3client.putObject(bucket, key, localFile);
            return getPrivateURL(bucket, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私有读bucket下，流上传，默认为私有读。设置带有过期时间的url使用
     */
    public String getPrivateURLByUploadStream(String bucket, String key, InputStream inputStream) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(inputStream.available());
            PutObjectResult putObjectResult = s3client.putObject(bucket, key, inputStream, meta);
            return getPrivateURL(bucket, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私有对象，设置带有过期时间的url使用
     *
     * @param bucket
     * @param key
     * @return
     * @throws ParseException
     */
    private String getPrivateURL(String bucket, String key) throws ParseException {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key);
        Date expirationDate = null;
        expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-12");
        request.setExpiration(expirationDate);
        URL url = s3client.generatePresignedUrl(request);
        return url == null ? "Null URL" : url.toString();
    }

    /**
     * 公有读bucket下，本地文件上传，默认为公有读。
     */
    public String getPublicURLByUploadFile(String publicBucket, String key, File localFile) {
        try {
            PutObjectResult putObjectResult = s3client.putObject(publicBucket, key, localFile);
            return s3client.getUrl(publicBucket, key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公有读bucket下，流上传，默认为公有读。
     */
    public String getPublicURLByUploadStream(String publicBucket, String key, InputStream inputStream) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(inputStream.available());
            PutObjectResult putObjectResult = s3client.putObject(publicBucket, key, inputStream, meta);
            return s3client.getUrl(publicBucket, key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 针对大文件，如5MB以上。低级别分段上传-简单
     *
     * @param bucket
     * @param key
     * @param fileName
     * @return
     */
    private CompleteMultipartUploadResult multiUpload(String bucket, String key, String fileName) {
        String filePath = UPLOAD_PATH + "/" + fileName;
        InitiateMultipartUploadResult initResponse = s3client.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucket, key));
        String uploadId = initResponse.getUploadId();
        // Create a list of ETag objects. You retrieve ETags for each object part uploaded,
        // then, after each individual part has been uploaded, pass the list of ETags to
        // the request to complete the upload.
        List<PartETag> partETags = new ArrayList<PartETag>();
        File file = new File(filePath);
        long contentLength = file.length();
        long partSize = PART_SIZE;
        // Initiate the multipart upload.
        // Upload the file parts.
        long filePosition = 0;
        for (int i = 1; filePosition < contentLength; i++) {
            partSize = Math.min(partSize, (contentLength - filePosition));
            UploadPartRequest uploadRequest = new UploadPartRequest()
                    .withBucketName(bucket)
                    .withKey(key)
                    .withUploadId(initResponse.getUploadId())
                    .withPartNumber(i)
                    .withFileOffset(filePosition)
                    .withFile(file)
                    .withPartSize(partSize);

            UploadPartResult uploadResult = s3client.uploadPart(uploadRequest);
            partETags.add(uploadResult.getPartETag());
            filePosition += partSize;

        }
        CompleteMultipartUploadResult result = s3client.completeMultipartUpload(
                new CompleteMultipartUploadRequest(bucket, key, uploadId, partETags));

        return result;
    }

    /**
     * 获取对象
     *
     * @param bucket
     * @param key
     * @return
     */
    public S3Object getObject(String bucket, String key) {
        return s3client.getObject(bucket, key);
    }

}
