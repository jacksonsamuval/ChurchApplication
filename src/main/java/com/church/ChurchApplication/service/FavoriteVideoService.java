package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.FavoriteVideo;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.entity.VideoStorage;
import com.church.ChurchApplication.repo.FavoriteVideoRepo;
import com.church.ChurchApplication.repo.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteVideoService {

    @Autowired
    private FavoriteVideoRepo favoriteVideoRepo;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public ResponseEntity<?> addToFavorites(Integer videoId) {
        Ulogin user = userService.getCurrentUser();
        VideoStorage videoStorage = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video Not Found"));

        if (favoriteVideoRepo.findFavoriteVideoAndUser(user, videoStorage).isPresent()) {
            return new ResponseEntity<>("Video is already in favorites", HttpStatus.CONFLICT);
        }

        FavoriteVideo favoriteVideo = new FavoriteVideo();
        favoriteVideo.setVideoStorage(videoStorage);
        favoriteVideo.setUlogin(user);
        favoriteVideoRepo.save(favoriteVideo);

        return new ResponseEntity<>("Video Added Successfully", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> removeFromFavorites(Integer videoId) {

        try{
            VideoStorage videoStorage = videoRepository.findById(videoId)
                    .orElseThrow(()-> new RuntimeException("Video Not Found"));

            Ulogin user = userService.getCurrentUser();

            FavoriteVideo favoriteVideo = favoriteVideoRepo.findFavoriteVideoAndUser(user,videoStorage)
                    .orElseThrow(()-> new RuntimeException("Video Is Not In Your Favorites"));
            favoriteVideoRepo.delete(favoriteVideo);
            return new ResponseEntity<>("Removed Successfully",HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Something went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Transactional
    public ResponseEntity<?> getAllFavoriteVideos() {
        try
        {
            Ulogin user = userService.getCurrentUser();
            List<VideoStorage> favoriteVideos = favoriteVideoRepo.findByUlogin(user).stream()
                    .map(FavoriteVideo::getVideoStorage)
                    .collect(Collectors.toList());


            if(favoriteVideos.isEmpty())
            {
                return new ResponseEntity<>("No Videos In Favorites",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(favoriteVideos,HttpStatus.OK);

        }catch (Exception e)
        {
            return new ResponseEntity<>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
