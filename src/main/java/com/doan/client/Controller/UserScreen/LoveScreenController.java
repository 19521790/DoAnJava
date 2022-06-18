package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.Component.MoreOptionCardController;
import com.doan.client.Controller.Component.TopCardController;
import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoveScreenController {
    public Label listSongName;
    public Label songCount;
    public MainScreenController mainScreenController;
    public VBox likeTable;
    public AnchorPane parentAnchorPane;

    public void initSongLiked() {

        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/playlist/findPlaylistById/" + MainScreenController.idPlaylistLike).asJson();
            JSONArray jsonArray = new JSONArray(jsonNodeHttpResponse.getBody().getObject().get("songs").toString());
            List<Song> songs = new ArrayList<>();
            String stringListName = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                Song song = new Gson().fromJson(String.valueOf(jsonobject), Song.class);
                songs.add(song);
                stringListName += song.getName() + " • ";
            }
            listSongName.setText(stringListName);
            songCount.setText(Integer.toString(songs.size()) + " songs");

            PublicController.addDataToTable(likeTable, songs);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

}
