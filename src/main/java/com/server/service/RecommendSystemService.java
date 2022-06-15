package com.server.service;

import com.server.entity.result.SongResult;
import com.server.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendSystemService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistService playlistService;

//    public List<SongResult> getLastedSongInPlaylist(){
//
//    }
}
