package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.Component.TopCardController;
import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Genre;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoverScreenController implements Initializable {
    public TabPane tabPaneTop;
    public Tab tabOffer;
    public Tab tabCategory;
    public Tab tabBillboard;
    public ToggleGroup Group3;
    public AnchorPane clipBanner;
    public AnchorPane banner1;
    public AnchorPane banner2;
    public Button navigateBtn;
    public AnchorPane banner11;
    public AnchorPane banner21;
    public ChoiceBox<String> choiceBoxSongs;
    public VBox billBoardTable;
    public ImageView imagePodcast;
    public Label namePodcast;
    public Label artistPodcast;
    public VBox categoryPane;
    public HBox hbox1;
    public HBox hbox2;
    public AnchorPane slideCardOffer;
    public AnchorPane outsideParent;
    private SingleSelectionModel<Tab> selectionModel;
    public List<Song> curPodcast;
    public MainScreenController mainScreenController;
    public ArrayList<Song> curListSong;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectionModel = tabPaneTop.getSelectionModel();
        Rectangle rectangle = new Rectangle(clipBanner.getPrefWidth(), clipBanner.getPrefHeight());
        clipBanner.setClip(rectangle);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/TopCard.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            billBoardTable.getChildren().add(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initPodcast();


    }

    public void initOffer() {
        try {
            PublicController publicController = new PublicController();
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/song/findAllSongs").asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Song> songs = null;
            songs = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });
            for (int i = 0; i < 5; i++) {
                Song curr_song = songs.get(i);
                AnchorPane anchorPane = publicController.musicItem(curr_song.getId(), curr_song.getAlbum().getImage(), curr_song.getName(), curr_song.getArtists().get(0).getName(), curr_song.getFile(), "PLAY", outsideParent, i, 590.0);
                anchorPane.setLayoutX(10 + 180 * i);
                slideCardOffer.getChildren().add(anchorPane);
            }
        } catch (JsonProcessingException | UnirestException e) {
            throw new RuntimeException(e);
        }

    }

    private void initPodcast() {
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/album/findAlbumById/62a85f35426e5d69d969eab2").asJson();

            curPodcast = PublicController.getSongFromJson(jsonNodeHttpResponse);
            namePodcast.setText(curPodcast.get(0).getName());
            imagePodcast.setImage(new Image(curPodcast.get(0).getAlbum().getImage()));
            artistPodcast.setText(curPodcast.get(0).getArtists().get(0).getName());

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTab(String buttonPane) {
        if (buttonPane.equals("offerBtn")) {
            selectionModel.select(tabOffer);
        } else if (buttonPane.equals("categoryBtn")) {
            selectionModel.select(tabCategory);
            initCategory();
        } else {
            selectionModel.select(tabBillboard);
            getBillBoardVietNam();
        }
    }

    private void initCategory() {
        PublicController publicController = new PublicController();
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/genre/findAllGenres").asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Genre> genres = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });
            for (int i = 0; i < 5; i++) {
                Genre genre = genres.get(i);
                AnchorPane anchorPane = publicController.genreItem(genre.getId(), genre.getImage(), genre.getName());
                HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                hbox1.getChildren().add(anchorPane);
            }
            for (int i = 5; i < genres.size(); i++) {
                Genre genre = genres.get(i);
                AnchorPane anchorPane = publicController.genreItem(genre.getId(), genre.getImage(), genre.getName());
                HBox.setMargin(anchorPane, new Insets(20, 10, 20, 10));
                hbox2.getChildren().add(anchorPane);
            }

        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void getBillBoardVietNam() {

        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/home/topSongVietNam").asJson();
            setDataBillBoard(jsonNodeHttpResponse);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDataBillBoard(HttpResponse<JsonNode> jsonNodeHttpResponse){
        ObjectMapper mapper = new ObjectMapper();
        List<Song> songs = null;
        try {
            songs = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });
            curListSong = (ArrayList<Song>) songs;

            PublicController.addDataToTable(billBoardTable, songs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    public void getBillBoardGlobal() {
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/home/topSongGlobal").asJson();
            setDataBillBoard(jsonNodeHttpResponse);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public void navigateNextBanner(ActionEvent mouseEvent) {
        if (banner1.isVisible()) {
            banner1.setVisible(false);
            banner2.setVisible(false);
            getBillBoardVietNam();
        } else {
            banner1.setVisible(true);
            banner2.setVisible(true);

            getBillBoardGlobal();
        }
    }


    public void changeToPodcast1(ActionEvent actionEvent) {
        namePodcast.setText(curPodcast.get(0).getName());
        imagePodcast.setImage(new Image(curPodcast.get(0).getAlbum().getImage()));
        artistPodcast.setText(curPodcast.get(0).getArtists().get(0).getName());
    }

    public void changeToPodcast2(ActionEvent actionEvent) {
        namePodcast.setText(curPodcast.get(1).getName());
        imagePodcast.setImage(new Image("http://localhost:8080/image/coding.png"));
        artistPodcast.setText(curPodcast.get(1).getArtists().get(0).getName());
    }

    public void playPodcast(MouseEvent mouseEvent) {
        if (Group3.getToggles().get(0).isSelected()) {
            mainScreenController.setMediaPlaying(curPodcast.get(0));
        } else {
            mainScreenController.setMediaPlaying(curPodcast.get(1));
        }
    }

    public void playAllSong(ActionEvent actionEvent) {
        mainScreenController.songs = curListSong;
        mainScreenController.setMediaPlaying(curListSong.get(0));
    }
}
