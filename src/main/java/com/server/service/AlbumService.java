package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.model.Album;
import com.server.exception.AlbumException;
import com.server.exception.FileFormatException;
import com.server.repository.AlbumRepository;
import com.server.repository.SongRepository;
import com.server.service.data.DataService;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GoogleDriveService driveService;

    @Autowired
    private DataService dataService;

    private String albumImgFolderId = "1RPmGzNV-xQ1n3F53tYAlq1P4_40hBxZ7";

    public Album addAlbum(String albumString, MultipartFile image) throws ConstraintViolationException, AlbumException, IOException, FileFormatException {
        ObjectMapper objectMapper = new ObjectMapper();
        Album album = objectMapper.readValue(albumString, Album.class);

        Album albumOptional = albumRepository.findByName(album.getName());

        if (albumOptional != null && albumOptional.getName().equals(album.getName())) {
            throw new AlbumException(AlbumException.AlbumAlreadyExist(album.getName()));
        } else {
            album.setImage(dataService.storeData(image,".jpg"));
            album.setTotalView(0);
            album.setCreatedAt(new Date(System.currentTimeMillis()));

            return albumRepository.save(album);
        }
    }

    public Album findAlbumById(String id) throws AlbumException {
        Album album = albumRepository.findById(id).orElse(null);

        if (album != null) {
            album.setSongs(songRepository.findSongByAlbum(id));
            return album;
        } else {
            throw new AlbumException(AlbumException.NotFoundException(id));
        }
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
//            albumToUpdate.setIdSongs(album.getIdSongs() != null ? album.getIdSongs() : albumToUpdate.getIdSongs());
            albumToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            if (image != null) {
                com.google.api.services.drive.model.File fileToUpdate = driveService.uploadFile(album.getName(), image, "image/jpeg", albumImgFolderId);
                albumToUpdate.setImage(fileToUpdate.getId());

                driveService.deleteLocalFile(image.getOriginalFilename());
                driveService.deleteFile(album.getImage());
            }

            return albumRepository.save(albumToUpdate);
        } else {
            throw new AlbumException(AlbumException.NotFoundException(album.getId()));
        }
    }

    public void deleteAlbum(String id)throws AlbumException, FileFormatException{
        Album album = albumRepository.findById(id).orElse(null);
        if (album == null) {
            throw new AlbumException(AlbumException.NotFoundException(id));
        } else {
            dataService.deleteData(album.getImage());
            songRepository.deleteById(id);
        }
    }
}
