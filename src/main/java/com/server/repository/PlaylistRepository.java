package com.server.repository;


import com.server.entity.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, String>, com.server.repository.custom.PlaylistTemplate {
    Playlist findByIdUser (String idUser);
}
