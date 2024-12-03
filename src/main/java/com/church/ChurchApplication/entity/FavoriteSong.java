package com.church.ChurchApplication.entity;

import jakarta.persistence.*;

@Entity
public class FavoriteSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ulogin_id", nullable = false)
    private Ulogin ulogin;

    @ManyToOne
    @JoinColumn(name = "songs_id", nullable = false)
    private Songs songs;

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

    public Songs getSongs() {
        return songs;
    }

    public void setSongs(Songs songs) {
        this.songs = songs;
    }
}
