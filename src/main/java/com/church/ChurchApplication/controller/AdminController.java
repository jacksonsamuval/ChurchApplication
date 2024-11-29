package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.entity.Songs;
import com.church.ChurchApplication.entity.VideoStorage;
import com.church.ChurchApplication.service.SongService;
import com.church.ChurchApplication.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SongService songService;

    @PostMapping("/addVideo")
    public ResponseEntity<?> saveVideo(@RequestPart MultipartFile videoFile)
    {
        try{
            VideoStorage video = videoService.saveVideo(videoFile);
            return new ResponseEntity<>(video, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteVideo/{videoId}")
    public ResponseEntity<?> deleteVideo(@PathVariable Integer videoId)
    {
        return videoService.deleteVideo(videoId);
    }

    @PostMapping("/addSongs")
    public ResponseEntity<?> addSongs(@RequestBody Songs songs)
    {
        return songService.addSongs(songs);
    }

    @DeleteMapping("deleteSongs/{songId}")
    public ResponseEntity<?> deleteSongs(@PathVariable Integer songId)
    {
        return songService.deleteSong(songId);
    }


}
