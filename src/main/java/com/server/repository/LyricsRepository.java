package com.server.repository;

import com.server.entity.Lyrics;
import com.server.repository.custom.LyricsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LyricsRepository extends MongoRepository<Lyrics,String>, LyricsTemplate {
    Lyrics findByIdSong(String idSong);
}
