package com.server.entity;

import com.server.entity.object.PlaylistInUser;
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
    private List<PlaylistInUser> playlists = new ArrayList<PlaylistInUser>();
    private Date createdAt;
    private Date updatedAt;

//    public void addPlaylistToUser(String idPlaylist) {
//        playlists.add(idPlaylist);
//    }
}
