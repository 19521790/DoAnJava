package com.example.doanjavaserver.repository;


import com.example.doanjavaserver.entity.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist,String> {
}
