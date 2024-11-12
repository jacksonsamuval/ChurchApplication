package com.church.ChurchApplication.entity;
import java.util.Collection;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@Entity
public class Ulogin{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String email;
	private String mobileNo;
	private String username;
	private String password;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ProfilePicture profilePicture;


	public Ulogin(Integer id, String name, String email, String mobileNo, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.username = username;
		this.password = password;
	}

	public ProfilePicture getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(ProfilePicture profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getMobileNo() {
		return mobileNo;
	}




	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public Ulogin()
	{

	}

}