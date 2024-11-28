package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.ProfilePicture;
import com.church.ChurchApplication.entity.Ulogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePictureRepo extends JpaRepository<ProfilePicture,Integer> {
    ProfilePicture findUserById(Integer userId);

    @Query("SELECT p FROM ProfilePicture p where p.user.id= :uloginId")
    ProfilePicture findUserByUloginId(Integer uloginId);

    ProfilePicture findByUser(Ulogin user);
}
