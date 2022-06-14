package com.server.repository.custom;

import com.server.entity.Playlist;
import com.server.entity.Song;

public interface PlaylistTemplate {
    public void addSongToPlaylist(String idPlaylist, String idSong);
    public void addPlaylistToUser(String idUser, String idPlaylist);
}
