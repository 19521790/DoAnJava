package com.doan.client.Model;

import com.doan.client.Model.Object.SongOtd;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Playlist {

    private String id;
    private String name;
    private String image;
    private Object idUser;
    private List<SongOtd>songs = new ArrayList<SongOtd>();

    private int totalView;
    private Date createdAt;
    private Date updatedAt;
}
