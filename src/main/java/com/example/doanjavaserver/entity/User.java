package com.example.doanjavaserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String displayName;
    private String password;
    private String image;
    private String role = "user";
    private List<String> idPlaylists = new ArrayList<String>();
    private Date createdAt;
    private Date updatedAt;

    public void addPlaylistToUser(String idPlaylist) {
        idPlaylists.add(idPlaylist);
    }
}
