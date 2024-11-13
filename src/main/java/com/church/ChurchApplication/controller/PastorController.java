package com.church.ChurchApplication.controller;

import com.church.ChurchApplication.dto.PastorVerify;
import com.church.ChurchApplication.entity.PastorId;
import com.church.ChurchApplication.service.PastorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/pastor/")
public class PastorController {

    @Autowired
    private PastorService pastorService;

    @PostMapping("pastorVerify")
    public ResponseEntity<String> pastorVerify(@RequestBody PastorVerify pastorVerify)
    {
        return pastorService.verifyPastorId(pastorVerify);
    }

    @PostMapping("addPastorId")
    public ResponseEntity<String> addPastorId(@RequestBody PastorId pastorId)
    {
        return pastorService.addPastorId(pastorId);
    }

}
