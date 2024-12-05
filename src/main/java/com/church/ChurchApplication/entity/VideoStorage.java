package com.church.ChurchApplication.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

@Entity
public class VideoStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String videoName;
    @Lob
    @Column(columnDefinition = "Text")
    private String videoDescription;
    private String url;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //for Storing video in db

//    private String videoName;
//    private String videoType;
//
//    @Lob
//    private byte[] videoData;

//    public String getVideoName() {
//        return videoName;
//    }
//
//    public void setVideoName(String videoName) {
//        this.videoName = videoName;
//    }
//
//    public String getVideoType() {
//        return videoType;
//    }
//
//    public void setVideoType(String videoType) {
//        this.videoType = videoType;
//    }
//
//    public byte[] getVideoData() {
//        return videoData;
//    }
//
//    public void setVideoData(byte[] videoData) {
//        this.videoData = videoData;
//    }

    public VideoStorage() {
    }
}
