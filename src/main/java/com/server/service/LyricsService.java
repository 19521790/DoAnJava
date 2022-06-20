package com.server.service;

import com.server.exception.SongException;
import com.server.model.Lyrics;
import com.server.model.Song;
import com.server.model.dto.LyricsDto;
import com.server.model.dto.SongDto;
import com.server.repository.LyricsRepository;
import com.server.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LyricsService {
    @Autowired
    private LyricsRepository lyricsRepository;

    @Autowired
    private SongRepository songRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private Lyrics convertToEntity(LyricsDto lyricsDto) {
        Lyrics lyrics = modelMapper.map(lyricsDto, Lyrics.class);

        if (lyricsDto.getId() != null) {
            return lyricsRepository.findById(lyricsDto.getId()).orElse(lyrics);
        } else {
            return lyrics;
        }
    }

    private LyricsDto convertToDto(Lyrics lyrics) {
        LyricsDto lyricsDto = modelMapper.map(lyrics, LyricsDto.class);
        return lyricsDto;
    }


    public LyricsDto addLyrics(Lyrics lyrics) {
        return convertToDto(lyricsRepository.save(lyrics));
    }

    public LyricsDto findLyricsBySong(String idSong) throws SongException {
        Song song = songRepository.findById(idSong).orElse(null);

        if (song != null) {
            return convertToDto(lyricsRepository.findLyricsBySong(idSong));
        } else {
            throw new SongException(SongException.NotFoundException(idSong));
        }

    }
}
