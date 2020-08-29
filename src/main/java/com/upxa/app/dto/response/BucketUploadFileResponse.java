package com.upxa.app.dto.response;

import com.upxa.app.domain.entity.File;

import java.net.URL;

public class BucketUploadFileResponse extends Response {
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "BucketUploadFileResponse{" +
                "file=" + file +
                '}';
    }
}
