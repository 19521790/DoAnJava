package com.server.repository.template;

import com.server.model.Song;

import java.util.List;

public interface SongTemplate {
    public List<Song> findSongByAlbum(String idAlbum);
    public List<Song> findSongByArtist(String idArtist);
    public List<Song> findSongByGenre(String genre);
    public List<Song> newUpdate();
    public List<Song> topSongVietNam();
    public List<Song> topSongGlobal();
    public List<Song> findByNameRegex(String word);
}
