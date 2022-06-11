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
import com.server.exception.SongException;
import com.server.repository.AlbumRepository;
import com.server.repository.ArtistRepository;
import com.server.repository.SongRepository;
import com.server.service.drive.GoogleDriveService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GoogleDriveService driveService;

    String songFolderId = "1LdzTFIFV9AALrvPHC9llu2OTI2LVeKm2";

    public Song addSong(String songString, MultipartFile file) throws ConstraintViolationException, SongException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Song song = objectMapper.readValue(songString, Song.class);

        Song songOptional = songRepository.findByName(song.getName());

        if (songOptional != null
                && songOptional.getAlbum().getId().equals(song.getAlbum().getId())
                && songOptional.getName().equals(song.getName())) {
            throw new SongException(SongException.SongAlreadyExist(song.getName()));
        } else {
            AlbumOtd album = new AlbumOtd().albumToAlbumOtd(albumRepository.findById(song.getAlbum().getId()).get());

            com.google.api.services.drive.model.File fileUpload = driveService.uploadFile(song.getName(), file, "audio/mpeg", songFolderId);
            song.setFile(fileUpload.getId());
            song.setAlbum(album);
            song.setWeekView(0);
            song.setTotalView(0);
            song.setCreatedAt(new Date(System.currentTimeMillis()));

            driveService.deleteLocalFile(file.getOriginalFilename() );
            return songRepository.save(song);
        }
    }

    public SongResult findSongById(String id,boolean getFileYes) throws SongException, IOException {
        Song song = songRepository.findById(id).orElse(null);
        SongResult songResult = new SongResult();
        if (song != null) {
            String imageId = song.getAlbum().getImage();
            String fileId = song.getFile();

            songResult.setSong(song);

            if(getFileYes) {
                songResult.setImagePath(driveService.downloadFile(imageId, ".jpg"));
                songResult.setFilePath(driveService.downloadFile(fileId, ".m4a"));
            }
            return songResult;

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

    public void deleteSong(String id) throws SongException {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) {
            throw new SongException(SongException.NotFoundException(id));
        } else {
            driveService.deleteFile(song.getFile());
            songRepository.deleteById(id);
        }
    }

    public Song updateSong(String songString, MultipartFile file) throws ConstraintViolationException, SongException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Song song = objectMapper.readValue(songString,Song.class);

        Song songToUpdate = songRepository.findById(song.getId()).get();

        if (songToUpdate != null) {
            songToUpdate.setName(song.getName() != null ? song.getName() : songToUpdate.getName());
            songToUpdate.setDuration(song.getDuration());
            songToUpdate.setArtists(song.getArtists() != null ? song.getArtists() : songToUpdate.getArtists());
            songToUpdate.setGenres(song.getGenres() != null ? song.getGenres() : songToUpdate.getGenres());
            songToUpdate.setAlbum(song.getAlbum() != null ? song.getAlbum() : songToUpdate.getAlbum());
            songToUpdate.setFile(song.getFile() != null ? song.getFile() : songToUpdate.getFile());
            songToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            if(file != null){
                com.google.api.services.drive.model.File fileToUpdate = driveService.uploadFile(song.getName(),file,"audio/mpeg",songFolderId);
                songToUpdate.setFile(fileToUpdate.getId());

                driveService.deleteLocalFile(file.getOriginalFilename());
                driveService.deleteFile(song.getFile());
            }
            return songRepository.save(songToUpdate);
        } else {
            throw new SongException(SongException.NotFoundException(song.getId()));
        }
    }
}
