package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.FavoriteVideo;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.entity.VideoStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteVideoRepo extends JpaRepository<FavoriteVideo, Integer> {

    @Query("SELECT fv FROM FavoriteVideo fv WHERE fv.ulogin = :user AND fv.videoStorage = :video")
    Optional<FavoriteVideo> findFavoriteVideoAndUser(@Param("user") Ulogin user, @Param("video") VideoStorage video);

    List<FavoriteVideo> findByUlogin(Ulogin user);
}
