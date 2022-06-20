package com.server.repository;


import com.server.model.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, String>, com.server.repository.template.PlaylistTemplate {
    Playlist findByIdUser (String idUser);
}
