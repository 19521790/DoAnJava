package com.server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@Document(collection = "playlists")
public class Playlist {
    @Id
    private String id;

    @NotNull(message = "Playlist name cannot be null")
    private String name;
    private String image;

    @NotNull(message = "Playlist idUser cannot be null")
    private int idUser;

    private List<Song>songs = new ArrayList<Song>();

    private int totalView;
    private Date createTime;
    private Date updateTime;

    public void addSongToPlaylist(Song song){
        songs.add(song);
    }
}
