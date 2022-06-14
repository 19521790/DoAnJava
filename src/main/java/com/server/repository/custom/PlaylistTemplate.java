package com.server.repository.custom;

import com.server.entity.Playlist;
import com.server.entity.Song;

import java.util.List;

public interface PlaylistTemplate {
    public void addSongToPlaylist(String idPlaylist, String idSong);
    public void addPlaylistToUser(String idUser, String idPlaylist);
    public Playlist findSongFromPlaylist(String idPlaylist);
    public List<Playlist> findPlaylistFromUser(String idUser);
    public void removeSongFromPlaylist (String idPlaylist, String idSong);
}
