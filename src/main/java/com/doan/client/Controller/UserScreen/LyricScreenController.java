package com.doan.client.Controller.UserScreen;

import com.doan.client.Model.Lyrics;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class LyricScreenController {

    public Label songTitle;
    public VBox vboxLyric;
    public ArrayList<Integer> playTime= new ArrayList<>();

    public void initLyrics(List<Lyrics> list) {
        for (int i = 1; i < list.size(); i++) {
            playTime.add(list.get(i).getSecond());
            Label label = new Label();
            label.setPrefWidth(1000);
            label.setPrefHeight(100);
            label.setStyle("-fx-font-size: 18; -fx-text-fill: black; -fx-font-weight: 500");
            label.setAlignment(Pos.CENTER);
            label.setText(list.get(i).getLyric());
            vboxLyric.getChildren().add(label);

        }

    }

    public void changeLyrics(int current) {


        for (int i=0 ; i< playTime.size(); i++){
            if (current == playTime.get(i)){

                Label label= (Label) vboxLyric.getChildren().get(i);
                label.setStyle("-fx-font-size: 25;-fx-text-fill: #3b75ff; -fx-font-weight: 700");
                for (int j=0 ; j< i; j++){
                    Label label1= (Label) vboxLyric.getChildren().get(j);
                    label1.setStyle("-fx-font-size: 18; -fx-text-fill: black; -fx-font-weight: 500");
                }
                for (int j=i+1 ; j< playTime.size(); j++){
                    Label label2= (Label) vboxLyric.getChildren().get(j);
                    label2.setStyle("-fx-font-size: 18; -fx-text-fill: black; -fx-font-weight: 500");
                }
            }
        }
    }
}
