package com.server.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "artists")
public class Artist {
    @Id
    private String id;

    @NotNull(message = "Artist name cannot be null")
    private String name;

    @NotNull(message = "Artist image cannot be null")
    private String image;

    private String description;
    private int totalView;
    private List<Song> songs;
    private Date createdAt;
    private Date updatedAt;
}
