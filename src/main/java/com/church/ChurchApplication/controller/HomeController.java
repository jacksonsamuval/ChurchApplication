package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.entity.FavoriteVideo;
import com.church.ChurchApplication.entity.PlayList;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private FavoriteVideoService favoriteVideoService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayListService playListService;

    @GetMapping("deleteMyAccount")
    public ResponseEntity<String> deleteMyAccount(@RequestParam String email )
    {
        return homeService.deleteMyAccount(email);
    }

    @GetMapping("listAllPastors")
    public ResponseEntity<List<Ulogin>> listAllPastors()
    {
        return homeService.getAllPastors();
    }

    @PostMapping("/addToFavorite/{videoId}")
    public ResponseEntity<?> addToFavorites(@PathVariable Integer videoId)
    {
        return favoriteVideoService.addToFavorites(videoId);
    }

   @DeleteMapping("/removeFromFavorite/{videoId}")
   public ResponseEntity<?> removeFromFavorites(@PathVariable Integer videoId)
   {
       return favoriteVideoService.removeFromFavorites(videoId);
   }

   @GetMapping("getAllFavorites")
   public ResponseEntity<?> getAllFavoriteVideos()
   {
       return favoriteVideoService.getAllFavoriteVideos();
   }

    @PostMapping("/getVideo/{videoId}")
    public ResponseEntity<?> getVideo(@PathVariable Integer videoId)
    {
        return videoService.findVideoById(videoId);
    }

    @PostMapping("/createPlayList/{playListName}")
    public ResponseEntity<?> createPlayList(@PathVariable String playListName)
    {
        Ulogin user = userService.getCurrentUser();
        return playListService.createPlayList(user.getId(),playListName);
    }

    @GetMapping("getPlayList")
    public List<PlayList> getPlayListForUser()
    {
        Ulogin user = userService.getCurrentUser();
        return playListService.getPlayListForUser(user.getId());
    }

    @PutMapping("addToPlayList/{playListId}")
    public ResponseEntity<?> addToPlayList(@PathVariable Integer playListId,@RequestParam Integer videoId)
    {
        return playListService.addToPlayList(playListId,videoId);
    }

    @PutMapping("removeFromPlayList/{playListId}")
    public ResponseEntity<?> removeFromPlayList(@PathVariable Integer playListId,@RequestParam Integer videoId)
    {
        return playListService.removeFromPlayList(playListId,videoId);
    }

    @DeleteMapping("deletePlayList/{playListId}")
    public ResponseEntity<?> deletePlayList(@PathVariable Integer playListId)
    {
        return playListService.deletePlayList(playListId);
    }

}


