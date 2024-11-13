package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<String> deleteMyAccount(String email) {

        Ulogin user = userRepo.findUserByEmail(email);
        if (user!=null)
        {
            userRepo.delete(user);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Something went Wrong", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<List<Ulogin>> getAllPastors() {
        String role="PASTOR";
        List<Ulogin> pastors = userRepo.findByRole(role);
        return new ResponseEntity<>(pastors,HttpStatus.OK);
    }
}
