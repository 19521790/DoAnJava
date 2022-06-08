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
    private Song song;
    private String imagePath;
    private String filePath;
}
