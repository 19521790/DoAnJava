package com.server.repository.custom;

import com.server.entity.Song;

import java.util.List;

public interface SongTemplate {
    public List<Song> findSongByAlbum(String idAlbum);
    public List<Song> findSongByArtist(String idArtist);
}
