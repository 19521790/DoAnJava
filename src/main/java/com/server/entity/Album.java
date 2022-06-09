package com.server.entity;

import com.server.entity.object.ArtistOtd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
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

//    private List<ObjectId> idSongs = new ArrayList<ObjectId>();

    private ArtistOtd artist;
    private int TotalView;
    private Date createdAt;
    private Date updatedAt;

//    public void addSongToAlbum(ObjectId id){
//        this.idSongs.add(id);
//    }
}
