package com.server.service;


import com.server.entity.Artist;
import com.server.entity.Song;
import com.server.repository.ArtistRepository;
import com.server.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    public Artist addArtist(Artist artist){
        return artistRepository.save(artist);
    }

    public Artist findArtistById(String id){
        return artistRepository.findById(id).orElse(null);
    }

    public List<Artist> findAllArtists(){
        return artistRepository.findAll();
    }

    public Artist updateArtist(Artist artist){
        return artistRepository.save(artist);
    }

    public String deleteArtist(String id){
        artistRepository.deleteById(id);
        return ("Artiest has been deleted: "+id);
    }

    public Artist addSongToArtist(String idArtist, String idSong){
        Artist playlist = artistRepository.findById(idArtist).get();
        Song song = songRepository.findById(idSong).get();
        return artistRepository.save(playlist);
    }
}
