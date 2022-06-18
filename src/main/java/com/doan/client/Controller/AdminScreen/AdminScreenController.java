package com.doan.client.Controller.AdminScreen;

import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Album;
import com.doan.client.Model.Artist;
import com.doan.client.Model.Genre;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {


    public ToggleGroup Group1;
    public AnchorPane mainBoard;

    public ToggleButton songTabBtn;

    public ToggleButton albumTabBtn;

    public ToggleButton artistTabBtn;


    public  static List<Song> listSong;
    public  static List<Artist> listArtist;
    public  static List<Album> listAlbum;
    public  static List<Genre> listGenre;
    public AnchorPane adminMainBoard;
    public ToggleButton genreTabBtn;
    AnchorPane songPane;
    AnchorPane artistPane;
    AnchorPane albumPane ;
    AnchorPane genrePane ;
    private SongEditScreenController songEditScreenController;
    private ArtistEditScreenController artistEditScreenController;
    private  AlbumEditScreenController albumEditScreenController;
    private  GenreEditScreenController genreEditScreenController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getSongData();
        getArtistData();
        getAlbumData();
        getGenreData();
        FXMLLoader fxmlSongLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/SongEditScreen.fxml"));
        FXMLLoader fxmlArtistLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/ArtistEditScreen.fxml"));
        FXMLLoader fxmlAlbumLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/AlbumEditScreen.fxml"));
        FXMLLoader fxmlGenreLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/GenreEditScreen.fxml"));
        try {
            songPane = fxmlSongLoader.load();
            songEditScreenController = fxmlSongLoader.getController();
            artistPane = fxmlArtistLoader.load();
            artistEditScreenController= fxmlArtistLoader.getController();
            albumPane = fxmlAlbumLoader.load();
            albumEditScreenController= fxmlAlbumLoader.getController();
            genrePane= fxmlGenreLoader.load();
            genreEditScreenController= fxmlGenreLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        songTabBtn.fire();



    }

    public static void getSongData() {
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/song/findAllSongs").asJson();
            String jsonValue = apiResponse.getBody().toString();
            ObjectMapper mapper = new ObjectMapper();
            listSong = mapper.readValue(jsonValue, new TypeReference<>() {
            });
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static  void getArtistData() {
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/artist/findAllArtists").asJson();
            String jsonValue = apiResponse.getBody().toString();
            ObjectMapper mapper = new ObjectMapper();
            listArtist = mapper.readValue(jsonValue, new TypeReference<>() {
            });

        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static  void getAlbumData() {
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/album/findAllAlbums").asJson();
            String jsonValue = apiResponse.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();

            listAlbum = mapper.readValue(jsonValue, new TypeReference<>() {
            });
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static  void getGenreData() {
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/genre/findAllGenres").asJson();
            String jsonValue = apiResponse.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();
            listGenre = mapper.readValue(jsonValue, new TypeReference<>() {
            });
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMusicTab(ActionEvent actionEvent) {

        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource();
        if (!toggleButton.isSelected()) {
            toggleButton.fire();
        }
        List<Toggle> toggleButtonList = toggleButton.getToggleGroup().getToggles();

        for (Toggle toggle : toggleButtonList) {
            ToggleButton indexToggleButton = (ToggleButton) toggle;
            if (indexToggleButton.isSelected()) {
                indexToggleButton.setStyle("-fx-background-color: #3b75ff; -fx-text-fill: white");

                MaterialIconView materialIconView = (MaterialIconView) indexToggleButton.getGraphic();
                materialIconView.setFill(Paint.valueOf("white"));

                pushScreen(indexToggleButton);
            } else {
                indexToggleButton.setStyle("-fx-background-color: white; -fx-text-fill: black");

                MaterialIconView materialIconView = (MaterialIconView) indexToggleButton.getGraphic();
                materialIconView.setFill(Paint.valueOf("black"));

            }
        }

    }

    public void pushScreen(ToggleButton toggleButton) {

        if (toggleButton.getId().equals("songTabBtn")) {
            adminMainBoard.getChildren().setAll(songPane);
            songEditScreenController.resetAllField();

        } else if (toggleButton.getId().equals("artistTabBtn")) {
            adminMainBoard.getChildren().setAll(artistPane);
            artistEditScreenController.resetAllField();

        } else if (toggleButton.getId().equals("albumTabBtn")) {
            adminMainBoard.getChildren().setAll(albumPane);
            albumEditScreenController.resetAllField();
        }else{
            adminMainBoard.getChildren().setAll(genrePane);
            genreEditScreenController.resetAllField();
        }
    }


    public void backToUserScreen(ActionEvent actionEvent) {
        PublicController.primaryStage.setScene(PublicController.userScene);
    }

}
