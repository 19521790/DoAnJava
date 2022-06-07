package com.server.entity;

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
@Document(collection = "albums")
public class Album {
    @Id
    private String id;

    @NotNull(message = "Album name cannot be null")
    private String name;

    @NotNull(message = "Album image cannot be null")
    private String image;

    private Date createdAt;
    private Date updatedAt;
}