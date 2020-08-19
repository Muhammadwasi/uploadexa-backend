package com.upxa.app.service;

import com.upxa.app.domain.entity.File;
import com.upxa.app.domain.entity.repository.IFileRepository;
import com.upxa.app.dto.response.BucketUploadFileResponse;
import com.upxa.app.thirdparty.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Component
public class BucketService {
    private AmazonClient amazonClient;
    @Autowired
    IFileRepository iFileRepository;
    @Autowired
    public BucketService(AmazonClient amazonClient) {
        this.amazonClient=amazonClient;
    }

    public BucketUploadFileResponse uploadFile(MultipartFile multipartFile) throws IOException {
        BucketUploadFileResponse bucketUploadFileResponse=new BucketUploadFileResponse();
        String objectId = new Date().getTime()+"-"+multipartFile.getOriginalFilename().replace(" ","_");

        URL url =this.amazonClient.uploadFile(multipartFile,objectId);
        if (url!=null){
            File fileDomain = new File(objectId,multipartFile.getOriginalFilename(),url);
            iFileRepository.save(fileDomain);
            bucketUploadFileResponse.setFile(fileDomain);
            System.out.println(fileDomain);
            System.out.println(bucketUploadFileResponse);
        }


        return bucketUploadFileResponse;
    }
}