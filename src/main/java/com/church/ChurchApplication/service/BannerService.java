package com.church.ChurchApplication.service;

import com.church.ChurchApplication.entity.Banners;
import com.church.ChurchApplication.entity.ProfilePicture;
import com.church.ChurchApplication.repo.BannersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    @Autowired
    private BannersRepo bannersRepo;

    public ResponseEntity<?> saveBanner(MultipartFile imageFile) throws IOException {
        try
        {
            if(bannersRepo.count()>=5)
            {
                Banners oldestBanner = bannersRepo.findTopByOrderByCreatedAtAsc();
                bannersRepo.delete(oldestBanner);
            }

            Banners banners = new Banners();
            banners.setBannerName(imageFile.getOriginalFilename());
            banners.setBannerType(imageFile.getContentType());
            banners.setBannerImage(imageFile.getBytes());
            bannersRepo.save(banners);
            return new ResponseEntity<>("Added Successfully", HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public ResponseEntity<?> deleteBanner(Integer bannerId) {
        try{
            Banners banners = bannersRepo.findById(bannerId).orElseThrow(()->
                    new RuntimeException("Banner Not Availaible"));
            bannersRepo.delete(banners);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        } catch (Exception e ){
            return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public ResponseEntity<?> getBanners() {
        try{
            List<Banners> banners = bannersRepo.findTop5ByOrderByCreatedAtDesc();
            return new ResponseEntity<>(banners,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("network error");
        }
    }

    public ResponseEntity<?> getALLBanners() {
        try {
            List<Banners> banners = bannersRepo.findAll();
            return new ResponseEntity<>(banners,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
