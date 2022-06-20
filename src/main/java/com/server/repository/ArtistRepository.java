package com.server.repository;


import com.server.model.Artist;
import com.server.repository.template.ArtistTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist,String>, ArtistTemplate {
    Artist findByName(String name);
}
