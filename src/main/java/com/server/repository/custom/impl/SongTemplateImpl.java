package com.server.repository.custom.impl;

import com.server.entity.Song;
import com.server.repository.custom.SongTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class SongTemplateImpl implements SongTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Song> findSongByAlbum(String idAlbum){
        Query query = new Query(Criteria.where("album._id").is(new ObjectId(idAlbum)));
        return mongoTemplate.find(query,Song.class);
    }

    @Override
    public List<Song> findSongByArtist(String idArtist){
        Query query = new Query(Criteria.where("artists._id").is(new ObjectId(idArtist)));
        return mongoTemplate.find(query,Song.class);
    }
}
