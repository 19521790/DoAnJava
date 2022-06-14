package com.server.repository;


import com.server.entity.Artist;
import com.server.repository.custom.ArtistTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends MongoRepository<Artist,String>, ArtistTemplate {
    Artist findByName(String name);
}
