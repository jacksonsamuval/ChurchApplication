package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.emailService.EmailService;
import com.church.ChurchApplication.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.church.ChurchApplication.jwtService.JwtService;
import com.church.ChurchApplication.emailService.OtpService;
import com.church.ChurchApplication.service.UserService;

import com.church.ChurchApplication.dto.*;
import com.church.ChurchApplication.repo.*;
import com.church.ChurchApplication.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/auth/")
public class HomeAuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserProfileService userProfileService;

	//test mappings
	
	@GetMapping("hello") //test without Spring Security
	public String demo()
	{
		return "hello";
	}
	
	@GetMapping("test") //test with Spring Security
	public String test()
	{
		return "test purpose - how are you guys";
	}

	@GetMapping("getUser")
	public ResponseEntity<GetUserDetails> getUser(@RequestParam String email)
	{
		return userService.getUser(email);
	}

	
	 //Required Mappings
	 
	@PostMapping("register")
	public ResponseEntity<String> login(@RequestBody Ulogin ulogin)
	{
		return userService.saveUser(ulogin);
	}
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody UsersLogin user)
	{	
		try
		{
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmailOrUsername(),user.getPassword()));
			
				return new ResponseEntity<>(jwtService.generateToken(user.getEmailOrUsername()),HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("forgotPassword")
	public ResponseEntity<String> forgetPassword(@RequestBody PasswordResetRequest passwordResetRequest)
	{
		String email = passwordResetRequest.getEmail();
		String otp = otpService.generateOtp();
		Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByEmail(email);
		if(resetToken.isPresent())
		{
			passwordResetTokenRepository.delete(resetToken.get());
		}
		 if (!userService.existsByEmail(email)) {
		        return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
		    }
		
		
		otpService.saveOtp(email,otp);
		emailService.sendOtpEmail(email,otp);
		
		return new ResponseEntity<>("OTP sent to email", HttpStatus.OK);
		
	}
	
	@GetMapping("validate")
    public ResponseEntity<String> testToken(@RequestParam String token) {
        // Validate the token (you'll implement this function)
        if (jwtService.validateToken(token)) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

	@PostMapping("otpVerification")
	public ResponseEntity<String> otpverified(@RequestBody VerificationRequest verificationRequest)
	{
		return userService.otpVerified(verificationRequest);
	}
	
	@PostMapping("resendOtp")
	public ResponseEntity<String> resendOtp(@RequestBody PasswordResetRequest passwordResetRequest) {
	    
		return userService.resendOtp(passwordResetRequest);
	}
	
	@PostMapping("resetPassword")
	public ResponseEntity<String> resetPasswordRequest(@RequestBody ResetPasswordRequest request) {
	    
		return userService.resetPassword(request);
	}

	@PostMapping("setProfilePicture/{userId}")
	public ResponseEntity<?> setProfilePicture(@PathVariable Integer userId,
											   @RequestPart MultipartFile imageFile) {
		try {
			ProfilePicture profilePicture1 = userProfileService.saveProfilePicture(userId, imageFile);
			return new ResponseEntity<>(profilePicture1,HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getProfilePicture/{uloginId}")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable Integer uloginId)
	{
		ProfilePicture profilePicture = userProfileService.getProfileById(uloginId);
		byte[] imageFile = profilePicture.getImageDate();
		return ResponseEntity.ok().contentType(MediaType.valueOf(profilePicture.getImageType()))
				.body(imageFile);
	}


}
