package com.server.entity.result;

import com.server.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongResult {
    private Song song;
    private String imagePath;
    private String filePath;
}
