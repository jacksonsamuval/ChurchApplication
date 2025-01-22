package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.VideoStorage;
import com.church.ChurchApplication.repo.FavoriteVideoRepo;
import com.church.ChurchApplication.repo.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private FavoriteVideoRepo favoriteVideoRepo;

    public ResponseEntity<?> findVideoById(Integer videoId) {

        try{
            Optional<VideoStorage> videoStorage = videoRepository.findById(videoId);
            return new ResponseEntity<>(videoStorage, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteVideo(Integer videoId) {
        VideoStorage videoStorage = videoRepository.findById(videoId)
                .orElseThrow(()-> new RuntimeException("Video Not Found"));
        videoStorage.setVideoDescription(null);
        videoStorage.setUrl(null);
        videoStorage.setVideoName("Video Deleted");
        videoRepository.save(videoStorage);
        return new ResponseEntity<>("Video Deleted SuccessFully",HttpStatus.OK);
    }

        public VideoStorage saveVideo(VideoStorage videoStorage) throws IOException {
            return videoRepository.save(videoStorage);
    }


    public ResponseEntity<?> getRecentVideos() {
        List<VideoStorage> videoStorageList = videoRepository.findTop5ByOrderByCreatedAtDesc();
        return new ResponseEntity<>(videoStorageList,HttpStatus.OK);
    }

    public ResponseEntity<?> getAllVideo() {
        try{
            List<VideoStorage> videos = videoRepository.findAll();
            return new ResponseEntity<>(videos,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> editVideo(Integer videoId, String videoName, String videoDescription, String videoUrl) {
        try{
            VideoStorage videoStorage = videoRepository.findById(videoId).orElseThrow(()-> new RuntimeException("Not Found"));
            if(videoStorage!=null){
                videoStorage.setUrl(videoUrl);
                videoStorage.setVideoDescription(videoDescription);
                videoStorage.setVideoName(videoName);
                videoRepository.save(videoStorage);
            }
            return new ResponseEntity<>(videoStorage,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

//Save Video Directly to db

//    public VideoStorage saveVideo(MultipartFile videoFile) throws IOException {
//        VideoStorage videoStorage = new VideoStorage();
//        videoStorage.setVideoName(videoFile.getOriginalFilename());
//        videoStorage.setVideoType(videoFile.getContentType());
//        videoStorage.setVideoData(videoFile.getBytes());
//
//        return videoRepository.save(videoStorage);
//
//    }

//getVideo By Id

//public ResponseEntity<?> findVideoById(Integer videoId) {
//
//    try{
//        Optional<VideoStorage> videoStorage = videoRepository.findById(videoId);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(videoStorage.get().getVideoType()));
//        headers.setContentLength(videoStorage.get().getVideoData().length);
//
//        return new ResponseEntity<>(videoStorage.get().getVideoData(), headers, HttpStatus.OK);
//    }catch (Exception e)
//    {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}

