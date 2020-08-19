package com.upxa.app.domain.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;

@Document(collection = "File")
public class File {
    @Id
    private String id;
    private String name;
    private URL downloadUrl;

    public File(String id, String name, URL downloadUrl) {
        this.id = id;
        this.name = name;
        this.downloadUrl = downloadUrl;
    }

    public File() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(URL downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", downloadUrl=" + downloadUrl +
                '}';
    }
}
