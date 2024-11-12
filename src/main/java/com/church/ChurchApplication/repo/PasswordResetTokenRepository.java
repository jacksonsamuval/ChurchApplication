package com.church.ChurchApplication.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.church.ChurchApplication.entity.PasswordResetToken;
import com.church.ChurchApplication.entity.Ulogin;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByEmailAndOtp(String email, String otp);

	Optional<PasswordResetToken> findByEmail(String email);

}