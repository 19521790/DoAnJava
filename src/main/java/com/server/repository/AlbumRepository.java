package com.server.repository;


import com.server.model.Album;
import com.server.repository.template.AlbumTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album,String>, AlbumTemplate {
    Album findByName(String name);
}
