package com.server.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.entity.object.SongLastListen;
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
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotNull(message = "User name cannot be null")
    private String name;

    @NotNull(message = "User email cannot be null")
    private String email;

    private String displayName;

    @NotNull(message = "User password cannot be null")
    private String password;

    private String image;
    private String role = "user";
    private Date createdAt;
    private Date updatedAt;

    private List<String> idLastListenSongs;
    private List<Song> lastListenSongs;
    private List<String> idAlbums;
    private List<Album> albums;
    private List<String> idArtists;
    private List<Artist> artists;
}
