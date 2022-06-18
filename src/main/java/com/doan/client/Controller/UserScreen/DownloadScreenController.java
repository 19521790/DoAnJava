package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Object.AlbumOtd;
import com.doan.client.Model.Object.ArtistOtd;
import com.doan.client.Model.Song;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class DownloadScreenController implements Initializable {
    public Label listSongName;
    public Label songCount;
    public VBox downloadVbox;
    public MainScreenController mainScreenController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File directory = new File("src/main/resources/com/doan/client/Music/");
        File[] files = directory.listFiles();

        ArrayList<File> songs = new ArrayList<>();
        if (files != null) {
            songs.addAll(Arrays.asList(files));
        }
        String arrayName = "";
        for (int i = 0; i < songs.size(); i++) {

            File file = songs.get(i);
            arrayName += file.getName().replace(".m4a", "") + " • ";
            String curName= file.getName().replace(".m4a", "");
            //get data
            Song song = new Song();
            song.setId(curName);
            song.setName(curName);
            song.setDuration(0);
            song.setFile(file.toURI().toString());
            AlbumOtd albumOtd = new AlbumOtd();
            albumOtd.setImage("http://localhost:8080/image/unknown.jpg");
            ArtistOtd artist = new ArtistOtd();
            artist.setName("UNKNOWN");
            List<ArtistOtd> artists = new ArrayList<>();
            artists.add(artist);
            song.setArtists(artists);
            song.setAlbum(albumOtd);
            URL url1 = null;
            try {
                url1 = new URL("http://www.google.com");
                URLConnection connection = url1.openConnection();
                connection.connect();
                try {
                    HttpResponse<JsonNode> jsonNodeHttpResponse= Unirest.get("http://localhost:8080/song/findSongByName/" +curName ).asJson();
                    song=  new Gson().fromJson(jsonNodeHttpResponse.getBody().toString(), Song.class);
                } catch (UnirestException e) {
                    System.out.println(e);
                }
            } catch (IOException e) {
            }

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getStyleClass().add("top_five_card");
            anchorPane.setPrefWidth(940);
            anchorPane.setPrefHeight(40);

            Label indexSong = new Label();
            indexSong.setText(Integer.toString(i));
            indexSong.setLayoutY(7);
            indexSong.setLayoutX(14);

            Label labelSong = new Label();
            labelSong.setText(curName);
            labelSong.setLayoutX(100);
            labelSong.setLayoutY(7);

            ImageView imageView = new ImageView(new Image(song.getAlbum().getImage()));
            imageView.setLayoutX(52);
            imageView.setFitHeight(32);
            imageView.setFitWidth(32);

            Button button = new Button();
            FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
            fontAwesomeIconView.setGlyphName("PLAY");
            fontAwesomeIconView.setSize("20");
            button.setGraphic(fontAwesomeIconView);
            button.getStyleClass().add("top_five_button");

            Label labelDuration = new Label();
            labelDuration.setLayoutX(840);
            labelDuration.setLayoutY(10);
            labelDuration.setText(PublicController.setTimePlay(song.getDuration()));

            anchorPane.getChildren().addAll(indexSong, button, labelSong, imageView , labelDuration);
            button.setCursor(Cursor.HAND);
            Song finalSong = song;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mainScreenController.setMediaPlaying(finalSong);
                }
            });
            downloadVbox.getChildren().add(anchorPane);

        }

        listSongName.setText(arrayName);
        songCount.setText(songs.size() + " songs");

    }
}
