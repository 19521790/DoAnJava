package com.server.repository.custom.impl;

import com.server.entity.Song;
import com.server.repository.custom.SongTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SongTemplateImpl implements SongTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Song> findSongByAlbum(String idAlbum) {
        Query query = new Query(Criteria.where("album._id").is(new ObjectId(idAlbum)));
        return mongoTemplate.find(query, Song.class);
    }

    @Override
    public List<Song> findSongByArtist(String idArtist) {
        Query query = new Query(Criteria.where("artists._id").is(new ObjectId(idArtist)));
        return mongoTemplate.find(query, Song.class);
    }

    @Override
    public List<Song> newUpdate() {

        Date date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3));

        Query query = new Query(Criteria.where("createdAt").lt(date))
                .with(Sort.by(Sort.Direction.DESC, "weekView"));

        List<Song> result = mongoTemplate.find(query, Song.class, "songs");

        List<Song> songs = new ArrayList<Song>();
        for (int i = 0; i < 5; i++) {
            songs.add(result.get(i));
        }
        return songs;
    }

    @Override
    public List<Song> topSongVietNam() {
        Query query = new Query(Criteria.where("genres").is("Viet Nam")).with(Sort.by(Sort.Direction.DESC, "weekView"));
        List<Song> result = mongoTemplate.find(query, Song.class, "songs");
        List<Song> songs = new ArrayList<Song>();
        for (int i = 0; i < 10; i++) {
            songs.add(result.get(i));
        }
        return songs;
    }

    @Override
    public List<Song> topSongGlobal() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "weekView"));
        List<Song> result = mongoTemplate.find(query, Song.class, "songs");
        List<Song> songs = new ArrayList<Song>();
        for (int i = 0; i < 10; i++) {
            songs.add(result.get(i));
        }
        return songs;
    }
}
