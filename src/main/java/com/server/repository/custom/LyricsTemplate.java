package com.server.repository.custom;

import com.server.entity.Lyrics;

public interface LyricsTemplate {
    public Lyrics addLyrics(Lyrics lyric);
    public Lyrics findLyricsBySong(String idSong);
}
