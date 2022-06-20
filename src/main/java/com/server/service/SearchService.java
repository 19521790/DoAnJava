package com.server.service;

import com.server.model.result.SearchResult;
import com.server.repository.AlbumRepository;
import com.server.repository.ArtistRepository;
import com.server.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    public SearchResult search(String word) {
        SearchResult result = new SearchResult();
        result.setSongs(songRepository.findByNameRegex(word));
        System.out.println(2);
        result.setArtists(artistRepository.findByNameRegex(word));
        System.out.println(3);
        result.setAlbums(albumRepository.findByNameRegex(word));
        System.out.println(4);
        return result;
    }
}
