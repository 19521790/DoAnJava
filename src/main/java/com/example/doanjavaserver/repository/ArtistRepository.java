package com.example.doanjavaserver.repository;


import com.example.doanjavaserver.entity.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist,String> {
}
