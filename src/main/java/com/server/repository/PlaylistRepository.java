package com.server.repository;


import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.repository.custom.CustomPlaylistRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, String>, CustomPlaylistRepository {
    Playlist findByName(String name);

}
