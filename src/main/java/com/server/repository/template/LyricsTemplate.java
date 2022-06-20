package com.server.repository.template;

import com.server.model.Lyrics;

public interface LyricsTemplate {
    public Lyrics addLyrics(Lyrics lyric);
    public Lyrics findLyricsBySong(String idSong);
}
