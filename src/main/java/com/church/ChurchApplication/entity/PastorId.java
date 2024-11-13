package com.church.ChurchApplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PastorId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String pastorName;
    private String pastorIdentity;
    private boolean isVerifiedPastor;

    public boolean isVerifiedPastor() {
        return isVerifiedPastor;
    }

    public void setVerifiedPastor(boolean verifiedPastor) {
        isVerifiedPastor = verifiedPastor;
    }

    public String getPastorName() {
        return pastorName;
    }

    public void setPastorName(String pastorName) {
        this.pastorName = pastorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPastorIdentity() {
        return pastorIdentity;
    }

    public void setPastorIdentity(String pastorIdentity) {
        this.pastorIdentity = pastorIdentity;
    }

    public PastorId() {
    }
}
