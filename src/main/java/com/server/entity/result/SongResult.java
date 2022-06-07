package com.server.entity.result;

import com.server.entity.Album;
import com.server.entity.Song;
import com.server.entity.object.ArtistInSong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongResult {
    @Id
    private String id;
    private String name;
    private OutputStream image;
    private OutputStream file;
    private double duration;
    private List<ArtistInSong> artists = new ArrayList<ArtistInSong>();
    private Album album;

    public void songToSongResult(Song song){
        this.id=song.getId();
        this.name=song.getName();
        this.artists=song.getArtists();
        this.album=song.getAlbum();
    }
}
