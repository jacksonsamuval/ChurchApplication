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

    public ResponseEntity<?> saveProfilePicture(Integer userId, MultipartFile imageFile) throws IOException {
        Ulogin user = userRepo.findById(userId).orElseThrow(() ->
                new RuntimeException("User Not Found")
        );

        ProfilePicture profilePicture = profilePictureRepo.findByUser(user);

        if (profilePicture != null) {
            ProfilePicture profilePicture1 = profilePictureRepo.findById(profilePicture.getId()).orElseThrow(() ->
                    new RuntimeException("Profile Picture Not Found"));
            profilePicture1.setImageName(imageFile.getOriginalFilename());
            profilePicture1.setImageType(imageFile.getContentType());
            profilePicture1.setImageDate(imageFile.getBytes());
            profilePictureRepo.save(profilePicture1);
            return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
        } else {
            ProfilePicture profilePicture2 = new ProfilePicture();
            profilePicture2.setUser(user);
            profilePicture2.setImageName(imageFile.getOriginalFilename());
            profilePicture2.setImageType(imageFile.getContentType());
            profilePicture2.setImageDate(imageFile.getBytes());
            profilePictureRepo.save(profilePicture2);
            return new ResponseEntity<>("Added Successfully", HttpStatus.OK);
        }
    }


    public ProfilePicture getProfileById(Integer uloginId) {
        return profilePictureRepo.findUserByUloginId(uloginId);
    }
}
