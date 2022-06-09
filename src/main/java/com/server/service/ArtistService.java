package com.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Album;
import com.server.entity.Artist;
import com.server.entity.Song;
import com.server.entity.result.ArtistResult;
import com.server.exception.AlbumException;
import com.server.exception.ArtistException;
import com.server.repository.ArtistRepository;
import com.server.repository.SongRepository;
import com.server.service.drive.GoogleDriveService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GoogleDriveService driveService;

    private String artistImgFolderId = "1AZozXXRklkIhhNf9I6C0GVb4czPl8X_C";

    public Artist addArtist(String artistString, MultipartFile image) throws ConstraintViolationException, JsonProcessingException, ArtistException {
        ObjectMapper objectMapper = new ObjectMapper();
        Artist artist = objectMapper.readValue(artistString, Artist.class);

        Artist artistOptional = artistRepository.findByName(artist.getName());

        if (artistOptional != null) {
            throw new ArtistException(ArtistException.ArtistAlreadyExist(artist.getName()));
        } else {
            artist.setImage(driveService.uploadFile(artist.getName(), image, "image/jpeg", artistImgFolderId).getId());
            artist.setTotalView(0);
            artist.setCreatedAt(new Date(System.currentTimeMillis()));

            driveService.deleteLocalFile(image.getOriginalFilename() );
            return artistRepository.save(artist);
        }
    }

    public Artist findArtistById(String id) {
        return artistRepository.findById(id).orElse(null);
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

    public void deleteArtist(String id) throws ArtistException {
        Artist artist = artistRepository.findById(id).orElse(null);

        if (artist == null) {
            driveService.deleteFile(artist.getImage());
            throw new ArtistException(ArtistException.NotFoundException(id));
        } else {
            artistRepository.deleteById(id);
        }
    }

    public List<ArtistResult> findAllSongs(String idArtist) {
        return artistRepository.findAllSongs(idArtist);
    }
}
