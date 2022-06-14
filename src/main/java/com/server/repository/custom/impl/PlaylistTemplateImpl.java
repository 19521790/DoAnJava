package com.server.repository.custom.impl;

import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.entity.User;
import com.server.repository.custom.PlaylistTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public class PlaylistTemplateImpl implements PlaylistTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addSongToPlaylist(String idPlaylist, String idSong) {
        Query query = new Query(Criteria.where("_id").is(idPlaylist));
        Update update = new Update();
        update.addToSet("idSongs", new ObjectId(idSong));
        System.out.println(mongoTemplate.updateFirst(query, update, "playlists"));
    }

    ;

    @Override
    public void addPlaylistToUser(String idUser, String idPlaylist) {
        Query query = new Query(Criteria.where("_id").is(idPlaylist));
        Update update = new Update();
        update.addToSet("idUser", new ObjectId(idUser));
        System.out.println(mongoTemplate.updateFirst(query, update, "playlists"));
    }

    @Override
    public Playlist findSongFromPlaylist(String idPlaylist) {
        Aggregation aggregation = newAggregation(
                Aggregation.match(
                        Criteria.where("_id").is(idPlaylist)
                ),
                Aggregation.lookup("songs", "idSongs", "_id", "songs")
        );
        AggregationResults<Playlist> results = mongoTemplate.aggregate(aggregation, "playlists", Playlist.class);
        System.out.println(results);
        System.out.println(results.getMappedResults());
        return results.getMappedResults().get(0);
    }

    @Override
    public List<Playlist> findPlaylistFromUser(String idUser) {
        Query query = new Query(Criteria.where("idUser").is(idUser));
        return mongoTemplate.find(query, Playlist.class, "playlists");
    }
}
