package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.dto.UsersLogin;
import com.church.ChurchApplication.dto.Video;
import com.church.ChurchApplication.entity.LiveVideo;
import com.church.ChurchApplication.entity.Songs;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.entity.VideoStorage;
import com.church.ChurchApplication.service.*;
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

    @Autowired
    private BannerService bannerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PastorService pastorService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody UsersLogin usersLogin){
        return adminService.adminLogin(usersLogin);
    }

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

    @GetMapping("/getAllSongs")
    public ResponseEntity<?> getAllSongs(){
        return songService.getAllSongs();
    }

    @PutMapping("/editSongs/{id}")
    public ResponseEntity<?> editSongs(@PathVariable Integer id,@RequestBody Songs songs){
        return songService.editSongs(id,songs);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser()
    {
        return userService.getAllUser();
    }

    @DeleteMapping("deleteSongs/{id}")
    public ResponseEntity<?> deleteSongs(@PathVariable Integer id)
    {
        return songService.deleteSong(id);
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

    @GetMapping("viewAllPastors")
    public ResponseEntity<?> viewAllPastors(){
        return userService.getAllPastors();
    }

    @DeleteMapping("deleteId/{id}")
    public ResponseEntity<?> deletePastorId(@PathVariable Integer id){
        return pastorService.deleteId(id);
    }

    @PostMapping("addLiveVideo")
    public ResponseEntity<?> addLiveVideo(@RequestBody LiveVideo liveVideo){
        return videoService.addLive(liveVideo);
    }
}
