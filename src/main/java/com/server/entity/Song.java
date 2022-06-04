package com.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "songs")
public class Song {
    @Id
    private String id;

    @NotNull(message = "Song name cannot be null")
    private String name;

    private String image;
    private String file;

    @NotNull(message = "Song duration cannot be null")
    private Integer duration;

    private List<String> idGenres = new ArrayList<String>();
    private List<Artist> artists = new ArrayList<Artist>();
    private Album album;
    private Integer weekView;
    private Integer totalView;
}
