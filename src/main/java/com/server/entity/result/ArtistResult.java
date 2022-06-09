package com.server.entity.result;

import com.server.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResult {
    private String id;
    private String name;
    private String image;
    private List<Song> songs = new ArrayList<Song>();
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
