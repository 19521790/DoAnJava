package com.server.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Document(collection = "artists")
public class Artist {
    @Id
    private String id;

    @NotNull(message = "Artist name cannot be null")
    private String name;

    @NotNull(message = "Artist image cannot be null")
    private String image;

    private List<String>idSongs = new ArrayList<String>();

    private String description;
    private Date createdAt;
    private Date updatedAt;

    public void addSongToArtist(String id){
        this.idSongs.add(id);
    }
}
