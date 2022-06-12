package com.doan.client.Model;


import com.doan.client.Model.Object.AlbumOtd;
import com.doan.client.Model.Object.ArtistOtd;
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
    private List<ArtistOtd> artists = new ArrayList<ArtistOtd>();
    private AlbumOtd album;
    private int weekView;
    private int totalView;
    private Date createdAt;
    private Date updatedAt;
}

