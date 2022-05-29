package com.example.doanjavaserver.controller;

import com.example.doanjavaserver.entity.Song;
import com.example.doanjavaserver.entity.User;
import com.example.doanjavaserver.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/addSong")
    public ResponseEntity addSong(@RequestBody Song song) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.addSong(song));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/addSongs")
    public ResponseEntity addSongs(@RequestBody List<Song> songs) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.addSongs(songs));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/findSongById/{id}")
    public ResponseEntity findSongById(@PathVariable String id) {
        Song song = songService.findSongById(id);
        if(song != null){
            return ResponseEntity.status(HttpStatus.OK).body(song);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not exist");
        }
    }

    @GetMapping("/findAllSongs")
    public ResponseEntity findAllSongs() {
        List<Song> songs = songService.findAllSongs();
        if (songs.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(songs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No songs available");
        }
    }

    @PutMapping("/updateSong")
    public Song updateSong(@RequestBody Song song) {
        return songService.updateSong(song);
    }

    @DeleteMapping("/deleteSong/{id}")
    public String deleteSong(@RequestParam String id) {
        return songService.deleteSong(id);
    }
}
