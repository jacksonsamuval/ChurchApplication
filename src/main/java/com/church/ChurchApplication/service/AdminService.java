package com.church.ChurchApplication.service;

import com.church.ChurchApplication.dto.Details;
import com.church.ChurchApplication.dto.UsersLogin;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.jwtService.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    public ResponseEntity<?> adminLogin(UsersLogin usersLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usersLogin.getEmailOrUsername(), usersLogin.getPassword())
            );

            Ulogin ulogin = userService.findUserByEmail(usersLogin.getEmailOrUsername());
            if (ulogin == null) {
                return ResponseEntity.status(403).body("No User Found");
            }

            if (!"ADMIN".equals(ulogin.getRole())) {
                return ResponseEntity.status(404).body("Access Denied: Not an Admin");
            }

            Details details = new Details();
            details.setUserId(ulogin.getId());
            details.setEmail(ulogin.getEmail());
            details.setRole(ulogin.getRole());
            details.setName(ulogin.getName());
            details.setUserName(ulogin.getEmail());
            details.setGender(ulogin.getGender());
            details.setMobileNo(ulogin.getMobileNo());

            String token = jwtService.generateToken(details.getUserName());

            Map<String, Object> response = new HashMap<>();
            response.put("Details", details);
            response.put("Token", token);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(404).body("Invalid email/username or password");
        } catch (DisabledException e) {
            return ResponseEntity.status(405).body("User account is disabled");
        } catch (Exception e) {
            return ResponseEntity.status(406).body("Unexpected Error");
        }
    }
}