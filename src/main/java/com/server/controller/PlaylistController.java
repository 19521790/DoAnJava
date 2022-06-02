package com.server.controller;

import com.server.entity.Playlist;
import com.server.exception.PlaylistException;
import com.server.exception.SongException;
import com.server.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/addPlaylist")
    public Playlist addPlaylist(@RequestBody Playlist playlist) {
        return playlistService.addPlaylist(playlist);
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
    public String deletePlaylist(@PathVariable String id) {
        return playlistService.deletePlaylist(id);
    }

    @PutMapping("/updatePlaylist")
    public Playlist updatePlaylist(@RequestBody Playlist playlist) {
        return playlistService.updatePlaylist(playlist);
    }

    @PutMapping("/addSongToPlaylist")
    public Playlist addSongToPlaylist(
            @RequestParam(name = "idPlaylist") String idPlaylist,
            @RequestParam(name = "idSong") String idSong
    ) {
        return playlistService.addSongToPlaylist(idPlaylist, idSong);
    }
}
