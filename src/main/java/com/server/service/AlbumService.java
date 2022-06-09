package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Album;
import com.server.entity.Song;
import com.server.exception.AlbumException;
import com.server.exception.SongException;
import com.server.repository.AlbumRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GoogleDriveService driveService;

    public Album addAlbum(String albumString, MultipartFile image) throws ConstraintViolationException, AlbumException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Album album = objectMapper.readValue(albumString, Album.class);

        String albumImgFolderId = "1RPmGzNV-xQ1n3F53tYAlq1P4_40hBxZ7";

        Album albumOptional = albumRepository.findByName(album.getName());

        if (albumOptional != null && albumOptional.getName().equals(album.getName())) {
            throw new AlbumException(AlbumException.AlbumAlreadyExist(album.getName()));
        } else {
            album.setImage(driveService.uploadFile(album.getName(), image, "image/jpeg", albumImgFolderId).getId());
            album.setCreatedAt(new Date(System.currentTimeMillis()));
            return albumRepository.save(album);
        }
    }

    public Album findAlbumById(String id) {
        return albumRepository.findById(id).orElse(null);
    }

    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    public Album updateAlbum(String albumString, MultipartFile image) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Album album = objectMapper.readValue(albumString, Album.class);

//        Song songToUpdate = alb.findById(song.getId()).get();
//
//        if (songToUpdate != null) {
//            songToUpdate.setName(song.getName() != null ? song.getName() : songToUpdate.getName());
//            songToUpdate.setDuration(song.getDuration());
//            songToUpdate.setArtists(song.getArtists() != null ? song.getArtists() : songToUpdate.getArtists());
//            songToUpdate.setGenres(song.getGenres() != null ? song.getGenres() : songToUpdate.getGenres());
//            songToUpdate.setAlbum(song.getAlbum() != null ? song.getAlbum() : songToUpdate.getAlbum());
//            songToUpdate.setFile(song.getFile() != null ? song.getFile() : songToUpdate.getFile());
//            songToUpdate.setWeekView(song.getWeekView() != null ? song.getWeekView() : songToUpdate.getWeekView());
//            songToUpdate.setTotalView(song.getTotalView() != null ? song.getTotalView() : songToUpdate.getTotalView());
//            songToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
//            return songRepository.save(songToUpdate);
//        } else {
//            throw new SongException(SongException.NotFoundException(song.getId()));
//        }
        return albumRepository.save(album);
    }

    public String deleteAlbum(String id) {
        albumRepository.deleteById(id);
        return ("Album has been deleted: " + id);
    }

//    public Album addSongToAlbum(List<String> idSongs, String idAlbum) throws AlbumException {
//        Album album = albumRepository.findById(idAlbum).get();
//        if (album == null) {
//            throw new AlbumException(AlbumException.NotFoundException(idAlbum));
//        } else {
//            for (String idSong : idSongs) {
//                album.addSongToAlbum(idSong);
//            }
//            return albumRepository.save(album);
//        }
//    }
}
