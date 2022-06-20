package com.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.model.Artist;
import com.server.exception.ArtistException;
import com.server.exception.FileFormatException;
import com.server.repository.ArtistRepository;
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
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private DataService dataService;

    @Autowired
    private GoogleDriveService driveService;

    private String artistImgFolderId = "1AZozXXRklkIhhNf9I6C0GVb4czPl8X_C";

    public Artist addArtist(String artistString, MultipartFile image) throws ConstraintViolationException, IOException, ArtistException, FileFormatException {
        ObjectMapper objectMapper = new ObjectMapper();
        Artist artist = objectMapper.readValue(artistString, Artist.class);

        Artist artistOptional = artistRepository.findByName(artist.getName());

        if (artistOptional != null) {
            throw new ArtistException(ArtistException.ArtistAlreadyExist(artist.getName()));
        } else {
            artist.setImage(dataService.storeData(image,".jpg"));
            artist.setTotalView(0);
            artist.setCreatedAt(new Date(System.currentTimeMillis()));

            return artistRepository.save(artist);
        }
    }

    public Artist findArtistById(String id) throws ArtistException {
        Artist artist = artistRepository.findById(id).orElse(null);

        if (artist != null) {
            artist.setSongs(songRepository.findSongByArtist(id));
            return artist;
        } else {
            throw new ArtistException(ArtistException.NotFoundException(id));
        }
    }

    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    public Artist updateArtist(String artistString, MultipartFile image) throws ArtistException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Artist artist = objectMapper.readValue(artistString, Artist.class);

        Artist artistToUpdate = artistRepository.findById(artist.getId()).get();

        if (artistToUpdate != null) {
            artistToUpdate.setName(artist.getName() != null ? artist.getName() : artistToUpdate.getName());
//            artistToUpdate.setIdAlbums(artist.getIdAlbums() != null ? artist.getIdAlbums() : artistToUpdate.getIdAlbums());
//            artistToUpdate.setIdSongs(artist.getIdSongs() != null ? artist.getIdSongs() : artistToUpdate.getIdSongs());
            artistToUpdate.setImage(artist.getImage() != null ? artist.getImage() : artistToUpdate.getImage());
            artistToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            if (image != null) {
                com.google.api.services.drive.model.File fileToUpdate = driveService.uploadFile(artist.getName(), image, "image/jpeg", artistImgFolderId);
                artistToUpdate.setImage(fileToUpdate.getId());

                driveService.deleteLocalFile(image.getOriginalFilename());
                driveService.deleteFile(artist.getImage());
            }

            return artistRepository.save(artistToUpdate);
        } else {
            throw new ArtistException(ArtistException.NotFoundException(artist.getId()));
        }

    }

    public void deleteArtist(String id) throws ArtistException, FileFormatException {
        Artist artist = artistRepository.findById(id).orElse(null);

        if (artist == null) {
            throw new ArtistException(ArtistException.NotFoundException(id));
        } else {
            dataService.deleteData(id);
            driveService.deleteFile(artist.getImage());
        }
    }
}
