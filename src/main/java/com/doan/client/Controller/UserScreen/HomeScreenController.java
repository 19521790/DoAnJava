package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.Component.TopFiveCardController;
import com.doan.client.Controller.PublicController;

import com.doan.client.Model.Album;
import com.doan.client.Model.Artist;
import com.doan.client.Model.Genre;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    public TextField searchBar;
    public AnchorPane clipPane;
    public AnchorPane slidePane;

    public AnchorPane clipPaneBackground;
    public AnchorPane slideCard;
    public AnchorPane outsideParent;
    public ImageView testImage;
    public VBox homeVbox;
    public AnchorPane clipPaneBackgroundArtist;
    public AnchorPane slideCardArtist;
    public AnchorPane clipPaneBackgroundAlbum;
    public AnchorPane slideCardAlbum;
    public AnchorPane clipPaneBackgroundGenre;
    public AnchorPane slideCardGenre;
    public VBox newPopularVbox;
    public ImageView imageNewPopular;
    PublicController publicController;
    public List<Song> listSongBanner;
    public MainScreenController mainScreenController;
    public List<Song> currentLastListeningList;
    PublicController publicControllerArtist;
    PublicController publicControllerAlbum;
    PublicController publicControllerGenre;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(900);
        rectangle.setHeight(200);
        clipPane.setClip(rectangle);

        publicController = new PublicController();
        publicControllerArtist = new PublicController();
        publicControllerAlbum = new PublicController();
        publicControllerGenre = new PublicController();

        Rectangle rectangle1 = new Rectangle(clipPaneBackground.getPrefWidth(), clipPaneBackground.getPrefHeight());
        clipPaneBackground.setClip(rectangle1);
        Rectangle rectangle2 = new Rectangle(clipPaneBackground.getPrefWidth(), clipPaneBackground.getPrefHeight());
        clipPaneBackgroundArtist.setClip(rectangle2);
        Rectangle rectangle3 = new Rectangle(clipPaneBackground.getPrefWidth(), clipPaneBackground.getPrefHeight());
        clipPaneBackgroundAlbum.setClip(rectangle3);
        Rectangle rectangle4 = new Rectangle(clipPaneBackground.getPrefWidth(), clipPaneBackground.getPrefHeight());
        clipPaneBackgroundGenre.setClip(rectangle4);


