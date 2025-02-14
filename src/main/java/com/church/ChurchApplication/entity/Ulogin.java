package com.church.ChurchApplication.entity;
import java.util.ArrayList;
import java.util.List;

import com.church.ChurchApplication.dto.Gender;
import jakarta.persistence.*;
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
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(nullable = false)
	private String role = "USER";

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ProfilePicture profilePicture;

	@OneToMany(mappedBy = "ulogin",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<PlayList> playList = new ArrayList<>();


	public Ulogin(Integer id, String name, String email, String mobileNo, String username, String password, String role, ProfilePicture profilePicture) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.username = username;
		this.password = password;
		this.role = role;
		this.profilePicture = profilePicture;
	}

	public List<PlayList> getPlayList() {
		return playList;
	}

	public void setPlayList(List<PlayList> playList) {
		this.playList = playList;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ProfilePicture getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(ProfilePicture profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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