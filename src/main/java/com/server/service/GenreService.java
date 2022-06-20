package com.server.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.model.Artist;
import com.server.model.Genre;
import com.server.exception.FileFormatException;
import com.server.exception.GenreException;
import com.server.model.dto.ArtistDto;
import com.server.model.dto.GenreDto;
import com.server.repository.GenreRepository;
import com.server.service.data.DataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private DataService dataService;

    private ModelMapper modelMapper = new ModelMapper();

    private Genre convertToEntity(GenreDto genreDto) {
        Genre genre = modelMapper.map(genreDto, Genre.class);

        if (genreDto.getId() != null) {
            return genreRepository.findById(genreDto.getId()).orElse(genre);
        } else {
            return genre;
        }
    }

    private GenreDto convertToDto(Genre genre) {
        GenreDto genreDto = modelMapper.map(genre, GenreDto.class);

        return genreDto;
    }

    public GenreDto addGenre(String genreString, MultipartFile image) throws IOException, GenreException, FileFormatException {
        ObjectMapper objectMapper = new ObjectMapper();
        Genre genre = objectMapper.readValue(genreString, Genre.class);

        Genre genreOptional = genreRepository.findByName(genre.getName());

        if (genreOptional != null && genreOptional.getName().equals(genre.getName())) {
            throw new GenreException(GenreException.GenreAlreadyExist(genre.getName()));
        } else {
            genre.setImage(dataService.storeData(image, ".jpg"));
            genre.setCreatedAt(new Date(System.currentTimeMillis()));

            return convertToDto(genreRepository.save(genre));
        }
    }

    public Genre findGenreById(String id) {
        return genreRepository.findById(id).orElse(null);
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public void deleteGenre(String id) throws FileFormatException, GenreException {
        Genre genre = genreRepository.findById(id).orElse(null);

        if (genre != null) {
            dataService.deleteData(genre.getImage());
            genreRepository.deleteById(id);
        } else {
            throw new GenreException(GenreException.NotFoundException(id));
        }
    }

    public GenreDto updateGenre(Genre genre) {
        return convertToDto(genreRepository.save(genre));
    }
}
