package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.dto.Video;
import com.church.ChurchApplication.entity.Songs;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.entity.VideoStorage;
import com.church.ChurchApplication.service.BannerService;
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
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SongService songService;

    @Autowired
    private BannerService bannerService;

    @PostMapping("/addVideo")
    public ResponseEntity<?> saveVideo(@RequestBody VideoStorage videoStorage )
    {
        try{
            VideoStorage video = videoService.saveVideo(videoStorage);
            return new ResponseEntity<>(video, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllVideos")
    public ResponseEntity<?> getAllVideo(){
        return videoService.getAllVideo();
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

    @PostMapping("addBanners")
    public ResponseEntity<?> addBannersToDb( @RequestPart("image") MultipartFile image) {
        try {
            return bannerService.saveBanner(image);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/viewBanners")
    public ResponseEntity<?> viewAllBanners(){
        return bannerService.getALLBanners();
    }

    @DeleteMapping("removeBanners/{bannerId}")
    public ResponseEntity<?> removeBanner(@PathVariable Integer bannerId) {
        try {
            return bannerService.deleteBanner(bannerId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("editVideo/{videoId}")
    public ResponseEntity<?> editVideo(@PathVariable Integer videoId, @RequestBody Video updatedVideo){
        return videoService.editVideo(videoId,updatedVideo.getVideoName(),updatedVideo.getVideoDescription(),updatedVideo.getVideoUrl());
    }
}

//Save Video to Db

//    @PostMapping("/addVideo")
//    public ResponseEntity<?> saveVideo(@RequestPart MultipartFile videoFile)
//    {
//        try{
//            VideoStorage video = videoService.saveVideo(videoFile);
//            return new ResponseEntity<>(video, HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
