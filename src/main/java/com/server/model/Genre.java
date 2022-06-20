package com.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "genres")
public class Genre {
    @Id
    private String id;

    @NotNull(message = "Genre name cannot be null")
    private String name;
    @NotNull(message = "Genre image cannot be null")
    private String image;
    private Date createdAt;
    private Date updatedAt;
}
