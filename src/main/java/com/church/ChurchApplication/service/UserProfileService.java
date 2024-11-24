package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.ProfilePicture;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.repo.ProfilePictureRepo;
import com.church.ChurchApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserProfileService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProfilePictureRepo profilePictureRepo;

    public ProfilePicture saveProfilePicture(Integer userId, MultipartFile imageFile) throws IOException {
        Ulogin user = userRepo.findById(userId).orElseThrow(()->
                new RuntimeException("User Not Found")
        );
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setUser(user);
        profilePicture.setImageName(imageFile.getOriginalFilename());
        profilePicture.setImageType(imageFile.getContentType());
        profilePicture.setImageDate(imageFile.getBytes());
        return profilePictureRepo.save(profilePicture);
    }

    public ProfilePicture getProfileById(Integer uloginId) {
        return profilePictureRepo.findUserByUloginId(uloginId);
    }
}
