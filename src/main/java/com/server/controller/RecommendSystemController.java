package com.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Song;
import com.server.entity.result.SongResult;
import com.server.service.SongService;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendSystemController {
    @Autowired
    private SongService songService;

    @PostMapping("/getRecommendedRecentlySong")
    public ResponseEntity getRecommendSongs(){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8000";
        ObjectMapper objectMapper = new ObjectMapper();
        // create request body
        List<SongResult> songs = new ArrayList<SongResult>();
        SongResult song1 = new SongResult();
        song1.setName("Come As You Are");
        song1.setYear(1991);
        songs.add(song1);

        SongResult song2 = new SongResult();
        song2.setName("Smells Like Teen Spirit");
        song2.setYear(1991);
        songs.add(song2);


        //set header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<SongResult>> entity = new HttpEntity<List<SongResult>>(songs,headers);
        System.out.println(entity);
        // send request and parse result
        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.POST, entity, String.class);
        System.out.println(response);
        return response;
    }
}
