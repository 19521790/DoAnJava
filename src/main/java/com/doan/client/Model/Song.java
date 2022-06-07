package com.doan.client.Model;


import com.doan.client.Model.Object.ArtistInSong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    private String id;
    private String name;
    private String file;
    private double duration;
    private List<String> genres = new ArrayList<String>();
    private List<ArtistInSong> artists = new ArrayList<>();
    private Album album;
    private Integer weekView;
    private Integer totalView;
    private Date createdAt;
    private Date updatedAt;
}

