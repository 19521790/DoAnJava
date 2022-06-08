package com.server.repository;


import com.server.entity.Artist;
import com.server.repository.custom.ArtistTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist,String>, ArtistTemplate {
    Artist findByName(String name);
}
