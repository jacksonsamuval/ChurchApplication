package com.church.ChurchApplication.service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.church.ChurchApplication.dto.*;
import com.church.ChurchApplication.emailService.EmailService;
import com.church.ChurchApplication.emailService.OtpService;
import com.church.ChurchApplication.entity.*;
import com.church.ChurchApplication.jwtService.JwtService;
import com.church.ChurchApplication.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

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


public ResponseEntity<?> saveUser(Ulogin user)
{
	try{
		if(user==null || user.getEmail()==null)
		{
			return ResponseEntity.status(401).body("error");
		}
		if (repo.findByEmail(user.getEmail()).isPresent())
		{
			return ResponseEntity.status(402).body("error");
		}
		if(repo.findByMobileNo(user.getMobileNo()).isPresent())
		{
			return ResponseEntity.status(405).body("error");
		}
		else if(repo.findUserByUsername(user.getUsername()).isPresent())
		{
			return  ResponseEntity.status(403).body("error");
		}
		else
		{
			repo.save(user);
			return new ResponseEntity<>("Success",HttpStatus.OK);
		}
	} catch (Exception e){
		return ResponseEntity.status(404).body("network error");
	}
}
	
	@Transactional
	public Ulogin findUserByEmail(String email)
	{
		return repo.findUserByEmail(email);
	}

	public ResponseEntity<String> otpVerified(VerificationRequest verificationRequest) {
		try{
			String email = verificationRequest.getEmail();
			String otp = verificationRequest.getOtp();

			Optional<PasswordResetToken> tokenOtp = passwordResetTokenRepository.findByEmailAndOtp(email,otp);
			if (tokenOtp.isPresent()) {
				if (tokenOtp.get().isExpired()) {
					return ResponseEntity.status(401).body("expired");
				}
				if (!tokenOtp.get().getOtp().equals(otp)) {
					return ResponseEntity.status(402).body("invalid");
				}
				passwordResetTokenRepository.delete(tokenOtp.get());
				return new ResponseEntity<>("Otp Successfully verified", HttpStatus.OK);
			} else {
				return ResponseEntity.status(403).body("timeup");
			}
		} catch (Exception e) {
			return ResponseEntity.status(404).body("Network error");
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
		try{
			String email = request.getEmail();
			String newPassword = request.getNewPassword();

			Ulogin user = repo.findUserByEmail(email);
			if (user == null) {
				return ResponseEntity.status(401).body("user not found");
			}
			user.setPassword(newPassword);
			repo.save(user);
			return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(402).body("Network error");
		}

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

    public ResponseEntity<?> greetingsToUser() {
        Ulogin ulogin = getCurrentUser();
        String name = ulogin.getName();
        String greetings;
        LocalTime now =LocalTime.now();

        if (now.isBefore(LocalTime.NOON))
        {
            greetings = "Good Morning";
        } else if (now.isBefore(LocalTime.of(18,0))) {
            greetings = "Good Afternoon";
        }else {
            greetings = "Good Night";
        }
        String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        String greet = greetings + " " + capitalizedName + "!";
        return new ResponseEntity<>(greet,HttpStatus.OK);
    }

    public ResponseEntity<?> getAllUser() {
		String role = "USER";
		try{
			List<Ulogin> ulogin = userRepo.findByRole(role);
			return new ResponseEntity<>(ulogin,HttpStatus.OK);
		} catch (Exception e ){
			return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
		}

    }

	public ResponseEntity<?> getAllPastors() {
		try{
			String role = "PASTOR";
			List<Ulogin> ulogins = userRepo.findByRole(role);
			return ResponseEntity.ok(ulogins);
		} catch (Exception e) {
			return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> forgetPassword(PasswordResetRequest passwordResetRequest) {
		try{
			String email = passwordResetRequest.getEmail();
			String otp = otpService.generateOtp();
			Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByEmail(email);
			if(resetToken.isPresent())
			{
				passwordResetTokenRepository.delete(resetToken.get());
			}
			if (!existsByEmail(email)) {
				return ResponseEntity.status(401).body("error");
			}

			otpService.saveOtp(email,otp);
			emailService.sendOtpEmail(email,otp);

			return new ResponseEntity<>("OTP sent to email", HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(402).body("network error");
		}
	}
}