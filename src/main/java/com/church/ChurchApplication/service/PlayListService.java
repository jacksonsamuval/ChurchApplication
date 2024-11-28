package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.PlayList;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.entity.VideoStorage;
import com.church.ChurchApplication.repo.PlayListRepo;
import com.church.ChurchApplication.repo.UserRepo;
import com.church.ChurchApplication.repo.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepo playListRepo;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<?> createPlayList(Integer userId, String playListName) {
        Ulogin user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        PlayList playList = new PlayList();
        playList.setName(playListName);
        playList.setUlogin(user);
        playListRepo.save(playList);
        return new ResponseEntity<>("PlayList created successfully", HttpStatus.CREATED);
    }

    public List<PlayList> getPlayListForUser(Integer userId) {
        Ulogin ulogin = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return playListRepo.findByUlogin(ulogin);
    }

    public ResponseEntity<?> addToPlayList(Integer playListId, Integer videoId) {
        try {
            PlayList playList = playListRepo.findById(playListId)
                    .orElseThrow(() -> new RuntimeException("PlayList Not Found"));
            VideoStorage videoStorage = videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video Not Found"));
            playList.getVideoStorageList().add(videoStorage);
            playListRepo.save(playList);
            return new ResponseEntity<>("Successfully Added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> removeFromPlayList(Integer playListId, Integer videoId) {
        PlayList playList = playListRepo.findById(playListId)
                .orElseThrow(() -> new RuntimeException("PlayList Not Found"));
        VideoStorage videoStorage = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video Not Found"));

        if (playList.getVideoStorageList().contains(videoStorage)) {
            playList.getVideoStorageList().remove(videoStorage);
            playListRepo.save(playList);
            return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Video Not Found in PlayList", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deletePlayList(Integer playListId) {
        try {
            PlayList playList = playListRepo.findById(playListId)
                    .orElseThrow(() -> new RuntimeException("PlayList Not Found"));
            playListRepo.deleteById(playListId);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
