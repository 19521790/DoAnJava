package com.server.repository.template;

import com.server.model.Lyrics;
import com.server.repository.template.LyricsTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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
