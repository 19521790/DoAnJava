package com.server.repository;


import com.server.entity.Album;
import com.server.entity.Artist;
import com.server.repository.custom.AlbumTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends MongoRepository<Album,String>, AlbumTemplate {
    Album findByName(String name);
}
