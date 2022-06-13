package com.doan.client.Controller.Component;

import com.doan.client.Controller.UserScreen.MainScreenController;
import com.doan.client.Model.Song;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;

public class MoreOptionCardController {
    public String idSong;
    public MainScreenController mainScreenController;
    public AnchorPane layer;
    public Button more;
    public String file;
    public String songName;

    public void addToNextPlayMedia(ActionEvent actionEvent) {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/song/findSongById/" + idSong).asJson();
            Song song = new Gson().fromJson(jsonNodeHttpResponse.getBody().toString(), Song.class);
            mainScreenController.songs.add(mainScreenController.songNumber + 1, song);
            more.getStyleClass().add("more_opacity");
            mainScreenController.homeScreenController.outsideParent.getChildren().remove(layer);

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

    }

    public void downloadMedia(ActionEvent actionEvent) {

        String newURL = file;
        try (InputStream in = new URL(newURL).openStream();
           ReadableByteChannel rbc = Channels.newChannel(in);
           FileOutputStream fos = new FileOutputStream("src/main/resources/com/doan/client/Music/" + songName + ".mp3")) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            more.getStyleClass().add("more_opacity");
            mainScreenController.homeScreenController.outsideParent.getChildren().remove(layer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Successful Save");
            alert.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
// Write your data

    }
}
