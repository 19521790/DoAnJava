package com.server.service;

import com.server.exception.SongException;
import com.server.model.Lyrics;
import com.server.model.Song;
import com.server.repository.LyricsRepository;
import com.server.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LyricsService {
    @Autowired
    private LyricsRepository lyricsRepository;

    @Autowired
    private SongRepository songRepository;

    public Lyrics addLyrics(Lyrics lyrics){
        return lyricsRepository.save(lyrics);
    }
    public Lyrics findLyricsBySong(String idSong) throws SongException {
        Song song = songRepository.findById(idSong).orElse(null);

        if(song!=null){
            return lyricsRepository.findLyricsBySong(idSong);
        }else{
            throw new SongException(SongException.NotFoundException(idSong));
        }

    }
}
