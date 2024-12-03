package com.church.ChurchApplication.service;

import java.util.List;
import java.util.Optional;

import com.church.ChurchApplication.dto.*;
import com.church.ChurchApplication.emailService.EmailService;
import com.church.ChurchApplication.emailService.OtpService;
import com.church.ChurchApplication.entity.*;
import com.church.ChurchApplication.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	@Autowired
	private OtpService otpService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private FavoriteSongsRepo favoriteSongsRepo;

	@Autowired
	private FavoriteVideoRepo favoriteVideoRepo;

	@Autowired
	private PlayListRepo playListRepo;

	@Autowired
	private UserRepo userRepo;


public ResponseEntity<String> saveUser(Ulogin user)
	{
		if(user==null || user.getEmail()==null)
		{
			return new ResponseEntity<>("Invalid User Details",HttpStatus.BAD_REQUEST);
		}
		
		 if (repo.findByEmail(user.getEmail()).isPresent()) 
		 {
			 return new ResponseEntity<>("Email Exists",HttpStatus.BAD_REQUEST);
		 }
		 else if(repo.findUserByUsername(user.getUsername()).isPresent())
		 {
			 return new ResponseEntity<>("Username Already Exists",HttpStatus.BAD_REQUEST);
		 }
		 else
		 {
			 repo.save(user);
			 return new ResponseEntity<>("Success",HttpStatus.OK);
		 }
		
		
	}
	
	@Transactional
	public Ulogin findUserByEmail(String email)
	{
		return repo.findUserByEmail(email);
	}

	public ResponseEntity<String> otpVerified(VerificationRequest verificationRequest) {
		
		String email = verificationRequest.getEmail();
		String otp = verificationRequest.getOtp();
		
		Optional<PasswordResetToken> tokenOtp = passwordResetTokenRepository.findByEmailAndOtp(email,otp);
		if (tokenOtp.isPresent()) {
		    if (tokenOtp.get().isExpired()) {
		        return new ResponseEntity<>("Otp Expired", HttpStatus.UNAUTHORIZED);
		    }
		    if (!tokenOtp.get().getOtp().equals(otp)) {
		        return new ResponseEntity<>("Otp Not Valid", HttpStatus.FORBIDDEN);
		    }
		    passwordResetTokenRepository.delete(tokenOtp.get());
		    return new ResponseEntity<>("Otp Successfully verified", HttpStatus.OK);
		} else {
		    return new ResponseEntity<>("Otp Not Valid", HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<String> resendOtp(PasswordResetRequest passwordResetRequest) {
		String email = passwordResetRequest.getEmail();
	    Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByEmail(email);

	    // Check if the token exists and is still valid (within 10 minutes)
	    
	    if (resetToken.isPresent() && !otpService.isExpired(resetToken.get())) {
	        return new ResponseEntity<>("OTP was already sent recently. Please check your email.", HttpStatus.TOO_MANY_REQUESTS);
	    }

	    // Generate new OTP and save
	    String otp = otpService.generateOtp();
	    resetToken.ifPresent(passwordResetTokenRepository::delete);
	    otpService.saveOtp(email, otp);
	    emailService.sendOtpEmail(email, otp);

	    return new ResponseEntity<>("New OTP sent to email", HttpStatus.OK);
	}

	public ResponseEntity<String> resetPassword(ResetPasswordRequest request) {
		String email = request.getEmail();
	    String newPassword = request.getNewPassword();

	    Ulogin user = repo.findUserByEmail(email);
	    
	    if (user == null) {
	        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	    }
	    user.setPassword(newPassword);
	    
	    repo.save(user);

	    return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
	}

	public boolean existsByEmail(String email) {

		return repo.existsByEmail(email);
		
	}

    public ResponseEntity<GetUserDetails> getUser(String email) {
		GetUserDetails getUserDetails = new GetUserDetails();
		Ulogin user =  repo.findUserByEmail(email);
		getUserDetails.setId(user.getId());
		getUserDetails.setName(user.getName());
		getUserDetails.setUsername(user.getUsername());
		getUserDetails.setMobileNo(user.getMobileNo());
		getUserDetails.setEmail(user.getEmail());
		getUserDetails.setRole(user.getRole());
		return new ResponseEntity<>(getUserDetails, HttpStatus.OK );

    }

	public Ulogin getCurrentUser() {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userPrincipal.getUser();
	}

	public ResponseEntity<?> getAboutProfile() {
		try {
			Ulogin ulogin = getCurrentUser();

			if (ulogin == null) {
				return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
			}

			List<PlayList> playList = playListRepo.findByUlogin(ulogin);
			List<FavoriteSong> favoriteSong = favoriteSongsRepo.findByUlogin(ulogin);
			List<FavoriteVideo> favoriteVideos = favoriteVideoRepo.findByUlogin(ulogin);

			AboutUser aboutUser = new AboutUser();
			aboutUser.setUserName(ulogin.getUsername());
			aboutUser.setUserId(ulogin.getId());
			aboutUser.setEmail(ulogin.getEmail());
			aboutUser.setMobileNo(ulogin.getMobileNo());
			aboutUser.setRole(ulogin.getRole());
			aboutUser.setProfilePicture(ulogin.getProfilePicture());
			aboutUser.setPlayList(playList);
			aboutUser.setFavoriteSongs(favoriteSong);
			aboutUser.setFavoriteVideos(favoriteVideos);

			return new ResponseEntity<>(aboutUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Something went wrong while fetching the profile", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public AboutUser getCompleteUserDetails(String email)
	{
			Ulogin ulogin = userRepo.findUserByEmail(email);

			List<PlayList> playList = playListRepo.findByUlogin(ulogin);
			List<FavoriteSong> favoriteSong = favoriteSongsRepo.findByUlogin(ulogin);
			List<FavoriteVideo> favoriteVideos = favoriteVideoRepo.findByUlogin(ulogin);

			AboutUser aboutUser = new AboutUser();
			aboutUser.setUserName(ulogin.getUsername());
			aboutUser.setUserId(ulogin.getId());
			aboutUser.setEmail(ulogin.getEmail());
			aboutUser.setMobileNo(ulogin.getMobileNo());
			aboutUser.setRole(ulogin.getRole());
			aboutUser.setProfilePicture(ulogin.getProfilePicture());
			aboutUser.setPlayList(playList);
			aboutUser.setFavoriteSongs(favoriteSong);
			aboutUser.setFavoriteVideos(favoriteVideos);

			return aboutUser;
	}
}
