package com.example.doanjavaserver.controller;

import com.example.doanjavaserver.entity.Playlist;
import com.example.doanjavaserver.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Playlist findPlaylistById(@PathVariable String id) {
        return playlistService.findPlaylistById(id);
    }

    @GetMapping("/findAllPlaylists")
    public List<Playlist> findPlaylists() {
        return playlistService.findAllPlaylists();
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
            @RequestParam(name="idPlaylist") String idPlaylist,
            @RequestParam(name="idSong") String idSong
    ) {
        return playlistService.addSongToPlaylist(idPlaylist,idSong);
    }
}
