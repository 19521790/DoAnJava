package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Album;
import com.doan.client.Model.Artist;
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



    public AnchorPane adminMainBoard;
    AnchorPane songPane;
    AnchorPane artistPane;
    AnchorPane albumPane ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlSongLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/SongEditScreen.fxml"));
        FXMLLoader fxmlArtistLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/ArtistEditScreen.fxml"));
        FXMLLoader fxmlAlbumLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/AlbumEditScreen.fxml"));
        try {
            songPane = fxmlSongLoader.load();
            artistPane = fxmlArtistLoader.load();
            albumPane = fxmlAlbumLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        songTabBtn.fire();

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
        } else if (toggleButton.getId().equals("artistTabBtn")) {
            adminMainBoard.getChildren().setAll(artistPane);
        } else {
            adminMainBoard.getChildren().setAll(albumPane);
        }
    }


}
