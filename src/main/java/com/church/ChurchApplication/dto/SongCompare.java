package com.church.ChurchApplication.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class SongCompare {
    private Integer id;
    private String englishSongName;
    private String kannadaSongName;
    private String songType;
    private String artistName;
    private String englishSong;
    private String kannadaSong;
    private LocalDateTime createdAt;
    private boolean isFavorite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnglishSongName() {
        return englishSongName;
    }

    public void setEnglishSongName(String englishSongName) {
        this.englishSongName = englishSongName;
    }

    public String getKannadaSongName() {
        return kannadaSongName;
    }

    public void setKannadaSongName(String kannadaSongName) {
        this.kannadaSongName = kannadaSongName;
    }

    public String getSongType() {
        return songType;
    }

    public void setSongType(String songType) {
        this.songType = songType;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getEnglishSong() {
        return englishSong;
    }

    public void setEnglishSong(String englishSong) {
        this.englishSong = englishSong;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
