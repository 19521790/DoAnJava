package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.server.entity.Song;
import com.server.exception.SongException;
import com.server.repository.SongRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GoogleDriveService driveService;

    public Song addSong(String songString, MultipartFile file, MultipartFile image) throws ConstraintViolationException, SongException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Song song = objectMapper.readValue(songString, Song.class);

        String songFolderId = "1LdzTFIFV9AALrvPHC9llu2OTI2LVeKm2";
        String songImgFolderId = "1sVp02QIus2FVUMMoXqxMiq6rmVFO51Cl";

        Song songOptional = songRepository.findByName(song.getName());

        if (songOptional == null) {
            song.setFile(driveService.uploadFile(file, "audio/mpeg", songFolderId).getId());
            song.setImage(driveService.uploadFile(image, "image/jpeg", songImgFolderId).getId());
            return songRepository.save(song);
        } else if (songOptional.getImage().equals(song.getImage())
                && songOptional.getName().equals(song.getName())
                && songOptional.getFile().equals(song.getFile())) {
            throw new SongException(SongException.SongAlreadyExist());
        } else {
            return songRepository.save(song);
        }
    }

    public List<Song> addSongs(List<Song> songs) {
        return songRepository.saveAll(songs);
    }

    public Song findSongById(String id) throws SongException {
        Song song = songRepository.findById(id).orElse(null);
        String fileId = song.getFile();
        String imageId = song.getImage();

        if (song != null) {
            com.google.api.services.drive.model.File file = driveService.downloadFile(fileId);
            com.google.api.services.drive.model.File image = driveService.downloadFile(imageId);
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

    public void deleteSong(String id) throws SongException{
        Song song = songRepository.findById(id).orElse(null);
        if(song==null){
            throw new SongException(SongException.NotFoundException(id));
        }else{
            songRepository.deleteById(id);
        }
    }

    public Song updateSong(Song song) throws ConstraintViolationException, SongException{
        Song songToUpdate = songRepository.findById(song.getId()).get();

        if (songToUpdate != null) {
            songToUpdate.setName(song.getName() != null ? song.getName() : songToUpdate.getName());
            songToUpdate.setImage(song.getImage() != null ? song.getImage() : songToUpdate.getImage());
            songToUpdate.setDuration(song.getDuration() != null ? song.getDuration() : songToUpdate.getDuration());
            songToUpdate.setArtists(song.getArtists()!=null?song.getArtists():songToUpdate.getArtists());
            songToUpdate.setIdGenres(song.getIdGenres()!=null?song.getIdGenres():songToUpdate.getIdGenres());
            songToUpdate.setAlbum(song.getAlbum()!=null?song.getAlbum():songToUpdate.getAlbum());
            songToUpdate.setFile(song.getFile()!=null?song.getFile():songToUpdate.getFile());
            songToUpdate.setWeekView(song.getWeekView() != null ? song.getWeekView() : songToUpdate.getWeekView());
            songToUpdate.setTotalView(song.getTotalView() != null ? song.getTotalView() : songToUpdate.getTotalView());
            return songRepository.save(songToUpdate);
        } else {
            throw new SongException(SongException.NotFoundException(song.getId()));
        }
    }
}
