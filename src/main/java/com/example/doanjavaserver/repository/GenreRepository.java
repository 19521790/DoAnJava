package com.example.doanjavaserver.repository;


import com.example.doanjavaserver.entity.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<Genre,String> {
}
