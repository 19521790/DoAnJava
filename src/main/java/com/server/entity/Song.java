package com.server.entity;

import com.server.entity.object.ArtistInSong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "songs")
public class Song {
    @Id
    private String id;

    @NotNull(message = "Song name cannot be null")
    private String name;

    @NotNull(message = "Song file cannot be null")
    private String file;

    @NotNull(message = "Song duration cannot be null")
    private double duration;

    private List<String> genres = new ArrayList<String>();
    private List<ArtistInSong> artists = new ArrayList<ArtistInSong>();
    private Album album;
    private Integer weekView;
    private Integer totalView;
    private Date createdAt;
    private Date updatedAt;
}
