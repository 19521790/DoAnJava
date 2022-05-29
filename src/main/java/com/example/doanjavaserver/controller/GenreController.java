package com.example.doanjavaserver.controller;

import com.example.doanjavaserver.entity.Genre;
import com.example.doanjavaserver.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping("/addGenre")
    public Genre addGenre(@RequestBody Genre genre) {
        return genreService.addGenre(genre);
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
