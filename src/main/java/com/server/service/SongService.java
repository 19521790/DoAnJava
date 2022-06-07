package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.server.entity.Song;
import com.server.entity.result.SongResult;
import com.server.exception.SongException;
import com.server.repository.SongRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
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

        Song songOptional = songRepository.findByName(song.getName());

        if(songOptional.getAlbum().getId().equals(song.getAlbum().getId())
        &&songOptional.getName().equals(song.getName())){
            throw new SongException(SongException.SongAlreadyExist(song.getName()));
        }else{
            song.setFile(driveService.uploadFile(song.getName(),file, "audio/mpeg", songFolderId).getId());
            song.setCreatedAt(new Date(System.currentTimeMillis()));
            return songRepository.save(song);
        }
    }

    public List<Song> addSongs(List<Song> songs) {
        return songRepository.saveAll(songs);
    }

    public File findSongById(String id) throws SongException, IOException {
        Song song = songRepository.findById(id).orElse(null);
        String imageId = song.getAlbum().getImage();
        System.out.println(imageId);
        java.io.File image = new java.io.File("temp.jpg");
        if (song != null) {
            OutputStream os = new FileOutputStream(image);
            driveService.downloadFile(imageId).writeTo(os);
            System.out.println(image);
            os.close();
//            System.out.println("os close");
//            FileInputStream input = new FileInputStream(image);
//            System.out.println("file inputStream");
//            MultipartFile multipartFile = new MockMultipartFile("file",
//                    image.getName(), "image/jpeg", org.apache.commons.io.IOUtils.toByteArray(input));
//            System.out.println("multipart file");
//            image.delete();
//            System.out.println("file delete");
            return image;
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

    public Song updateSong(Song song) throws ConstraintViolationException, SongException {
        Song songToUpdate = songRepository.findById(song.getId()).get();

        if (songToUpdate != null) {
            songToUpdate.setName(song.getName() != null ? song.getName() : songToUpdate.getName());
            songToUpdate.setDuration(song.getDuration());
            songToUpdate.setArtists(song.getArtists() != null ? song.getArtists() : songToUpdate.getArtists());
            songToUpdate.setIdGenres(song.getIdGenres() != null ? song.getIdGenres() : songToUpdate.getIdGenres());
            songToUpdate.setAlbum(song.getAlbum() != null ? song.getAlbum() : songToUpdate.getAlbum());
            songToUpdate.setFile(song.getFile() != null ? song.getFile() : songToUpdate.getFile());
            songToUpdate.setWeekView(song.getWeekView() != null ? song.getWeekView() : songToUpdate.getWeekView());
            songToUpdate.setTotalView(song.getTotalView() != null ? song.getTotalView() : songToUpdate.getTotalView());
            return songRepository.save(songToUpdate);
        } else {
            throw new SongException(SongException.NotFoundException(song.getId()));
        }
    }
}
