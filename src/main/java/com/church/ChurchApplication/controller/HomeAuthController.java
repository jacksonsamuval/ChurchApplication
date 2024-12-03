package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.emailService.EmailService;
import com.church.ChurchApplication.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.church.ChurchApplication.jwtService.JwtService;
import com.church.ChurchApplication.emailService.OtpService;
import com.church.ChurchApplication.service.UserService;

import com.church.ChurchApplication.dto.*;
import com.church.ChurchApplication.repo.*;
import com.church.ChurchApplication.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	@Autowired
	private ProfilePictureRepo profilePictureRepo;

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
	public ResponseEntity<?> login(@RequestBody UsersLogin user)
	{	
		try
		{
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmailOrUsername(),user.getPassword()));
			if(authentication.isAuthenticated()) {

				String email = user.getEmailOrUsername();
				Ulogin userService1 = userService.findUserByEmail(email);

				Details details = new Details();
				details.setUserId(userService1.getId());
				details.setUserName(userService1.getUsername());
				details.setEmail(email);
				details.setRole(userService1.getRole());
				details.setMobileNo( userService1.getMobileNo());
				details.setName(userService1.getName());

				String token = jwtService.generateToken(user.getEmailOrUsername());
				//To Get Complete User Details
//				AboutUser aboutUser = userService.getCompleteUserDetails(user.getEmailOrUsername());
				Map<String,Object> response = new HashMap<>();
				response.put("Token",token);
				response.put("Details",details);

				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
			}
		}
		catch (BadCredentialsException e) {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
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

	@PostMapping("setProfilePicture")
	public ResponseEntity<?> setProfilePicture( @RequestPart MultipartFile imageFile) {
		try {
			Ulogin users = userService.getCurrentUser();
			System.out.println(users.getEmail());
			return userProfileService.saveProfilePicture(users.getId(), imageFile);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getProfilePicture")
	public ResponseEntity<byte[]> getImageByProductId()
	{
		Ulogin users = userService.getCurrentUser();
		ProfilePicture profilePicture = userProfileService.getProfileById(users.getId());
		byte[] imageFile = profilePicture.getImageDate();
		System.out.println(users.getEmail());
		return ResponseEntity.ok().contentType(MediaType.valueOf(profilePicture.getImageType()))
				.body(imageFile);
	}
}
