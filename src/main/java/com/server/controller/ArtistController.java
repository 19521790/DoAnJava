package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.entity.Artist;
import com.server.exception.ArtistException;
import com.server.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @PostMapping("/addArtist")
    public ResponseEntity addArtist(@RequestPart("artist") String artistString, @RequestPart("image") MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(artistService.addArtist(artistString, image));
        }catch (ArtistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }catch (JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/findArtistById/{id}")
    public Artist findArtistById(@PathVariable String id) {
        return artistService.findArtistById(id);
    }

    @GetMapping("/findAllArtists")
    public List<Artist> findArtists() {
        return artistService.findAllArtists();
    }

    @DeleteMapping("/deleteArtist/{id}")
    public ResponseEntity deleteArtist(@PathVariable String id) {
        try{
            artistService.deleteArtist(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete artist with id: "+id);
        }catch (ArtistException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/updateArtist")
    public Artist updateArtist(@RequestBody Artist artist) {
        return artistService.updateArtist(artist);
    }

    @PutMapping("/addSongToArtist")
    public Artist addSongToArtist(@RequestParam String idArtist, @RequestParam String idSong) {
        return artistService.addSongToArtist(idArtist, idSong);
    }
}
