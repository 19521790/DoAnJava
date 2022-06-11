package com.server.repository.custom.impl;

import com.server.entity.User;
import com.server.repository.custom.UserTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UserTemplateImpl implements UserTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addPlaylistToUser(String idUser, String idPlaylist){
        Query query = new Query(Criteria.where("_id").is(idUser));
        Update update = new Update();
        update.addToSet("idPlaylists", new ObjectId(idPlaylist));
        System.out.println(mongoTemplate.updateFirst(query,update,"users"));
    }

    @Override
    public User findUserById(String idUser){
//        LookupOperation lookupOperation = LookupOperation.newLookup().from("playlist");
        return new User();
    }
}
