package com.server.repository.custom.impl;

import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.repository.custom.PlaylistTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class PlaylistTemplateImpl implements PlaylistTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addSongToPlaylist(String idPlaylist, String idSong){
        Query query = new Query(Criteria.where("_id").is(idPlaylist));
        Update update = new Update();
        update.addToSet("idSongs",new ObjectId(idSong));
        System.out.println(mongoTemplate.updateFirst(query,update,"playlists"));
    };
}
