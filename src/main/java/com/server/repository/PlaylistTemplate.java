package com.server.repository;


import com.server.entity.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistTemplate extends MongoRepository<Playlist, String>, com.server.repository.custom.PlaylistTemplate {
    Playlist findByName(String name);

}
