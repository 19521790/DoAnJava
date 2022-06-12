package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.entity.Playlist;
import com.server.exception.FileFormatException;
import com.server.exception.PlaylistException;
import com.server.exception.SongException;
import com.server.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/addPlaylist")
    public ResponseEntity addPlaylist(@RequestPart("playlist") String playlistString, @RequestPart("image") MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.addPlaylist(playlistString, image));
        } catch (FileFormatException | IOException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/findPlaylistById/{id}")
    public ResponseEntity findPlaylistById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(playlistService.findPlaylistById(id));
        } catch (PlaylistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findAllPlaylists")
    public ResponseEntity findPlaylists() {
        List<Playlist> playlists = playlistService.findAllPlaylists();
        return ResponseEntity.status(playlists.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(playlists);
    }

    @DeleteMapping("/deletePlaylist/{id}")
    public ResponseEntity deletePlaylist(@PathVariable String id) {
        try {
            playlistService.deletePlaylist(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete playlist with id " + id);
        } catch (PlaylistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @PutMapping("/updatePlaylist")
    public Playlist updatePlaylist(@RequestBody Playlist playlist) {
        return playlistService.updatePlaylist(playlist);
    }

//    @PutMapping("/addSongToPlaylist")
//    public Playlist addSongToPlaylist(
//            @RequestParam(name = "idPlaylist") String idPlaylist,
//            @RequestParam(name = "idSong") String idSong
//    ) {
//        playlistService.addSongToPlaylist(idPlaylist, idSong);
//    }
}
