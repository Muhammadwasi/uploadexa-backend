package com.upxa.app.rest.api;

import com.upxa.app.dto.response.Response;
import com.upxa.app.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("v1/storage")
public class BucketController {
    private BucketService bucketService;

    @Autowired
    public  BucketController(BucketService service){
        this.bucketService =service;
    }

    @RequestMapping(value = "/uploadFile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.POST}
            )
    public Response uploadFile(@RequestPart(value="file") MultipartFile file) throws IOException {
        Response uploadFileResponse= bucketService.uploadFile(file);
        return uploadFileResponse;
    }


}
