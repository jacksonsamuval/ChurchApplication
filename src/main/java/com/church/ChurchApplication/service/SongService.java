package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.Songs;
import com.church.ChurchApplication.repo.SongsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongsRepo songsRepo;

    public ResponseEntity<?> addSongs(Songs songs) {
        if (songs!=null)
        {
            songsRepo.save(songs);
            return new ResponseEntity<>("Successfully Saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Please enter all the Details", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getSongs(Integer songId) {
        Optional<Songs> songs = songsRepo.findById(songId);
        if(songs.isEmpty())
        {
            return new ResponseEntity<>("Song Not Found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(songs,HttpStatus.FOUND);

    }

    public ResponseEntity<?> deleteSong(Integer songId) {
        Optional<Songs> songs = songsRepo.findById(songId);
        if (songs.isEmpty())
        {
            return new ResponseEntity<>("Song Not Found",HttpStatus.NOT_FOUND);
        }
        songsRepo.deleteById(songId);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}
