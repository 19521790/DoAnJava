package com.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.exception.FileFormatException;
import com.server.exception.PlaylistException;
import com.server.repository.PlaylistTemplate;
import com.server.repository.SongRepository;
import com.server.repository.UserRepository;
import com.server.service.data.DataService;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
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
    private DataService dataService;

    @Autowired
    private UserRepository userRepository;

    public String addPlaylist(String playlistString, String idUser, MultipartFile image) throws ConstraintViolationException, IOException, FileFormatException {
        ObjectMapper objectMapper = new ObjectMapper();
        Playlist playlist = objectMapper.readValue(playlistString, Playlist.class);

        playlist.setImage(dataService.storeData(image, ".jpg"));
        playlist.setCreatedAt(new Date(System.currentTimeMillis()));
        playlist.setTotalView(0);

        //add playlist to user
        String idPlaylist = playlistRepository.save(playlist).getId();
        playlistRepository.addPlaylistToUser(idUser, idPlaylist);

        return idPlaylist;
    }

    public void addLikeAndDownloadPlaylist(String idUser) {
        Playlist like = new Playlist();
        Playlist download = new Playlist();
        like.setName("Like");
        download.setName("Download");

        like.setCreatedAt(new Date(System.currentTimeMillis()));
        playlistRepository.addPlaylistToUser(idUser, playlistRepository.save(like).getId());

        download.setCreatedAt(new Date(System.currentTimeMillis()));
        playlistRepository.addPlaylistToUser(idUser, playlistRepository.save(download).getId());
    }

    public Playlist findPlaylistById(String id) throws PlaylistException {
        Playlist playlist = playlistRepository.findById(id).orElse(null);

        if (playlist == null) {
            throw new PlaylistException(PlaylistException.NotFoundException(id));
        } else {
            return playlist;
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

    public void deletePlaylist(String id) throws PlaylistException, FileFormatException {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        if (playlist == null) {
            throw new PlaylistException(PlaylistException.NotFoundException(id));
        } else {
            dataService.deleteData(id);
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
