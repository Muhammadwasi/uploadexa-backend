package com.upxa.app.thirdparty;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.upxa.app.rest.api.BucketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

@Component
public class AmazonClient {
    private AmazonS3 amazonS3Client;
    Logger logger = LoggerFactory.getLogger(AmazonClient.class);

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.region}")
    private String region;

    @PostConstruct
    private void initializeAmazonClient(){
        this.amazonS3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(this.region)
                .build();
    }

    public URL uploadFileInParts(MultipartFile multipartFile,String objectId) throws IOException {
        URL url=null;
        try{


            byte[] bytes = new byte[BUFFER_SIZE];
            String uploadId = this.amazonS3Client.initiateMultipartUpload(
                    new InitiateMultipartUploadRequest(
                            this.bucketName,
                            objectId
                    )).getUploadId();

            InputStream inputStream = multipartFile.getInputStream();
            int bytesRead = 0;
            int partNumber = 1;
            List<UploadPartResult> results = new ArrayList<>();
            bytesRead = inputStream.read(bytes);
            while (bytesRead >= 0) {
                logger.info("Transferring "+ bytesRead+ "bytes...");
                UploadPartRequest part = new UploadPartRequest()
                        .withBucketName(this.bucketName)
                        .withKey(objectId)
                        .withUploadId(uploadId)
                        .withPartNumber(partNumber)
                        .withInputStream(new ByteArrayInputStream(bytes, 0, bytesRead))
                        .withPartSize(bytesRead);
                results.add(this.amazonS3Client.uploadPart(part));
                bytesRead = inputStream.read(bytes);
                partNumber++;
            }
            CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest()
                    .withBucketName(this.bucketName)
                    .withKey(objectId)
                    .withUploadId(uploadId)
                    .withPartETags(results);
            this.amazonS3Client.completeMultipartUpload(completeRequest);

            logger.info("Object upload complete");


            // Generate the presigned URL.
            logger.info("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectId)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(this.getExpirationTime());;
            url = this.amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            logger.info("Pre-Signed URL: " + url.toString());

        }catch (AmazonServiceException  amazonServiceException){
            logger.error(String.format("Exception occured at occured %s", AmazonClient.class));
            amazonServiceException.printStackTrace();
        }
        return url;
    }

    private Date getExpirationTime(){
        // Set the presigned URL to expire after 7 days.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 24 * 7;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
    public URL uploadFile(MultipartFile multipartFile,String objectId) throws IOException {
        URL url=null;
        try{
            if(multipartFile.getSize()/1024/1024 > 100){
                return url;
            }
            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(this.amazonS3Client)
                    .build();

            // TransferManager processes all transfers asynchronously,
            // so this call returns immediately.

            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(bytes.length);
            objectMetadata.setContentDisposition("attachment; filename=" + multipartFile.getOriginalFilename());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            Upload upload=tm.upload(
                    this.bucketName,
                    objectId,
                    byteArrayInputStream,
                    objectMetadata);
            System.out.println("Object upload started");

            // Optionally, wait for the upload to finish before continuing.
            upload.waitForCompletion();
            System.out.println("Object upload complete");

            // Generate the presigned URL.
           // ResponseHeaderOverrides responseHeaderOverrides= new ResponseHeaderOverrides().setContentDisposition("Content-Disposition - attachment; filename=" + multipartFile.getOriginalFilename())

            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectId)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(this.getExpirationTime());
            url = this.amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            System.out.println("Pre-Signed URL: " + url.toString());



        }catch (AmazonServiceException | InterruptedException amazonServiceException){
            amazonServiceException.printStackTrace();
        }
        return url;
    }

    private File convertMultipartFiletoFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream=new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return convertedFile;
    }
}
