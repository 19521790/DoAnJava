package com.doan.client.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    private String id;
    private String name;
    private String image;
    private String file;
    private Double duration;
    private List<String> idGenres = new ArrayList<String>();
    private List<Artist> artists = new ArrayList<Artist>();

    private Album album;
    private Integer weekView;
    private Integer totalView;

}

