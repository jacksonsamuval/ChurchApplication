package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.*;
import com.church.ChurchApplication.service.FavoriteSongService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteSongsRepo extends JpaRepository<FavoriteSong, Integer> {

    @Query("SELECT fv FROM FavoriteSong fv WHERE fv.ulogin = :user AND fv.songs = :song")
    Optional<FavoriteSong> findFavoriteSongAndUser(@Param("user") Ulogin user, @Param("song") Songs song);

    List<FavoriteSong> findByUlogin(Ulogin ulogin);
}
