package com.server.repository.custom.impl;

import com.server.entity.Lyrics;
import com.server.repository.custom.LyricsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class LyricsTemplateImpl implements LyricsTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Lyrics addLyrics(Lyrics lyrics){

        return new Lyrics();
    }
}
