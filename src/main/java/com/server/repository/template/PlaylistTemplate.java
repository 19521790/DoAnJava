package com.server.repository.template;

import com.server.model.Playlist;

import java.util.List;

public interface PlaylistTemplate {
    public void addSongToPlaylist(String idPlaylist, String idSong);
    public void addPlaylistToUser(String idUser, String idPlaylist);
    public Playlist findSongFromPlaylist(String idPlaylist);
    public List<Playlist> findPlaylistFromUser(String idUser);
    public void removeSongFromPlaylist (String idPlaylist, String idSong);
}
