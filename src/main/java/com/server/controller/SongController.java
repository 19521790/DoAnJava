package com.server.controller;

import com.server.model.Lyrics;
import com.server.model.Song;
import com.server.exception.FileFormatException;
import com.server.exception.SongException;
import com.server.service.LyricsService;
import com.server.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private LyricsService lyricsService;

    @PostMapping("/addSong")
    public ResponseEntity addSong(
            @RequestPart("song") String songString, @RequestPart("file") MultipartFile file
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.addSong(songString, file));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/findSongById/{id}")
    public ResponseEntity findSongById(@PathVariable String id) throws SongException, IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.findSongById(id));
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/findAllSongs")
    public ResponseEntity findAllSongs() {
        List<Song> songs = songService.findAllSongs();
        return ResponseEntity.status(songs.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(songs);
    }

    @PutMapping("/updateSong")
    public ResponseEntity updateSong(@RequestPart("song") String songString
            , @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.updateSong(songString, file));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException | FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }

    }

    @DeleteMapping("/deleteSong/{id}")
    public ResponseEntity deleteSong(@PathVariable String id) {
        try {
            songService.deleteSong(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete song " + id);
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @PutMapping("/countPlay/{id}")
    public ResponseEntity countPlay(@PathVariable String id) {
        try {
            songService.countPlay(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully count 1 play for song " + id);
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findSongByName/{name}")
    public ResponseEntity findSongByName(@PathVariable String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.findSongByName(name));
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/addLyrics")
    public ResponseEntity addLyrics(@RequestBody Lyrics lyrics) {
        return ResponseEntity.status(HttpStatus.OK).body(lyricsService.addLyrics(lyrics));
    }

    @GetMapping("/findLyricsBySong/{idSong}")
    public ResponseEntity findLyricsBySong(@PathVariable String idSong) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(lyricsService.findLyricsBySong(idSong));
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findSongByGenre/{genre}")
    public ResponseEntity findSongByGenre(@PathVariable String genre) {
        return ResponseEntity.status(HttpStatus.OK).body(songService.findSongByGenre(genre));
    }
}

