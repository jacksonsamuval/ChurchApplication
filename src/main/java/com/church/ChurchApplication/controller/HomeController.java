package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.entity.FavoriteVideo;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.service.FavoriteVideoService;
import com.church.ChurchApplication.service.HomeService;
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
}
