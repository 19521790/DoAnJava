package com.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Artist;
import com.server.entity.Song;
import com.server.exception.ArtistException;
import com.server.repository.ArtistRepository;
import com.server.repository.SongRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Artist addArtist(String artistString, MultipartFile image) throws ConstraintViolationException, JsonProcessingException, ArtistException {
        ObjectMapper objectMapper = new ObjectMapper();
        Artist artist = objectMapper.readValue(artistString, Artist.class);

        Artist artistOptional = artistRepository.findByName(artist.getName());

        String artistImgFolderId = "1AZozXXRklkIhhNf9I6C0GVb4czPl8X_C";

        if (artistOptional != null && artistOptional.getName().equals(artist.getName())) {
            throw new ArtistException(ArtistException.ArtistAlreadyExist(artist.getName()));
        } else {
            artist.setImage(driveService.uploadFile(artist.getName(), image, "image/jpeg", artistImgFolderId).getId());
            artist.setCreatedAt(new Date(System.currentTimeMillis()));
            return artistRepository.save(artist);
        }
    }

    public Artist findArtistById(String id) {
        return artistRepository.findById(id).orElse(null);
    }

    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    public Artist updateArtist(Artist artist) {
        return artistRepository.save(artist);
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

    public Artist addSongToArtist(String idArtist, String idSong) {
        Artist playlist = artistRepository.findById(idArtist).get();
        Song song = songRepository.findById(idSong).get();
        return artistRepository.save(playlist);
    }
}
