package com.server.service;

import com.google.api.client.http.FileContent;
import com.server.entity.Song;
import com.server.exception.SongException;
import com.server.repository.SongRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GoogleDriveService driveService;

    public Song addSong(Song song, File file) throws ConstraintViolationException, SongException, Exception {
        Song songOptional = songRepository.findByName(song.getName());
        String fileName = "first_image";
        String filePath = "D:\\Downloads\\Beatbox - NCT Dream.m4a";
        String mimeType = "audio/mpeg";
        if (songOptional == null) {
            System.out.println(System.getProperty("user.dir"));
            System.out.println("####################");
            try {
                driveService.uploadFile(fileName, filePath, mimeType);
                return songRepository.save(song);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        else if (songOptional.getImage().equals(song.getImage()) && songOptional.getName().equals(song.getName())) {
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
        if (song != null) {
            driveService.printFile();
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

    public String deleteSong(String id) {
        try {
            songRepository.deleteById(id);
            return "Song has been removed: " + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Song updateSong(Song song) {
        Song songToUpdate = songRepository.findById(song.getId()).get();
        if (songToUpdate != null) {
            songToUpdate.setName(song.getName() != null ? song.getName() : songToUpdate.getName());
            songToUpdate.setImage(song.getImage() != null ? song.getImage() : songToUpdate.getImage());
            songToUpdate.setDuration(song.getDuration() != null ? song.getDuration() : songToUpdate.getDuration());
            songToUpdate.setWeekView(song.getWeekView() != null ? song.getWeekView() : songToUpdate.getWeekView());
            songToUpdate.setTotalView(song.getTotalView() != null ? song.getTotalView() : songToUpdate.getTotalView());
            return songRepository.save(songToUpdate);
        } else {
            return songToUpdate;
        }
    }
}
