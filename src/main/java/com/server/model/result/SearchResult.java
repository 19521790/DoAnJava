package com.server.model.result;

import com.server.model.Album;
import com.server.model.Artist;
import com.server.model.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    List<Song> songs;
    List<Album> albums;
    List<Artist> artists;
}
