package com.doan.client.Model;


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
    private List<Artist> artists = new ArrayList<Artist>();
    private Album album;
    private int weekView;
    private int totalView;
    private int year;
    private Date createdAt;
    private Date updatedAt;
}

