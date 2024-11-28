package com.church.ChurchApplication.entity;

import jakarta.persistence.*;

@Entity
public class FavoriteVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ulogin_id", nullable = false)
    private Ulogin ulogin;

    @ManyToOne
    @JoinColumn(name = "videoStorage_id", nullable = false)
    private VideoStorage videoStorage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ulogin getUlogin() {
        return ulogin;
    }

    public void setUlogin(Ulogin ulogin) {
        this.ulogin = ulogin;
    }

    public VideoStorage getVideoStorage() {
        return videoStorage;
    }

    public void setVideoStorage(VideoStorage videoStorage) {
        this.videoStorage = videoStorage;
    }

    public FavoriteVideo() {
    }
}
