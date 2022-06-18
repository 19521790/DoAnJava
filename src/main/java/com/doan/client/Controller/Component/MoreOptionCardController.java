package com.doan.client.Controller.Component;

import com.doan.client.Controller.UserScreen.MainScreenController;
import com.doan.client.Controller.UserScreen.PlayListScreenController;
import com.doan.client.Model.Playlist;
import com.doan.client.Model.Song;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.ResourceBundle;

public class MoreOptionCardController implements Initializable {
    public String idSong;
    public MainScreenController mainScreenController;
    public AnchorPane layer;
    public Button more;
    public String file;
    public String songName;
    public Button thumpLikeBtn;
    public FontAwesomeIconView thumpLike;
    public MenuButton menuCard;
    public Button removeBtn;
    public PlayListScreenController playListScreenController;

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
           FileOutputStream fos = new FileOutputStream("src/main/resources/com/doan/client/Music/" + songName + ".m4a")) {
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

    public void likeSong(ActionEvent actionEvent) {
        if (MainScreenController.likedList.contains(idSong)){
            thumpLike.setFill(Paint.valueOf("black"));
            thumpLikeBtn.setText("Like");
            MainScreenController.likedList.remove(idSong);
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Successful remove Like");
            alert.show();

            new Thread(()->{
                try {
                    HttpResponse<String> stringHttpResponse= null;
                    stringHttpResponse = Unirest.put("http://localhost:8080/playlist/removeSongFromPlaylist?idPlaylist="+ MainScreenController.idPlaylistLike+"&idSong=" + idSong).asString();
                    System.out.println(stringHttpResponse.getBody());
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            if (mainScreenController.likeBtn.isSelected()){
                mainScreenController.likeBtn.fire();
            }

        }else{
            thumpLike.setFill(Paint.valueOf("#3b75ff"));
            thumpLikeBtn.setText("Liked");
            MainScreenController.likedList.add(idSong);

            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Successful add Like");
            alert.show();
            new Thread(() -> {
                try {
                    HttpResponse<String> stringHttpResponse = null;
                    stringHttpResponse = Unirest.put("http://localhost:8080/playlist/addSongToPlaylist?idPlaylist=" + MainScreenController.idPlaylistLike + "&idSong=" + idSong).asString();
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        }
    }
    public  void setThumpLikeBtn(){
        if (MainScreenController.likedList.contains(idSong)){
            thumpLike.setFill(Paint.valueOf("#3b75ff"));

            thumpLikeBtn.setText("Liked");
        }else{
            thumpLike.setFill(Paint.valueOf("black"));
            thumpLikeBtn.setText("Like");
        }
    }


    public void addToQueue(ActionEvent actionEvent) {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/song/findSongById/" + idSong).asJson();
            Song song = new Gson().fromJson(jsonNodeHttpResponse.getBody().toString(), Song.class);
            mainScreenController.songs.add(song);
            more.getStyleClass().add("more_opacity");
            mainScreenController.homeScreenController.outsideParent.getChildren().remove(layer);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

    }
    public void initMenu(){
        for (Playlist playlist : MainScreenController.playlistOfUser){
            MenuItem menuItem= new MenuItem();
            menuItem.setText(playlist.getName());
            menuCard.getItems().add(menuItem);
            menuItem.setOnAction(actionEvent -> addData(playlist.getId()));
        }
    }

    private void addData(String id) {
        try {
            HttpResponse<String> jsonNodeHttpResponse= Unirest.put("http://localhost:8080/playlist/addSongToPlaylist?idPlaylist="+id+"&idSong=" + idSong).asString();
            System.out.println(jsonNodeHttpResponse.getBody());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void removeSongFromPlaylist(ActionEvent actionEvent) {
    }
}
