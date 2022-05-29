package com.server.service;


import com.server.entity.Genre;
import com.server.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
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
