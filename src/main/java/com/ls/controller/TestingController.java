package com.ls.controller;

import com.ls.annotation.LogPerf;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {

    @LogPerf
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
