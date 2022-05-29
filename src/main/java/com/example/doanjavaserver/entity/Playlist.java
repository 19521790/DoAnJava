package com.example.doanjavaserver.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "playlists")
public class Playlist {
    @Id
    private String id;

    private String name;
    private String image;
    private int idUser;
    private int totalView;
    private Date createTime;
    private Date updateTime;
}
