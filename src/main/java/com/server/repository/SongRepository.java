package com.server.repository;


import com.server.model.Song;
import com.server.repository.template.SongTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends MongoRepository<Song,String>, SongTemplate {
    Song findSongByName (String name);
//    List<Song> findByNameRegex(String name);
}
