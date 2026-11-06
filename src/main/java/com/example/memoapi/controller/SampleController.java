package com.example.memoapi.controller;

import com.example.memoapi.dto.SampleMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/sample")
    public String sample(){
        return "hello springboot API !!";
    }

    @GetMapping("/sample/api")
    public ResponseEntity<SampleMessage> sampleApi(){
        SampleMessage sampleMessage = new SampleMessage();
        sampleMessage.setId(100);
        sampleMessage.setMessage("hello springboot API !!!");
        return new ResponseEntity<>(sampleMessage, HttpStatus.OK);
    }
}
