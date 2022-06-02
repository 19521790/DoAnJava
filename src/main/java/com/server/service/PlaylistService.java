package com.server.service;

import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.exception.PlaylistException;
import com.server.repository.PlaylistRepository;
import com.server.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    public Playlist addPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist findPlaylistById(String id) throws PlaylistException{
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        if(playlist==null){
            throw new PlaylistException(PlaylistException.NotFoundException(id));
        }else{
            return playlist;
        }
    }

    public List<Playlist> findAllPlaylists(){
        List<Playlist> playlists = playlistRepository.findAll();
        if(playlists.size()>0){
            return playlists;
        }else{
            return new ArrayList<Playlist>();
        }
    }

    public String deletePlaylist(String id) {
        playlistRepository.deleteById(id);
        return ("Playlist has been deleted: " + id);
    }

    public Playlist updatePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist addSongToPlaylist(String idPlaylist, String idSong) {
        Playlist playlist = playlistRepository.findById(idPlaylist).get();
        Song song = songRepository.findById(idSong).get();

        Song songToUpload = new Song();
        songToUpload.setName(song.getName());
        songToUpload.setArtists(song.getArtists());
        songToUpload.setDuration(song.getDuration());
        songToUpload.setAlbum(song.getAlbum());

        return playlistRepository.addSongToPlaylist(playlist,songToUpload);
    }
}
