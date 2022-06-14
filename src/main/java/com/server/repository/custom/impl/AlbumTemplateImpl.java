package com.server.repository.custom.impl;

import com.server.entity.Album;
import com.server.entity.Artist;
import com.server.repository.AlbumRepository;
import com.server.repository.custom.AlbumTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class AlbumTemplateImpl implements AlbumTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Album> findByNameRegex(String name){
        Query query = new Query(Criteria.where("name").regex(name,"i"));
        return mongoTemplate.find(query, Album.class,"albums");
    }
}
