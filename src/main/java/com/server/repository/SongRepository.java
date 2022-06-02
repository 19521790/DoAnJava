package com.server.repository;


import com.server.entity.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends MongoRepository<Song,String> {
    Song findByName(String name);
}
