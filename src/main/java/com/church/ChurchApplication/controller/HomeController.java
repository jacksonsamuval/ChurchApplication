package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.entity.Ulogin;
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


}
