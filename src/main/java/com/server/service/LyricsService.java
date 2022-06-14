package com.server.service;

import com.server.entity.Lyrics;
import com.server.repository.LyricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LyricsService {
    @Autowired
    private LyricsRepository lyricsRepository;

    public Lyrics addLyrics(Lyrics lyrics){
        return lyricsRepository.save(lyrics);
    }
}
