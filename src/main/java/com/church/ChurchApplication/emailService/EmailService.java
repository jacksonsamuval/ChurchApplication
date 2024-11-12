package com.church.ChurchApplication.emailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendOtpEmail(String email,String otp)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\nThis code is valid for 10 minutes.");

        mailSender.send(message);
		
	}

}
