package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.server.entity.Album;
import com.server.entity.Artist;
import com.server.entity.Song;
import com.server.entity.object.AlbumOtd;
import com.server.entity.result.SongResult;
import com.server.exception.AlbumException;
import com.server.exception.ArtistException;
import com.server.exception.FileFormatException;
import com.server.exception.SongException;
import com.server.repository.AlbumRepository;
import com.server.repository.ArtistRepository;
import com.server.repository.SongRepository;
import com.server.service.data.DataService;
import com.server.service.drive.GoogleDriveService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.*;
import java.util.*;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private DataService dataService;

    @Autowired
    private GoogleDriveService driveService;

    public Song addSong(String songString, MultipartFile file) throws ConstraintViolationException, SongException, IOException, FileFormatException {
        ObjectMapper objectMapper = new ObjectMapper();
        Song song = objectMapper.readValue(songString, Song.class);

        Song songOptional = songRepository.findSongByName(song.getName());

        if (songOptional != null
                && songOptional.getAlbum().getId().equals(song.getAlbum().getId())
                && songOptional.getName().equals(song.getName())) {
            throw new SongException(SongException.SongAlreadyExist(song.getName()));
        } else {
            AlbumOtd album = new AlbumOtd().albumToAlbumOtd(albumRepository.findById(song.getAlbum().getId()).get());
            song.setFile(dataService.storeData(file, ".m4a"));
            song.setAlbum(album);
            song.setWeekView(0);
            song.setTotalView(0);
            song.setCreatedAt(new Date(System.currentTimeMillis()));

            return songRepository.save(song);
        }
    }

    public Song findSongById(String id) throws SongException, IOException, FileFormatException {
        Song song = songRepository.findById(id).orElse(null);

        if (song != null) {
            return song;
        } else {
            throw new SongException(SongException.NotFoundException(id));
        }
    }

    public List<Song> findAllSongs() {
        List<Song> songs = songRepository.findAll();
        if (songs.size() > 0) {
            return songs;
        } else {
            return new ArrayList<Song>();
        }
    }

    public void deleteSong(String id) throws SongException, FileFormatException {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) {
            throw new SongException(SongException.NotFoundException(id));
        } else {
            dataService.deleteData(song.getFile());
            songRepository.deleteById(id);
        }
    }

    public Song updateSong(String songString, MultipartFile file) throws ConstraintViolationException, SongException, IOException, FileFormatException {
        ObjectMapper objectMapper = new ObjectMapper();
        Song song = objectMapper.readValue(songString, Song.class);

        Song songToUpdate = songRepository.findById(song.getId()).get();

        if (songToUpdate != null) {
            songToUpdate.setName(song.getName() != null ? song.getName() : songToUpdate.getName());
            songToUpdate.setDuration(song.getDuration());
            songToUpdate.setArtists(song.getArtists() != null ? song.getArtists() : songToUpdate.getArtists());
            songToUpdate.setGenres(song.getGenres() != null ? song.getGenres() : songToUpdate.getGenres());
            songToUpdate.setAlbum(song.getAlbum() != null ? song.getAlbum() : songToUpdate.getAlbum());

            if (file.getOriginalFilename() != null) {
                dataService.deleteData(songToUpdate.getFile());
                songToUpdate.setFile(dataService.storeData(file, ".m4a"));
            }
            songToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            return songRepository.save(songToUpdate);
        } else {
            throw new SongException(SongException.NotFoundException(song.getId()));
        }
    }

    public void countPlay(String idSong) throws SongException {
        Song song = songRepository.findById(idSong).orElse(null);

        if (song != null) {
            song.setWeekView(song.getWeekView() + 1);
            song.setTotalView(song.getTotalView() + 1);
            songRepository.save(song);
        } else {
            throw new SongException(SongException.NotFoundException(idSong));
        }
    }

    public Song findSongByName(String name) throws SongException {
        Song song = songRepository.findSongByName(name);

        if (song != null) {
            return song;
        } else {
            throw new SongException(SongException.NotFoundException(name));
        }
    }

    public List<Song> findSongByGenre(String genre) {
        return songRepository.findSongByGenre(genre);
    }
}
