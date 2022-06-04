package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.entity.Song;
import com.server.exception.SongException;
import com.server.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/addSong")
    public ResponseEntity addSong(
            @RequestPart("song") String songString, @RequestPart("image") MultipartFile image, @RequestPart("file") MultipartFile file
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.addSong(songString, file, image));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch(JsonProcessingException e){
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
    public ResponseEntity updateSong(@RequestBody Song song) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(songService.updateSong(song));
        }catch (ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }catch (SongException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @DeleteMapping("/deleteSong/{id}")
    public ResponseEntity deleteSong(@PathVariable String id) {
        try{
            songService.deleteSong(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete song with id: "+id);
        }catch (SongException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
