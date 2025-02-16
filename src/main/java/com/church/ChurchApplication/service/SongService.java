package com.church.ChurchApplication.service;

import com.church.ChurchApplication.dto.SongCompare;
import com.church.ChurchApplication.entity.FavoriteSong;
import com.church.ChurchApplication.entity.Songs;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.repo.FavoriteSongsRepo;
import com.church.ChurchApplication.repo.SongsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SongService {

    @Autowired
    private SongsRepo songsRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteSongsRepo favoriteSongsRepo;

    public ResponseEntity<?> addSongs(Songs songs) {
        if (songs != null) {
            songsRepo.save(songs);
            return new ResponseEntity<>("Successfully Saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Please enter all the Details", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getSongs(Integer songId) {
        Optional<Songs> songs = songsRepo.findById(songId);
        if (songs.isEmpty()) {
            return new ResponseEntity<>("Song Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(songs, HttpStatus.FOUND);

    }

    public ResponseEntity<?> deleteSong(Integer songId) {
        Optional<Songs> songs = songsRepo.findById(songId);
        if (songs.isEmpty()) {
            return new ResponseEntity<>("Song Not Found", HttpStatus.NOT_FOUND);
        }
        songsRepo.deleteById(songId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<?> getAllSongs() {
        try {
            List<Songs> song = songsRepo.findAll();
            return new ResponseEntity<>(song, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> editSongs(Integer songId, Songs songs) {
        try {
            Songs song = songsRepo.findById(songId).orElseThrow(() -> new RuntimeException("Not Found"));
            song.setEnglishSongName(songs.getEnglishSongName());
            song.setKannadaSongName(songs.getKannadaSongName());
            song.setSongType(songs.getSongType());
            song.setArtistName(songs.getArtistName());
            song.setKannadaSong(song.getKannadaSong());
            song.setEnglishSong(song.getEnglishSong());
            songsRepo.save(song);
            return new ResponseEntity<>(song, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSongsBasedOnLikes() {
        Ulogin ulogin = userService.getCurrentUser();
        List<Songs> songs = songsRepo.findAll();
        List<FavoriteSong> favoriteSongs = favoriteSongsRepo.findByUlogin(ulogin);

        Set<Integer> favoriteSongIds = favoriteSongs.stream()
                .map(favoriteSong -> favoriteSong.getSongs().getId())
                .collect(Collectors.toSet());

        List<SongCompare> songList = songs.stream().map(song -> {
            SongCompare songCompare = new SongCompare();
            songCompare.setId(song.getId());
            songCompare.setEnglishSongName(song.getEnglishSongName());
            songCompare.setKannadaSongName(song.getKannadaSongName());
            songCompare.setSongType(song.getSongType());
            songCompare.setArtistName(song.getArtistName());
            songCompare.setEnglishSong(song.getEnglishSong());
            songCompare.setKannadaSong(song.getKannadaSong());
            songCompare.setCreatedAt(song.getCreatedAt());

            boolean isPresent = favoriteSongIds.contains(song.getId());

            songCompare.setFavorite(isPresent);

            return songCompare;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(songList);
    }

}
