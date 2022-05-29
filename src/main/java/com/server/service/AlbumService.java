package com.server.service;

import com.server.entity.Album;
import com.server.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public Album addAlbum(Album album){
        return albumRepository.save(album);
    }

    public Album findAlbumById(String id){
        return albumRepository.findById(id).orElse(null);
    }

    public List<Album> findAllAlbums(){
        return  albumRepository.findAll();
    }

    public Album updateAlbum(Album album){
        return albumRepository.save(album);
    }

    public String deleteAlbum(String id){
        albumRepository.deleteById(id);
        return ("Album has been deleted: "+id);
    }
}
