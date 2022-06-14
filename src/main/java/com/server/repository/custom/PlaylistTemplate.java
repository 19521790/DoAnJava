package com.server.repository.custom;

import com.server.entity.Playlist;
import com.server.entity.Song;

import java.util.List;

public interface PlaylistTemplate {
    public void addSongToPlaylist(String idPlaylist, String idSong);
    public void addPlaylistToUser(String idUser, String idPlaylist);
    public Playlist findSongByPlaylist(String idPlaylist);
}
