package com.server.controller;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/recommend")
public class RecommendSystemController {
    @GetMapping
    public ResponseEntity getRecommendSongs(){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8000/getRecommendedSongs";
        // create request body
        JSONObject request = new JSONObject();
        request.put("name", "Come As You Are");
        request.put("year", 1991);

        //set header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(),headers);

        // send request and parse result
        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.GET, entity, String.class);
        return response;
    }
}
