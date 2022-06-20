package com.server.service;

import com.server.model.Song;
import com.server.model.dto.SongDto;
import com.server.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {
    @Autowired
    private SongRepository songRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private List<SongDto> convertToListDto(List<Song> songs){
        return songs.stream().map(song -> modelMapper.map(song, SongDto.class)).collect(Collectors.toList());
    }

    public List<SongDto> newUpdate() {
        return convertToListDto(songRepository.newUpdate());
    }

    public List<SongDto> topSongVietNam() {
        return convertToListDto(songRepository.topSongVietNam());
    }

    public List<SongDto> topSongGlobal() {
        return convertToListDto(songRepository.topSongGlobal());
    }
}
