package com.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Album;
import com.server.entity.Genre;
import com.server.exception.GenreException;
import com.server.repository.GenreRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GoogleDriveService driveService;

    public Genre addGenre(String genreString, MultipartFile image) throws JsonProcessingException, GenreException {
        ObjectMapper objectMapper = new ObjectMapper();
        Genre genre = objectMapper.readValue(genreString, Genre.class);

        String genreFolderId = "1UE5E0PFuzW5v-TRBQf-USGSjq_gOwRsD";

        Genre genreOptional = genreRepository.findByName(genre.getName());

        if (genreOptional != null && genreOptional.getName().equals(genre.getName())) {
            throw new GenreException(GenreException.GenreAlreadyExist(genre.getName()));
        } else {
            genre.setImage(driveService.uploadFile(genre.getName(), image, "image/jpeg", genreFolderId).getId());
            genre.setCreatedAt(new Date(System.currentTimeMillis()));
            return genreRepository.save(genre);
        }
    }

    public Genre findGenreById(String id) {
        return genreRepository.findById(id).orElse(null);
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public String deleteGenre(String id) {
        genreRepository.deleteById(id);
        return ("Genre has been deleted: " + id);
    }

    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
