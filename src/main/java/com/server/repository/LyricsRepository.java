package com.server.repository;

import com.server.model.Lyrics;
import com.server.repository.template.LyricsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LyricsRepository extends MongoRepository<Lyrics,String>, LyricsTemplate {
}
