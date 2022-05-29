package com.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "songs")
public class Song {
    @Id
    private String id;
    private String name;
    private String image;
    private int idAlbum;
    private int duration;
    private int weekView;
    private int totalView;
}
