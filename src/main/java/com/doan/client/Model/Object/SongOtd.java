package com.doan.client.Model.Object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongOtd {

    private String id;
    private String name;
    private String file;
    private AlbumOtd album;
    private List<ArtistOtd> artistOtdList = new ArrayList<ArtistOtd>();
    private Date addedAt;
}
