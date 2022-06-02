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

    @NotNull(message = "Song image cannot be null")
    private String image;

    @NotNull(message = "Song file cannot be null")
    private String file;

    private Album album;

    @NotNull(message = "Song duration cannot be null")
    private Integer duration;

    private List<String> idGenre = new ArrayList<String>();

    private List<String> artists = new ArrayList<String>();
    private Integer weekView;
    private Integer totalView;
}
