package com.church.ChurchApplication.config;

import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountCreator implements ApplicationRunner {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String adminEmail = "admin@gmail.com";
        String adminPassword = "admin123";
        Ulogin user = userRepo.findUserByEmail(adminEmail);
        if (user==null)
        {
            Ulogin admin = new Ulogin();
            admin.setUsername("admin");
            admin.setName("Administrator");
            admin.setEmail(adminEmail);
            admin.setMobileNo("7795011089");
            admin.setRole("ADMIN");
            admin.setPassword(adminPassword);
            userRepo.save(admin);
            System.out.println("Default admin account created with email: " + adminEmail);
        } else {
            System.out.println("Admin account already exists.");
        }
    }
}
