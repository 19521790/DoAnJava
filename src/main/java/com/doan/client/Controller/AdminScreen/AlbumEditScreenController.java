package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Artist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.util.List;

public class AlbumEditScreenController {
    public HBox listAddedSong1;
    public VBox listNotAddedSong1;
    public ScrollPane scrollPaneSearch1;
    public TextField addSongField;
    public ImageView imageAlbum;

    public void filterSong(KeyEvent keyEvent) {
    }

    public void showSearchForm(MouseEvent mouseEvent) {
    }


    public void addAlbumArtist(ActionEvent actionEvent) {
    }
}
