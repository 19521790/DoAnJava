package com.server.repository;


import com.server.entity.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<Genre,String> {
    Genre findByName(String name);
}
