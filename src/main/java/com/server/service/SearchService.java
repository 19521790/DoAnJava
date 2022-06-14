package com.server.service;

import com.server.entity.result.SearchResult;
import com.server.repository.SongRepository;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private SongRepository songRepository;

    public SearchResult search(String word) {
        SearchResult result = new SearchResult();
        result.setSongs(songRepository.findByNameRegex(".*" + word + ".*"));
        return result;
    }
}
