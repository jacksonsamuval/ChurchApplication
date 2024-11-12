package com.church.ChurchApplication.emailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.church.ChurchApplication.repo.PasswordResetTokenRepository;
import com.church.ChurchApplication.entity.PasswordResetToken;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	
	
	public String generateOtp()
	{
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}
	
	public void saveOtp(String email, String otp)
	{
		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setEmail(email);
		resetToken.setOtp(otp);
		resetToken.setExpiryDate(Instant.now().plus(Duration.ofMinutes(5)));
		passwordResetTokenRepository.save(resetToken);
	}

	public boolean isExpired(PasswordResetToken passwordResetToken) {
		Optional<PasswordResetToken> passwordResetToken1 = passwordResetTokenRepository.findByEmail(passwordResetToken.getEmail());
		return passwordResetToken1.get().isExpired();
	}

}
