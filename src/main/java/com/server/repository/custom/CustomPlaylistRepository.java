package com.server.repository.custom;

import com.server.entity.Playlist;
import com.server.entity.Song;

public interface CustomPlaylistRepository {
    public Playlist addSongToPlaylist(Playlist playlist, Song song);
}
