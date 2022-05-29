package com.example.doanjavaserver.controller;

import com.example.doanjavaserver.entity.Album;
import com.example.doanjavaserver.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @PostMapping("/addAlbum")
    public Album addAlbum(@RequestBody Album album){
        return albumService.addAlbum(album);
    }

    @GetMapping("/findAlbumById/{id}")
    public Album findAlbumById(@PathVariable String id){
        return albumService.findAlbumById(id);
    }

    @GetMapping("/findAllAlbums")
    public List<Album> findAllAlbums(){
        return albumService.findAllAlbums();
    }

    @PutMapping("/updateAlbum")
    public Album updateAlbum(@RequestBody Album album){
        return albumService.updateAlbum(album);
    }

    @DeleteMapping("/deleteAlbum/{id}")
    public String deleteAlbum(@PathVariable String id){
        return albumService.deleteAlbum(id);
    }
}
