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
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "artists")
public class Artist {
    @Id
    private int id;

    @NotNull(message = "Artist name cannot be null")
    private String name;

    @NotNull(message = "Artist image cannot be null")
    private String image;

    private String description;
    private Date createdAt;
    private Date updatedAt;
}
