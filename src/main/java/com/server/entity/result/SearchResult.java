package com.server.entity.result;

import com.server.entity.Album;
import com.server.entity.Artist;
import com.server.entity.Song;
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
