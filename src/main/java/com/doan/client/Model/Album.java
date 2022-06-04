package com.doan.client.Model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Album {

    private int id;

    private String name;
    private String image;
}

