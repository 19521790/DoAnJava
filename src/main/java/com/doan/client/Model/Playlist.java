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
public class Playlist {
    private String id;
    private String name;
    private String image;
    private String idUser;
    private List<String> idSongs = new ArrayList<String>();
    private List<Song> songs;
    private int totalView;
    private Date createdAt;
    private Date updatedAt;
}
