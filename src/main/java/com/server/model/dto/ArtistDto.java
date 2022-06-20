package com.server.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistDto {
    private String id;
    private String name;
    private String image;
    private String description;
    private List<SongDto> songs;
}
