package com.server.service;

import com.server.entity.Playlist;
import com.server.entity.Song;
import com.server.entity.User;
import com.server.entity.result.SongResult;
import com.server.repository.PlaylistRepository;
import com.server.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendSystemService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<SongResult> getSongForPlaylistRecommend(String idPlaylist) {
        Playlist playlist = playlistRepository.findSongFromPlaylist(idPlaylist);
        List<Song> songs = playlist.getSongs();
        List<SongResult> songResults = new ArrayList<SongResult>();
        for (int i = 0; i < songs.size(); i++) {
            SongResult temp = new SongResult();
            temp.setName(songs.get(i).getName());
            temp.setYear(songs.get(i).getYear());
            songResults.add(temp);
        }
        return songResults;
    }

    public List<SongResult> recommendUser(String idUser){
        User user = userRepository.getLastListenSong(idUser);
        List<Song> songs = user.getLastListenSongs();
        List<SongResult> songResults = new ArrayList<SongResult>();
        for (int i = 0; i < songs.size(); i++) {
            SongResult temp = new SongResult();
            temp.setName(songs.get(i).getName());
            temp.setYear(songs.get(i).getYear());
            songResults.add(temp);
        }
        return songResults;
    }
}
