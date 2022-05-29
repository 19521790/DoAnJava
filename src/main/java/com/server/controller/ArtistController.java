package com.server.controller;

import com.server.entity.Artist;
import com.server.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @PostMapping("/addArtist")
    public Artist addArtist(@RequestBody Artist artist) {
        return artistService.addArtist(artist);
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
    public String deleteArtist(@PathVariable String id) {
        return artistService.deleteArtist(id);
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
