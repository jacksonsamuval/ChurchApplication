package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePictureRepo extends JpaRepository<ProfilePicture,Integer> {
    ProfilePicture findUserById(Integer userId);
}
