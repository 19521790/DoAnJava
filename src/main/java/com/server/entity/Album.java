package com.server.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "albums")
public class Album {
    @Id
    private String id;

    @NotNull(message = "Album name cannot be null")
    private String name;

    @NotNull(message = "Album image cannot be null")
    private String image;

    private ArtistOtd artist;
    private int totalView;
    private List<Song> songs;
    private Date createdAt;
    private Date updatedAt;
}
