package com.doan.client.Model.Object;


import com.doan.client.Model.Album;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumOtd {
    private String id;
    private String name;
    private String image;
    private double totalView;
}
