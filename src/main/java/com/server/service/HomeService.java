package com.server.service;

import com.server.entity.Song;
import com.server.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    @Autowired
    private SongRepository songRepository;

    public List<Song> newUpdate(){
        return songRepository.newUpdate();
    }
}
