package com.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "playlists")
public class Playlist {
    @Id
    private String id;

    @NotNull(message = "Playlist name cannot be null")
    private String name;

    private String image;

    @NotNull(message = "Playlist idUser cannot be null")
    private String idUser;

    private List<String> idSongs = new ArrayList<String>();
    private List<Song> songs;

    private int totalView;
    private Date createdAt;
    private Date updatedAt;
}
