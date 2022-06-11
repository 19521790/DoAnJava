package com.server.repository;


import com.server.entity.Song;
import com.server.repository.custom.SongTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends MongoRepository<Song,String>, SongTemplate {
    Song findByName(String name);
}
