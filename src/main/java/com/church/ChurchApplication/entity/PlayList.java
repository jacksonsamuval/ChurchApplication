package com.church.ChurchApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "ulogin_id",nullable = false)
    @JsonIgnore
    private Ulogin ulogin;

    @ElementCollection
    private List<VideoStorage> videoStorageList = new ArrayList<>();

    @ElementCollection
    private List<Songs> songsList =new ArrayList<>();

    public List<Songs> getSongsList() {
        return songsList;
    }

    public void setSongsList(List<Songs> songsList) {
        this.songsList = songsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ulogin getUlogin() {
        return ulogin;
    }

    public void setUlogin(Ulogin ulogin) {
        this.ulogin = ulogin;
    }

    public List<VideoStorage> getVideoStorageList() {
        return videoStorageList;
    }

    public void setVideoStorageList(List<VideoStorage> videoStorageList) {
        this.videoStorageList = videoStorageList;
    }

    public PlayList() {
    }
}
