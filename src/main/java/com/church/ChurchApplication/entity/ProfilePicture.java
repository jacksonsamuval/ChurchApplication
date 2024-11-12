package com.church.ChurchApplication.entity;

import jakarta.persistence.*;

@Entity
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageName;
    private String imageType;

    @Lob
    private byte[] imageDate;

    @OneToOne
    @JoinColumn(name = "Ulogin_id")
    private Ulogin user;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageDate() {
        return imageDate;
    }

    public void setImageDate(byte[] imageDate) {
        this.imageDate = imageDate;
    }

    public Ulogin getUser() {
        return user;
    }

    public void setUser(Ulogin user) {
        this.user = user;
    }

    public ProfilePicture() {
    }
}
