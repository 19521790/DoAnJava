package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.exception.FileFormatException;
import com.server.exception.PlaylistException;
import com.server.exception.SongException;
import com.server.repository.PlaylistRepository;
import com.server.repository.SongRepository;
import com.server.repository.UserRepository;
import com.server.service.data.DataService;
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
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private DataService dataService;

    @Autowired
    private UserRepository userRepository;

    public Playlist addPlaylist(String playlistString, MultipartFile image) throws ConstraintViolationException, IOException, FileFormatException {
        ObjectMapper objectMapper = new ObjectMapper();
        Playlist playlist = objectMapper.readValue(playlistString, Playlist.class);

        playlist.setImage(dataService.storeData(image, ".jpg"));
        playlist.setCreatedAt(new Date(System.currentTimeMillis()));
        playlist.setTotalView(0);

        return playlistRepository.save(playlist);
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
            return playlistRepository.findSongFromPlaylist(id);
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
            dataService.deleteData(playlist.getImage());
            playlistRepository.deleteById(id);
        }
    }

    public Playlist updatePlaylist(String playlistString,MultipartFile image) throws IOException, FileFormatException, PlaylistException {
        ObjectMapper objectMapper = new ObjectMapper();
        Playlist playlist = objectMapper.readValue(playlistString, Playlist.class);

        Playlist playlistToUpdate = playlistRepository.findById(playlist.getId()).orElse(null);
        System.out.println(playlistToUpdate);
        if(playlistToUpdate!=null){
            playlistToUpdate.setName(playlist.getName() != null ? playlist.getName() : playlistToUpdate.getName());
            playlistToUpdate.setImage(dataService.storeData(image, ".jpg"));
            return playlistRepository.save(playlistToUpdate);
        }else{
            throw new PlaylistException(PlaylistException.NotFoundException(playlist.getId()));
        }
    }

    public void addSongToPlaylist(String idPlaylist, String idSong) throws PlaylistException, SongException {
        Playlist playlist = playlistRepository.findById(idPlaylist).orElse(null);
        Song song = songRepository.findById(idSong).orElse(null);

        if (playlist == null) {
            throw new PlaylistException(PlaylistException.NotFoundException(idPlaylist));
        } else if (song == null) {
            throw new SongException(SongException.NotFoundException(idSong));
        } else {
            playlistRepository.addSongToPlaylist(idPlaylist, idSong);
        }
    }

    public void removeSongFromPlaylist(String idPlaylist,String idSong) throws PlaylistException, SongException {
        Playlist playlist = playlistRepository.findById(idPlaylist).orElse(null);
        Song song = songRepository.findById(idSong).orElse(null);

        if (playlist == null) {
            throw new PlaylistException(PlaylistException.NotFoundException(idPlaylist));
        } else if (song == null) {
            throw new SongException(SongException.NotFoundException(idSong));
        } else {
            playlistRepository.removeSongFromPlaylist(idPlaylist, idSong);
        }
    }
}
