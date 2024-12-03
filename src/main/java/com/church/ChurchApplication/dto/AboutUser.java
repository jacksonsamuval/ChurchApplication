package com.church.ChurchApplication.dto;

import com.church.ChurchApplication.entity.FavoriteSong;
import com.church.ChurchApplication.entity.FavoriteVideo;
import com.church.ChurchApplication.entity.PlayList;
import com.church.ChurchApplication.entity.ProfilePicture;

import java.util.ArrayList;
import java.util.List;

public class AboutUser {
    private Integer userId;
    private String name;
    private String userName;
    private String mobileNo;
    private String email;
    private String role;
    private ProfilePicture profilePicture;
    private List<PlayList> playList = new ArrayList<>();
    private List<FavoriteSong> favoriteSongs;
    private List<FavoriteVideo> favoriteVideos;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<PlayList> getPlayList() {
        return playList;
    }

    public void setPlayList(List<PlayList> playList) {
        this.playList = playList;
    }

    public List<FavoriteSong> getFavoriteSongs() {
        return favoriteSongs;
    }

    public void setFavoriteSongs(List<FavoriteSong> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

    public List<FavoriteVideo> getFavoriteVideos() {
        return favoriteVideos;
    }

    public void setFavoriteVideos(List<FavoriteVideo> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
    }
}
