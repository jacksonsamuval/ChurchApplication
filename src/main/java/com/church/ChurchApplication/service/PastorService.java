package com.church.ChurchApplication.service;

import com.church.ChurchApplication.dto.PastorVerify;
import com.church.ChurchApplication.entity.PastorId;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.repo.PastorIdRepo;
import com.church.ChurchApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PastorService {

    @Autowired
    private PastorIdRepo pastorIdRepo;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<String> verifyPastorId(PastorVerify pastorVerify) {

        String email = pastorVerify.getEmail();
        String pastorId = pastorVerify.getPastorId();

        PastorId pastorRepo = pastorIdRepo.findByEmail(email);
        Ulogin emailUser = userRepo.findUserByEmail(email);

        if (pastorRepo != null && emailUser != null) {
            if (pastorRepo.getPastorIdentity().equals(pastorId)) {

                emailUser.setRole("PASTOR");
                pastorRepo.setVerifiedPastor(true);
                pastorIdRepo.save(pastorRepo);
                userRepo.save(emailUser);
                return new ResponseEntity<>("Success: User role updated to PASTOR", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid Pastor ID", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User or Pastor record not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addPastorId(PastorId pastorId) {
        String email = pastorId.getEmail();
        PastorId pastorRepo = pastorIdRepo.findByEmail(email);
        if(pastorRepo!=null)
        {
            return new ResponseEntity<>("Failure : Pastor With similar Email Exists", HttpStatus.BAD_REQUEST);
        }
        pastorIdRepo.save(pastorId);
        return new ResponseEntity<>("Success: Pastor ID added", HttpStatus.OK);
    }
}
