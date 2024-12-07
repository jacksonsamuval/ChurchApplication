package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.entity.PlayList;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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

    @Autowired
    private SongService songService;

    @Autowired
    private FavoriteSongService favoriteSongService;

    @Autowired
    private BannerService bannerService;

    @GetMapping("greet")
    public ResponseEntity<?> greetingToUser()
    {
        return userService.greetingsToUser();
    }


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
    public ResponseEntity<List<PlayList>> getPlayListForUser()
    {
        Ulogin user = userService.getCurrentUser();
        return playListService.getPlayListForUser(user.getId());
    }

    @PutMapping("addToPlayList/{playListId}")
    public ResponseEntity<?> addToPlayList(@PathVariable Integer playListId,@RequestParam(required = false) Integer videoId,@RequestParam(required = false) Integer songId)
    {
        if (videoId!=null && songId!=null)
        {
            return new ResponseEntity<>("Only One can be added at a time", HttpStatus.BAD_REQUEST);
        }
        return playListService.addToPlayList(playListId,videoId,songId);
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

    @GetMapping("/getSongs/{songId}")
    public ResponseEntity<?> getSongs(@PathVariable Integer songId)
    {
        return  songService.getSongs(songId);
    }

    @PostMapping("addSongToFavorites/{songId}")
    public ResponseEntity<?> addSongToFavorites(@PathVariable Integer songId)
    {
        return favoriteSongService.addToFavorites(songId);
    }

    @GetMapping("getAllFavoriteSongs")
    public ResponseEntity<?> getAllFavoriteSongs()
    {
        return favoriteSongService.getAllFavoriteSongs();
    }

    @DeleteMapping("/removeSongFromFavorite/{songId}")
    public ResponseEntity<?> removeSongFromFavorites(@PathVariable Integer songId)
    {
        return favoriteSongService.removeSongFromFavorites(songId);
    }

    @GetMapping("/getRecentVideos")
    public ResponseEntity<?> getRecentVideos()
    {
        return videoService.getRecentVideos();
    }

    //About Profile
    @GetMapping("getProfileDetails")
    public ResponseEntity<?> getAboutProfile()
    {
        return userService.getAboutProfile();
    }

    @GetMapping("getBanners")
    public ResponseEntity<?> getBanners()
    {
        return bannerService.getBanners();
    }

}


