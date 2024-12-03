package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.*;
import com.church.ChurchApplication.repo.FavoriteSongsRepo;
import com.church.ChurchApplication.repo.SongsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteSongService {

    @Autowired
    private UserService userService;

    @Autowired
    private SongsRepo songsRepo;

    @Autowired
    private FavoriteSongsRepo favoriteSongsRepo;

    public ResponseEntity<?> addToFavorites(Integer songId) {
        Ulogin user = userService.getCurrentUser();

        Songs song = songsRepo.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song Not Found"));

        if (favoriteSongsRepo.findFavoriteSongAndUser(user, song).isPresent()) {
            return new ResponseEntity<>("Song is already in favorites", HttpStatus.CONFLICT);
        }

        FavoriteSong favoriteSong = new FavoriteSong();
        favoriteSong.setSongs(song);
        favoriteSong.setUlogin(user);
        favoriteSongsRepo.save(favoriteSong);

        return new ResponseEntity<>("Song Added Successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllFavoriteSongs() {

        Ulogin ulogin = userService.getCurrentUser();
        List<FavoriteSong> favoriteSong = favoriteSongsRepo.findByUlogin(ulogin);
        if (favoriteSong.isEmpty()) {
            return new ResponseEntity<>("No Songs Available", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(favoriteSong, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> removeSongFromFavorites(Integer songId) {

        try{
            Songs songs = songsRepo.findById(songId)
                    .orElseThrow(()-> new RuntimeException("Song Not Found"));

            Ulogin user = userService.getCurrentUser();

            FavoriteSong favoriteSong = favoriteSongsRepo.findFavoriteSongAndUser(user,songs)
                    .orElseThrow(()-> new RuntimeException("Song Is Not In Your FavoriteVideo"));
            favoriteSongsRepo.delete(favoriteSong);
            return new ResponseEntity<>("Removed Successfully",HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Something went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
