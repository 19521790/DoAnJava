package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.AdminScreen.AdminScreenController;
import com.doan.client.Controller.Component.EditPlaylistFormController;
import com.doan.client.Controller.Component.TopCardController;
import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Playlist;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlayListScreenController implements Initializable {
    public static String image;
    public static String name;
    public ImageView avatarUser;
    public Label username;
    public static boolean isSetUser;
    public Label playlistName;
    public AnchorPane playlistCover;
    public EditPlaylistFormController editPlaylistFormController;
    public AnchorPane editPlaylistPane;
    public ImageView imagePlaylist;
    public String curId;
    public VBox playlistTable;
    public  MainScreenController mainScreenController;
    public AnchorPane searchPane;
    public List<Song> curSongs;
    public VBox searchVboxPane;
    public List<Song> songs;
    public TextField addSongField;
    public AnchorPane slideCardOffer;
    public Label lableRecommend;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isSetUser) {
            avatarUser.setImage(new Image(image));
            username.setText(name);
        } else {
            username.setText("Anonymous");
        }
        initSongSearch();
    }

    private void initSongSearch() {

        try {
            HttpResponse<JsonNode> httpResponse= Unirest.get("http://localhost:8080/song/findAllSongs").asJson();
            songs = new ObjectMapper().readValue(httpResponse.getBody().toString(), new TypeReference<List<Song>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            for (int i= 0; i< songs.size() ; i++){
                addSongToSearchBox(i, songs);
            }
        } catch (JsonProcessingException | UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSongToSearchBox(int i, List<Song> songs) {
        Button button= new Button();
        button.setText(songs.get(i).getName());
        button.setOnAction(actionEvent -> addSongToPlaylist(songs.get(i)));
        button.setPrefWidth(320);
        button.getStyleClass().add("itemSearch");
        searchVboxPane.getChildren().add(button);
    }

    private void addSongToPlaylist(Song song) {
        curSongs.add(song);
        FXMLLoader cardFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/TopCard.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = cardFxmlLoader.load();
            TopCardController topCardController = cardFxmlLoader.getController();
            topCardController.mainScreenController = mainScreenController;
            topCardController.setCard(song, curSongs.size());
            topCardController.playListScreenController= this;
            topCardController.moreBtn.setOnAction(actionEvent -> topCardController.showMoreCardPlaylist());
            playlistTable.getChildren().add(playlistTable.getChildren().size()-2,anchorPane);
            searchPane.setVisible(false);
            new Thread(()->{
                try {
                    HttpResponse<String> jsonNodeHttpResponse= Unirest.put("http://localhost:8080/playlist/addSongToPlaylist?idPlaylist="+ curId +"&idSong=" + song.getId()).asString();
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.setText(playlistName);
    }

    public void setEditPaneVisible(ActionEvent actionEvent) {
        editPlaylistPane.setVisible(true);
    }

    public void initPlaylist(String id) {
        curId= id;
        editPlaylistFormController.playListScreenController= this;
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse= Unirest.get("http://localhost:8080/playlist/findPlaylistById/" + id).asJson();
            Playlist playlist = new Gson().fromJson(jsonNodeHttpResponse.getBody().toString(), Playlist.class);
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(220);
            rectangle.setHeight(220);
            rectangle.setArcWidth(40);
            rectangle.setArcHeight(40);
            imagePlaylist.setClip(rectangle);
            imagePlaylist.setImage(new Image(playlist.getImage()));
            playlistName.setText(playlist.getName());
            curSongs= playlist.getSongs();
            playlistTable.getChildren().remove(0, playlistTable.getChildren().size()-2);
            for (int i = 0; i < curSongs.size(); i++) {
                FXMLLoader cardFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/TopCard.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = cardFxmlLoader.load();
                    TopCardController topCardController = cardFxmlLoader.getController();
                    topCardController.mainScreenController = mainScreenController;
                    topCardController.setCard(curSongs.get(i), i + 1);
                    topCardController.playListScreenController= this;
                    topCardController.moreBtn.setOnAction(actionEvent -> topCardController.showMoreCardPlaylist());
                    playlistTable.getChildren().add(playlistTable.getChildren().size()-2,anchorPane);

                } catch (IOException e) {

                    throw new RuntimeException(e);
                }
            }

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

    }

    public void hidePane(MouseEvent mouseEvent) {
        searchPane.setVisible(false);
    }

    public void setVisiblePane(KeyEvent keyEvent) {


        searchPane.setVisible(true);
        List<Song> listFilter = new ArrayList<>(songs.stream().filter(p -> p.getName().startsWith(addSongField.getText())).toList());
        for (Song song: curSongs){
             listFilter.remove(song);
        }
        searchVboxPane.getChildren().clear();
        for (int i = listFilter.size()-1; i >-1; i--){
            addSongToSearchBox(i, listFilter);
        }

    }

    public void playAllSong(ActionEvent actionEvent) {
        mainScreenController.songs= (ArrayList<Song>) curSongs;
        mainScreenController.setMediaPlaying(curSongs.get(0));
    }

    public void initRecommend(String id) {
        PublicController.initOfferPane(slideCardOffer, null, "http://localhost:8080/recommend/getSongForPlaylistRecommend/" + id, lableRecommend);
    }
}
