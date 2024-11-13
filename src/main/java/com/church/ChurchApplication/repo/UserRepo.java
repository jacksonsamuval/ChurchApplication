package com.church.ChurchApplication.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.church.ChurchApplication.entity.PasswordResetToken;
import com.church.ChurchApplication.entity.Ulogin;

@Repository
public interface UserRepo extends JpaRepository<Ulogin, Integer> {


	Ulogin findUserByEmail(String email);

	Ulogin findByUsername(String username);

	Optional<Ulogin> findUserByUsername(String username);
	
	Optional<PasswordResetToken> findUserUsingByEmail(String email);
	
	Optional<Ulogin> findByEmail(String email);

	boolean existsByEmail(String email);

	List<Ulogin> findByRole(String role);
}
