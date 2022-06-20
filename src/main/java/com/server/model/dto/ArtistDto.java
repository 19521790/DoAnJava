package com.server.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistDto {
    private String id;
    private String name;
    private String image;
    private String description;
    private int totalView;
    private List<SongDto> songs;
    private Date createdAt;
    private Date updatedAt;
}
