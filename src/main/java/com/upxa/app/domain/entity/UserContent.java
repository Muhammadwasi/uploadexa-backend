package com.upxa.app.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;


@Document(collection = "UserContent")
public class UserContent {
    @Id
    private String contentId;
    private List<File> files;


    @Override
    public String toString() {
        return "UserContent{" +
                "id='" + contentId + '\'' +
                ", files=" + files +
                '}';
    }


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
}
