package com.server.entity.object;

import com.server.entity.Album;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongOtd {
    private String id;
    private String name;
    private String file;
    private Album album;
    private List<ArtistOtd> artistOtdList = new ArrayList<ArtistOtd>();
    private String addedAt;
}
