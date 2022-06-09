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

    private String albumImgFolderId = "1RPmGzNV-xQ1n3F53tYAlq1P4_40hBxZ7";

    public Album addAlbum(String albumString, MultipartFile image) throws ConstraintViolationException, AlbumException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Album album = objectMapper.readValue(albumString, Album.class);

        Album albumOptional = albumRepository.findByName(album.getName());

        if (albumOptional != null && albumOptional.getName().equals(album.getName())) {
            throw new AlbumException(AlbumException.AlbumAlreadyExist(album.getName()));
        } else {
            album.setImage(driveService.uploadFile(album.getName(), image, "image/jpeg", albumImgFolderId).getId());
            album.setTotalView(0);
            album.setCreatedAt(new Date(System.currentTimeMillis()));

            driveService.deleteLocalFile(image.getOriginalFilename() );
            return albumRepository.save(album);
        }
    }

    public Album findAlbumById(String id) {
        return albumRepository.findById(id).orElse(null);
    }

    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    public Album updateAlbum(String albumString, MultipartFile image) throws JsonProcessingException, AlbumException {
        ObjectMapper objectMapper = new ObjectMapper();
        Album album = objectMapper.readValue(albumString, Album.class);

        Album albumToUpdate = albumRepository.findById(album.getId()).get();

        if (albumToUpdate != null) {
            albumToUpdate.setName(album.getName() != null ? album.getName() : albumToUpdate.getName());
            albumToUpdate.setArtist(album.getArtist() != null ? album.getArtist() : albumToUpdate.getArtist());
            albumToUpdate.setImage(album.getImage() != null ? album.getImage() : albumToUpdate.getImage());
            albumToUpdate.setIdSongs(album.getIdSongs() != null ? album.getIdSongs() : albumToUpdate.getIdSongs());
            albumToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            if (image != null) {
                com.google.api.services.drive.model.File fileToUpdate = driveService.uploadFile(album.getName(),image,"image/jpeg",albumImgFolderId);
                albumToUpdate.setImage(fileToUpdate.getId());

                driveService.deleteLocalFile(image.getOriginalFilename());
                driveService.deleteFile(album.getImage());
            }

            return albumRepository.save(albumToUpdate);
        } else {
            throw new AlbumException(AlbumException.NotFoundException(album.getId()));
        }
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
