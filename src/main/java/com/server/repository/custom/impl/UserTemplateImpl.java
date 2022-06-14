package com.server.repository.custom.impl;

import com.server.entity.*;
import com.server.entity.object.SongLastListen;
import com.server.repository.custom.UserTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newUpdate;

public class UserTemplateImpl implements UserTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addLastListenSong(String idUser, String idSong) {
        Query query = new Query(Criteria.where("_id").is(idUser));
        Update update = new Update();

        update.addToSet("idLastListenSongs", new ObjectId(idSong));
        System.out.println(mongoTemplate.updateFirst(query, update, "users"));

        System.out.println(countNumberLastListenSong(idUser));

        if (countNumberLastListenSong(idUser) > 10) {
            mongoTemplate.updateFirst(query, new Update().pull("idLastListenSongs", 0), "users");
        }
    }

    @Override
    public User getLastListenSong(String idUser) {
        Aggregation aggregation = newAggregation(
                Aggregation.match(
                        Criteria.where("_id").is(idUser)
                ),
                Aggregation.lookup("songs", "idLastListenSongs", "_id", "lastListenSongs")
        );
        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "users", User.class);
        System.out.println(results);
        System.out.println(results.getMappedResults());
        return results.getMappedResults().get(0);
    }

    @Override
    public long countNumberLastListenSong(String idUser) {
        Query query = new Query(Criteria.where("idLastListenSongs"));
        return mongoTemplate.count(query, "users");
    }

    @Override
    public void followArtist(String idUser, String idArtist) {
        Query query = new Query(Criteria.where("_id").is(idUser));
        Update update = new Update();
        update.addToSet("idArtists", new ObjectId(idArtist));
        System.out.println(mongoTemplate.updateFirst(query, update, "users"));
    }

    @Override
    public void saveAlbum(String idUser, String idAlbum) {
        Query query = new Query(Criteria.where("_id").is(idUser));
        Update update = new Update();
        update.addToSet("idAlbums", new ObjectId(idAlbum));
        System.out.println(mongoTemplate.updateFirst(query, update, "users"));
    }

    @Override
    public void unfollowArtist(String idUser, String idArtist) {
        Query query = new Query(Criteria.where("_id").is(idUser));
        Update update = new Update();
        update.pull("idArtists", new ObjectId(idArtist));
        System.out.println(mongoTemplate.updateFirst(query, update, "users"));
    }
    @Override
    public void removeAlbum(String idUser, String idALbum) {
        Query query = new Query(Criteria.where("_id").is(idUser));
        Update update = new Update();
        update.pull("idAlbums", new ObjectId(idALbum));
        System.out.println(mongoTemplate.updateFirst(query, update, "users"));
    }

    @Override
    public List<Album> findSavedAlbumFromUser(String idUser){
        Aggregation aggregation = newAggregation(
                Aggregation.match(
                        Criteria.where("_id").is(idUser)
                ),
                Aggregation.lookup("albums", "idAlbums", "_id", "albums")
        );
        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "users", User.class);
        System.out.println(results);
        System.out.println(results.getMappedResults());
        return results.getMappedResults().get(0).getAlbums();
    }

    @Override
    public List<Artist> findFollowedArtistFromUser(String idUser){
        Aggregation aggregation = newAggregation(
                Aggregation.match(
                        Criteria.where("_id").is(idUser)
                ),
                Aggregation.lookup("artists", "idArtists", "_id", "artists")
        );
        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "users", User.class);
        System.out.println(results);
        System.out.println(results.getMappedResults());
        return results.getMappedResults().get(0).getArtists();
    }
}
