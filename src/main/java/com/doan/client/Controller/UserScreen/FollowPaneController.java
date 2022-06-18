package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Album;
import com.doan.client.Model.Artist;
import com.doan.client.Model.Genre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class FollowPaneController {
    public VBox bigPane;
    public HBox hbox1;
    public HBox hbox2;
    public MainScreenController mainScreenController;
    public void initAlbum() {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/user/findSavedAlbumFromUser?idUser=" + MainScreenController.idUser).asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Album> albums = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });
            PublicController publicController= new PublicController();
            hbox1.getChildren().clear();
            hbox2.getChildren().clear();
            if (albums.size() > 5){
                for (int i = 0; i < 5; i++) {
                    Album album = albums.get(i);
                    AnchorPane anchorPane = publicController.albumItem(album.getId(), album.getImage(), album.getName());
                    HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                    hbox1.getChildren().add(anchorPane);
                }
                for (int i = 5; i < albums.size(); i++) {
                    Album album = albums.get(i);
                    AnchorPane anchorPane = publicController.albumItem(album.getId(), album.getImage(), album.getName());
                    HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                    hbox2.getChildren().add(anchorPane);
                }
            }else{
                for (int i = 0; i < albums.size(); i++) {
                    Album album = albums.get(i);
                    AnchorPane anchorPane = publicController.albumItem(album.getId(), album.getImage(), album.getName());
                    HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                    hbox1.getChildren().add(anchorPane);
                }
            }


        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void initArtist() {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/user/findFollowedArtistFromUser?idUser=" + MainScreenController.idUser).asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Artist> artists = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });
            PublicController publicController= new PublicController();
            hbox1.getChildren().clear();
            hbox2.getChildren().clear();
            if (artists.size() > 5){
                for (int i = 0; i < 5; i++) {
                    Artist artist = artists.get(i);
                    AnchorPane anchorPane = publicController.artistItem(artist.getId(), artist.getImage(), artist.getName());
                    HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                    hbox1.getChildren().add(anchorPane);
                }
                for (int i = 5; i < artists.size(); i++) {
                    Artist artist = artists.get(i);
                    AnchorPane anchorPane = publicController.artistItem(artist.getId(), artist.getImage(), artist.getName());
                    HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                    hbox2.getChildren().add(anchorPane);
                }
            }else{
                for (Artist artist : artists) {
                    AnchorPane anchorPane = publicController.artistItem(artist.getId(), artist.getImage(), artist.getName());
                    HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                    hbox1.getChildren().add(anchorPane);
                }
            }


        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
