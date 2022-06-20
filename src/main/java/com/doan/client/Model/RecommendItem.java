package com.doan.client.Model;


import com.doan.client.Model.Object.AlbumOtd;
import com.doan.client.Model.Object.ArtistOtd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendItem {

    private String name;
    private String artists;
    private int year;

}

