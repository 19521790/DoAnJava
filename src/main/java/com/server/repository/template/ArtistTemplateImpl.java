package com.server.repository.template;

import com.server.model.Artist;
import com.server.repository.template.ArtistTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ArtistTemplateImpl implements ArtistTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Artist> findByNameRegex(String name){
        Query query = new Query(Criteria.where("name").regex(name,"i"));
        return mongoTemplate.find(query,Artist.class,"artists");
    }
}
