package com.server.entity;

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
@Document(collection = "albums")
public class Album {
    @Id
    private String id;

    @NotNull(message = "Album name cannot be null")
    private String name;

    @NotNull(message = "Album image cannot be null")
    private String image;

    private List<String> idSongs = new ArrayList<String>();
    private Date createdAt;
    private Date updatedAt;

    public void addSongToAlbum(String id){
        this.idSongs.add(id);
    }
}
