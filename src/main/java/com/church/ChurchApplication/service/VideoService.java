package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.FavoriteVideo;
import com.church.ChurchApplication.entity.VideoStorage;
import com.church.ChurchApplication.repo.FavoriteVideoRepo;
import com.church.ChurchApplication.repo.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private FavoriteVideoRepo favoriteVideoRepo;

    public VideoStorage saveVideo(MultipartFile videoFile) throws IOException {
        VideoStorage videoStorage = new VideoStorage();
        videoStorage.setVideoName(videoFile.getOriginalFilename());
        videoStorage.setVideoType(videoFile.getContentType());
        videoStorage.setVideoData(videoFile.getBytes());

        return videoRepository.save(videoStorage);

    }

    public ResponseEntity<?> findVideoById(Integer videoId) {

        try{
            Optional<VideoStorage> videoStorage = videoRepository.findById(videoId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(videoStorage.get().getVideoType()));
            headers.setContentLength(videoStorage.get().getVideoData().length);

            return new ResponseEntity<>(videoStorage.get().getVideoData(), headers, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteVideo(Integer videoId) {
        VideoStorage videoStorage = videoRepository.findById(videoId)
                .orElseThrow(()-> new RuntimeException("Video Not Found"));
        videoStorage.setVideoData(null);
        videoStorage.setVideoType(null);
        videoStorage.setVideoName("Video Deleted");
        videoRepository.save(videoStorage);
        return new ResponseEntity<>("Video Deleted SuccessFully",HttpStatus.OK);
    }
}
