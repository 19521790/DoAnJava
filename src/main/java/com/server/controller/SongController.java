package com.server.controller;

import com.server.entity.Song;
import com.server.exception.SongException;
import com.server.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
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
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/addSongs")
    public ResponseEntity addSongs(@RequestBody List<Song> songs) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.addSongs(songs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/findSongById/{id}")
    public ResponseEntity findSongById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.findSongById(id));
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findAllSongs")
    public ResponseEntity findAllSongs() {
        List<Song> songs = songService.findAllSongs();
        return ResponseEntity.status(songs.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(songs);
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