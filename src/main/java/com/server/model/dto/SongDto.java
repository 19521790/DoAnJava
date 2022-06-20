package com.server.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.model.Album;
import com.server.model.object.ArtistOtd;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongDto {
    private String id;
    private String name;
    private String file;
    private double duration;
    private List<String> genres = new ArrayList<String>();
    private List<ArtistDto> artists = new ArrayList<ArtistDto>();
    private AlbumDto album;
    private int year;
}
