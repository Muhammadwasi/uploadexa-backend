package com.upxa.app.dto;

import com.upxa.app.domain.entity.File;

import java.util.List;

public class UserContentDTO {
    private String contentId;
    private List<File> files;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "UserContentDTO{" +
                "contentId='" + contentId + '\'' +
                ", files=" + files +
                '}';
    }
}
