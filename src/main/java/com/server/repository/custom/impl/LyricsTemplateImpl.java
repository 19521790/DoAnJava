package com.server.repository.custom.impl;

import com.server.entity.Lyrics;
import com.server.repository.custom.LyricsTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class LyricsTemplateImpl implements LyricsTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Lyrics addLyrics(Lyrics lyrics){
        String idSong = lyrics.getIdSong();
        String idLyrics = mongoTemplate.save(lyrics).getId();
        Query query = new Query(Criteria.where("id").is(idLyrics));
        Update update = new Update();
        update.set("idSong",new ObjectId(idSong));
//        mongoTemplate.updateFirst(query,update);
        return new Lyrics();
    }

    @Override
    public Lyrics findLyricsBySong(String idSong){
        Query query = new Query(Criteria.where("idSong").is(new ObjectId(idSong)));
        Lyrics result = mongoTemplate.findOne(query,Lyrics.class,"lyrics");
        System.out.println(result);
        return result;
    }
}
