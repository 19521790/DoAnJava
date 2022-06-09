package com.server.entity;

import com.server.entity.object.ArtistOtd;
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
    private List<ArtistOtd> artists = new ArrayList<ArtistOtd>();

    @NotNull(message = "Song album cannot be null")
    private Album album;
    private int weekView;
    private int totalView;
    private Date createdAt;
    private Date updatedAt;
}
