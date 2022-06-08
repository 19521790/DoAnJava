package com.server.repository.custom.impl;

import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.repository.custom.PlaylistTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class PlaylistTemplateImpl implements PlaylistTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Playlist addSongToPlaylist(Playlist playlist, Song song){
       mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(playlist.getId())),
                new Update().push("songs",song),
                "playlists"
        );
       return playlist;
    };
}
