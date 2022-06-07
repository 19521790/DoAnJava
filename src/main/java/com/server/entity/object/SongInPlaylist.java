package com.server.entity.object;

import com.server.entity.Album;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongInPlaylist {
    private String id;
    private String name;
    private String image;
    private String file;
    private Album album;
    private List<ArtistInSong> artistInSongList = new ArrayList<ArtistInSong>();
    private String addedAt;
}
