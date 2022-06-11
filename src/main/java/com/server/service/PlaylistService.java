package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.entity.result.PlaylistResult;
import com.server.exception.PlaylistException;
import com.server.exception.SongException;
import com.server.repository.PlaylistTemplate;
import com.server.repository.SongRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistTemplate playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GoogleDriveService driveService;

    private String playlistImgFolderId = "1V_RzyQ-L04PNkmLMrOsGVEABprMh6IqK";

    public Playlist addPlaylist(String playlistString, MultipartFile image) throws ConstraintViolationException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Playlist playlist = objectMapper.readValue(playlistString, Playlist.class);

        playlist.setImage(driveService.uploadFile(playlist.getName(), image, "image/jpeg", playlistImgFolderId).getId());
        playlist.setCreatedAt(new Date(System.currentTimeMillis()));
        return playlistRepository.save(playlist);
    }

    public PlaylistResult findPlaylistById(String id) throws PlaylistException {
        Playlist playlist = playlistRepository.findById(id).orElse(null);

        if (playlist == null) {
            throw new PlaylistException(PlaylistException.NotFoundException(id));
        } else {
            return new PlaylistResult().playlistToPlaylistResult(playlist);
        }
    }

    public List<Playlist> findAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        if (playlists.size() > 0) {
            return playlists;
        } else {
            return new ArrayList<Playlist>();
        }
    }

    public void deletePlaylist(String id) throws PlaylistException {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        if (playlist == null) {
            throw new PlaylistException(PlaylistException.NotFoundException(id));
        } else {
            driveService.deleteFile(playlist.getImage());
            songRepository.deleteById(id);
        }
    }

    public Playlist updatePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void addSongToPlaylist(String idPlaylist, String idSong) {
        Playlist playlist = playlistRepository.findById(idPlaylist).get();
        Song song = songRepository.findById(idSong).get();

        Song songToUpload = new Song();
        songToUpload.setName(song.getName());
        songToUpload.setArtists(song.getArtists());
        songToUpload.setDuration(song.getDuration());
        songToUpload.setAlbum(song.getAlbum());
        songToUpload.setFile(song.getFile());

//        playlistRepository.addSongToPlaylist(playlist, songToUpload);
    }
}
