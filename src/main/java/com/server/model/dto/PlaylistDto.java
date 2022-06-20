package com.server.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.model.Song;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDto {
    private String id;
    private String name;
    private String image;
    private String idUser;
    private List<Song> songs;
    private int totalView;
}
