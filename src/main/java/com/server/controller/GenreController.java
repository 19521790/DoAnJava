package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.entity.Genre;
import com.server.exception.GenreException;
import com.server.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping("/addGenre")
    public ResponseEntity addGenre(@RequestPart("genre") String genreString, @RequestPart("image")MultipartFile image) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(genreService.addGenre(genreString,image));
        }catch(JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (GenreException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findGenreById/{id}")
    public Genre findGenreById(@PathVariable String id) {
        return genreService.findGenreById(id);
    }

    @GetMapping("/findAllGenres")
    public List<Genre> findGenres() {
        return genreService.findAllGenres();
    }

    @DeleteMapping("/deleteGenre/{id}")
    public String deleteGenre(@PathVariable String id) {
        return genreService.deleteGenre(id);
    }

    @PutMapping("/updateGenre")
    public Genre updateGenre(@RequestBody Genre genre) {
        return genreService.updateGenre(genre);
    }
}
