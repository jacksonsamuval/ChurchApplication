package com.church.ChurchApplication.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Entity
public class Songs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tamilSongName;
    private String kannadaSongName;
    private String songType;

    @Lob
    @Column(columnDefinition = "Text")
    private String tamilSong;

    @Lob
    @Column(columnDefinition = "Text")
    private String kannadaSong;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public String getTamilSongName() {
        return tamilSongName;
    }

    public void setTamilSongName(String tamilSongName) {
        this.tamilSongName = tamilSongName;
    }

    public String getKannadaSongName() {
        return kannadaSongName;
    }

    public void setKannadaSongName(String kannadaSongName) {
        this.kannadaSongName = kannadaSongName;
    }

    public String getTamilSong() {
        return tamilSong;
    }

    public void setTamilSong(String tamilSong) {
        this.tamilSong = tamilSong;
    }

    public String getKannadaSong() {
        return kannadaSong;
    }

    public void setKannadaSong(String kannadaSong) {
        this.kannadaSong = kannadaSong;
    }

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

    public String getSongType() {
        return songType;
    }

    public void setSongType(String songType) {
        this.songType = songType;
    }
    public Songs() {
    }
}