//        for (int i =0 ; i < 9; i++ ){
//            AnchorPane anchorPane = publicController.musicItem("http://localhost:8080/image/loading.gif","Born to Die", "Lana Del Rey", "PLAY", outsideParent, i);
//            anchorPane.setLayoutX(10+ 180*i);
//            slideCard.getChildren().add(anchorPane);
//        }
        currentLastListeningList = new ArrayList<>();
        initArtist();
        initAlbum();
        initGenre();

    }

    public void initNewAndPopular() {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/home/newUpdate").asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Song> listSong = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });

            imageNewPopular.setImage(new Image(listSong.get(0).getAlbum().getImage()));

            for (int i = 0; i < 5; i++) {
                FXMLLoader topFiveCardFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/TopFiveCard.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = topFiveCardFxmlLoader.load();
                    TopFiveCardController topFiveCardController = topFiveCardFxmlLoader.getController();
                    topFiveCardController.setSongToComponent(listSong.get(i), i + 1);
                    topFiveCardController.mainScreenController = mainScreenController;
                    newPopularVbox.getChildren().add(anchorPane);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } catch (UnirestException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void initGenre() {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/genre/findAllGenres").asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Genre> genres = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });

            for (int i = 0; i < genres.size(); i++) {
                Genre genre = genres.get(i);
                AnchorPane anchorPane = publicController.genreItem(genre.getId(), genre.getImage(), genre.getName());
                anchorPane.setLayoutX(10 + 180 * i);
                slideCardGenre.getChildren().add(anchorPane);
            }
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void initAlbum() {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/album/findAllAlbums").asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Album> albums = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });

            for (int i = 0; i < albums.size(); i++) {
                Album album = albums.get(i);
                AnchorPane anchorPane = publicController.albumItem(album.getId(), album.getImage(), album.getName());
                anchorPane.setLayoutX(10 + 180 * i);
                slideCardAlbum.getChildren().add(anchorPane);
            }

        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void initArtist() {
        HttpResponse<JsonNode> jsonNodeHttpResponse = null;
        try {
            jsonNodeHttpResponse = Unirest.get("http://localhost:8080/artist/findAllArtists").asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Artist> artists = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });

            for (int i = 0; i < artists.size(); i++) {
                Artist artist = artists.get(i);
                AnchorPane anchorPane = publicController.artistItem(artist.getId(), artist.getImage(), artist.getName());
                anchorPane.setLayoutX(10 + 180 * i);
                slideCardArtist.getChildren().add(anchorPane);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public void initFirstMedia() {
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/song/findAllSongs").asJson();
            ObjectMapper mapper = new ObjectMapper();
            listSongBanner = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });
            Song curr_song = listSongBanner.get(0);
            mainScreenController.setMediaPlaying(curr_song);

            AnchorPane anchorPane = publicController.musicItem(curr_song.getId(), curr_song.getAlbum().getImage(), curr_song.getName(), curr_song.getArtists().get(0).getName(), curr_song.getFile(), "PLAY", outsideParent, 0, 520.0);
            anchorPane.setLayoutX(10 + 180 * 0);
            slideCard.getChildren().add(anchorPane);

        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 9; i++) {
            ImageView imageView = new ImageView(new Image(listSongBanner.get(i).getAlbum().getImage()));
            imageView.setFitWidth(250);
            imageView.setFitHeight(170);
            imageView.setLayoutY(15);
            imageView.setLayoutX(25 + 300 * i);
            Rectangle clip = new Rectangle();
            clip.setWidth(250);
            clip.setHeight(170);
            clip.setArcWidth(50);
            clip.setArcHeight(50);
            imageView.setClip(clip);
            imageView.setCursor(Cursor.HAND);
            slidePane.getChildren().add(imageView);
            imageView.setId(Integer.toString(i));
            imageView.setOnMouseClicked(this::playMusic);
        }

    }

    public void addChildrenToLastListening(List<Song> songList) {
        currentLastListeningList = songList;
        slideCard.getChildren().clear();
        for (int i = 0; i < songList.size(); i++) {
            Song curr_song = songList.get(i);
            AnchorPane anchorPane = publicController.musicItem(curr_song.getId(), curr_song.getAlbum().getImage(), curr_song.getName(), curr_song.getArtists().get(0).getName(), curr_song.getFile(), "PLAY", outsideParent, i, 520.0);
            anchorPane.setLayoutX(10 + 180 * i);
            slideCard.getChildren().add(anchorPane);
        }
        if (mainScreenController.login) {
            new Thread(() -> {
                try {
                    HttpResponse<String> stringHttpResponse = Unirest.put("http://localhost:8080/user/addLastListenSong?idUser=" + mainScreenController.user.getId() + "&idSong=" + songList.get(0).getId()).asString();
                } catch (UnirestException e) {
                    System.out.println("Not found id");
                }
            }).start();
        }
    }

    private void playMusic(javafx.scene.input.MouseEvent mouseEvent) {

        ImageView imageView = (ImageView) mouseEvent.getSource();
        int id = Integer.parseInt(imageView.getId());
        Song curr_song = listSongBanner.get(id);
        mainScreenController.setMediaPlaying(curr_song);

    }


    public int index = 0;

    public void goPreviousSlide(ActionEvent mouseEvent) {
        if (index > 0) {
            index = index - 1;
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(slidePane.translateXProperty(), -300 * (index + 1))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(slidePane.translateXProperty(), -300 * (index))));
            timeline.play();

        }
    }

    public void goNextSlide(ActionEvent mouseEvent) {
        if (index < 6) {
            index = index + 1;
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(slidePane.translateXProperty(), -300 * (index - 1))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(slidePane.translateXProperty(), -300 * (index))));
            timeline.play();

        }
    }

    public void goNextCard(ActionEvent actionEvent) {
        moveSlideNext(slideCard, publicController);
    }

    public void goPreviousCard(ActionEvent actionEvent) {
        moveSlidePrevious(slideCard, publicController);
    }

    public void goNextCardArtist(ActionEvent actionEvent) {
        moveSlideNext(slideCardArtist, publicControllerArtist);
    }

    public void goPreviousCardArtist(ActionEvent actionEvent) {
        moveSlidePrevious(slideCardArtist, publicControllerArtist);

    }

    public void moveSlidePrevious(AnchorPane anchorPane, PublicController publicController) {
        if (publicController.currentScrollIndex > 0) {
            publicController.currentScrollIndex = publicController.currentScrollIndex - 1;
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(anchorPane.translateXProperty(), -180 * (publicController.currentScrollIndex + 1))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(anchorPane.translateXProperty(), -180 * (publicController.currentScrollIndex))));
            timeline.play();

        }
    }

    public void moveSlideNext(AnchorPane anchorPane, PublicController publicController) {
        if (publicController.currentScrollIndex < 6) {
            publicController.currentScrollIndex += 1;
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(anchorPane.translateXProperty(), -180 * (publicController.currentScrollIndex - 1))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(anchorPane.translateXProperty(), -180 * (publicController.currentScrollIndex))));
            timeline.play();
        }
    }

    public void goNextCardAlbum(ActionEvent actionEvent) {
        moveSlideNext(slideCardAlbum, publicControllerAlbum);
    }

    public void goPreviousCardAlbum(ActionEvent actionEvent) {
        moveSlidePrevious(slideCardAlbum, publicControllerAlbum);
    }

    public void goNextCardGenre(ActionEvent actionEvent) {
        moveSlideNext(slideCardGenre, publicControllerGenre);
    }

    public void goPreviousCardGenre(ActionEvent actionEvent) {
        moveSlidePrevious(slideCardGenre, publicControllerGenre);
    }

    public void goToTopVietNam(MouseEvent mouseEvent) {
        mainScreenController.discoverBtn.fire();
        mainScreenController.discoverController.tabPaneTop.getSelectionModel().select(2);


    }

    public void goToTopGlobal(MouseEvent mouseEvent) {
        mainScreenController.discoverBtn.fire();
        mainScreenController.discoverController.tabPaneTop.getSelectionModel().select(2);
        mainScreenController.discoverController.navigateBtn.fire();

    }
}
