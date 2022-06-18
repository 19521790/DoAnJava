package com.doan.client.Controller;

import com.doan.client.Controller.Component.MoreOptionCardController;
import com.doan.client.Controller.Component.TopCardController;
import com.doan.client.Controller.UserScreen.ComponentScreenController;
import com.doan.client.Controller.UserScreen.MainScreenController;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PublicController {
    public int currentScrollIndex = 0;
    public static MainScreenController mainScreenController;
    public static Scene userScene;
    public static Stage primaryStage;

    public ImageView setImageView(double arcWidth, double arcHeight, String imgUrl){
        ImageView imageView = new ImageView(new Image(imgUrl));
        imageView.setFitWidth(130);
        imageView.setFitHeight(135);
        imageView.setLayoutX(15);
        imageView.setLayoutY(14);
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(130);
        rectangle.setHeight(130);
        rectangle.setArcWidth(arcWidth);
        rectangle.setArcHeight(arcHeight);
        imageView.setClip(rectangle);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.getStyleClass().add("image_shadow");
        imageView.setImage(image);
        return  imageView;
    }
    public AnchorPane setAnchorPane(double width, double height){

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(width);
        anchorPane.setPrefHeight(height);
        anchorPane.getStyleClass().add("background_shadow");
        return anchorPane;
    }
    public Button createButton(FontAwesomeIconView fontAwesomeIconView, String idArtist){
        fontAwesomeIconView.setSize("20");
        fontAwesomeIconView.setFill(Paint.valueOf("white"));
        Button button = new Button();
        button.setPrefWidth(36);
        button.setPrefHeight(36);
        button.setLayoutY(124);
        button.setLayoutX(117);
        button.setGraphic(fontAwesomeIconView);
        button.getStyleClass().add("button_opacity");
        button.setCursor(Cursor.HAND);
        button.setId(idArtist);
        return  button;
    }
    public  Label createLabel(String name){
        Label titleLabel = new Label();
        titleLabel.setText(name);
        titleLabel.setLayoutX(22);
        titleLabel.setLayoutY(160);
        titleLabel.getStyleClass().add("label_card");
        return  titleLabel;
    }
    public Label createDescription(String name){
        Label descriptionLabel = new Label();
        descriptionLabel.setText(name);
        descriptionLabel.setLayoutX(22);
        descriptionLabel.setLayoutY(181);
        return descriptionLabel;
    }
    public AnchorPane musicItem(String idSong, String imgUrl, String title, String description, String file, String type, AnchorPane parentAnchorPane, int position, Double layoutY) {

        AnchorPane anchorPane = setAnchorPane(160,220);

        ImageView imageView = setImageView(10, 10, imgUrl);
        Label titleLabel = createLabel(title);


        Label descriptionLabel = createDescription(description);

        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        if (type.equals("PLAY")) {
            fontAwesomeIconView.setGlyphName("PLAY");
            fontAwesomeIconView.setWrappingWidth(13);
        } else {
            fontAwesomeIconView.setGlyphName("SHARE");
            fontAwesomeIconView.setWrappingWidth(19.6);
        }
        Button button = createButton(fontAwesomeIconView, idSong);
        button.setOnAction(this::playMusic);
        Button more = new Button();
        MaterialIconView materialIconView = new MaterialIconView();
        materialIconView.setGlyphName("MORE_HORIZ");
        materialIconView.setSize("18");
        more.setGraphic(materialIconView);
        more.setText("");
        more.setLayoutY(180);
        more.setLayoutX(120);
        more.getStyleClass().add("more_opacity");
        more.setCursor(Cursor.HAND);
        more.setBackground(Background.fill(Paint.valueOf("white")));

        more.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (mainScreenController.login) {
                    more.getStyleClass().remove("more_opacity");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/MoreOptionCard.fxml"));
                    try {
                        AnchorPane card = fxmlLoader.load();
                        MoreOptionCardController moreOptionCardController = fxmlLoader.getController();
                        moreOptionCardController.idSong = idSong;
                        moreOptionCardController.mainScreenController = mainScreenController;
                        card.setLayoutY(layoutY);
                        card.setLayoutX(180 * (position - currentScrollIndex));

                        AnchorPane layer = new AnchorPane();
                        layer.setPrefWidth(parentAnchorPane.getPrefWidth());
                        layer.setPrefHeight(parentAnchorPane.getPrefHeight());
                        layer.getChildren().add(card);

                        parentAnchorPane.getChildren().add(layer);
                        layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                parentAnchorPane.getChildren().remove(layer);
                                more.getStyleClass().add("more_opacity");
                            }
                        });
                        moreOptionCardController.layer = layer;
                        moreOptionCardController.more = more;
                        moreOptionCardController.file = file;
                        moreOptionCardController.songName = title;
                        moreOptionCardController.setThumpLikeBtn();
                        moreOptionCardController.initMenu();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Please login to use these features");
                    alert.show();
                    mainScreenController.loginPaneFromHome.setVisible(true);
                }
            }
        });
        anchorPane.getChildren().add(titleLabel);
        anchorPane.getChildren().add(descriptionLabel);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(more);


        return anchorPane;
    }

    private void playMusic(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/song/findSongById/" + button.getId()).asJson();
            Song song = new Gson().fromJson(jsonNodeHttpResponse.getBody().toString(), Song.class);
            mainScreenController.setMediaPlaying(song);
            mainScreenController.playMediaBtn.fire();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }


    }

    public static String setTimePlay(double current) {
        int minutePlay = (int) Math.floor(current / 60);

        int secondPlay = (int) (Math.round(current) - Math.floor(current / 60) * 60);

        if (secondPlay == 60) {
            secondPlay = 0;
            minutePlay += 1;
        }
        String labelMinutePlay = String.valueOf(minutePlay);
        String labelSecondPlay = String.valueOf(secondPlay);
        if (secondPlay < 10) {
            labelSecondPlay = "0" + labelSecondPlay;
        }
        if (minutePlay < 10) {
            labelMinutePlay = "0" + labelMinutePlay;
        }
        return labelMinutePlay + ":" + labelSecondPlay;
    }

    public AnchorPane artistItem(String idArtist, String imgUrl, String name) {
        AnchorPane anchorPane = setAnchorPane(160, 220);
        ImageView imageView= setImageView(130, 130, imgUrl);
        Label titleLabel = createLabel(name);

        Label descriptionLabel = createDescription("Artist");


        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        Button button = createButton(fontAwesomeIconView, idArtist);

        if (MainScreenController.artistFollowed!= null){
            if (MainScreenController.artistFollowed.contains(idArtist)){
                fontAwesomeIconView.setGlyphName("CHECK");
                button.setOnAction(actionEvent -> unFollowArtist(idArtist, button, fontAwesomeIconView));
            }else{
                fontAwesomeIconView.setGlyphName("PLUS");
                button.setOnAction(actionEvent -> followArtist(idArtist, button, fontAwesomeIconView));
            }
        }else{
            fontAwesomeIconView.setGlyphName("PLUS");
            button.setOnAction(actionEvent -> followArtist(idArtist, button, fontAwesomeIconView));
        }

        anchorPane.getChildren().add(titleLabel);
        anchorPane.getChildren().add(descriptionLabel);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(button);
        anchorPane.setOnMouseClicked(mouseEvent -> goToArtist(idArtist));
        anchorPane.setCursor(Cursor.HAND);
        return anchorPane;
    }

    private void followArtist(String idArtist, Button button, FontAwesomeIconView fontAwesomeIconView) {
        if (mainScreenController.login) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Followed Artist");
            alert.show();
            MainScreenController.artistFollowed.add(idArtist);
            button.setOnAction(actionEvent -> unFollowAlbum(idArtist, button, fontAwesomeIconView));
            fontAwesomeIconView.setGlyphName("CHECK");
            new Thread(() -> {
                HttpResponse<String> stringHttpResponse = null;
                try {
                    stringHttpResponse = Unirest.put("http://localhost:8080/user/followArtist?idUser=" + MainScreenController.idUser + "&idArtist=" + idArtist).asString();
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(stringHttpResponse.getBody());
            }).start();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Please login to Follow Artist");
            alert.show();
            mainScreenController.loginPaneFromHome.setVisible(true);
        }
    }

    private void unFollowArtist(String idArtist, Button button, FontAwesomeIconView fontAwesomeIconView) {
        if (mainScreenController.login) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(" Unfollow Artist success");
            alert.show();
            MainScreenController.artistFollowed.remove(idArtist);
            button.setOnAction(actionEvent -> followAlbum(idArtist, button, fontAwesomeIconView));
            fontAwesomeIconView.setGlyphName("PLUS");
            new Thread(() -> {
                HttpResponse<String> stringHttpResponse = null;
                try {
                    stringHttpResponse = Unirest.put("http://localhost:8080/user/unfollowArtist?idUser=" + MainScreenController.idUser + "&idArtist=" +idArtist).asString();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (mainScreenController.followsBtn.isSelected()){
                                mainScreenController.followsBtn.fire();
                            }
                        }
                    });

                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(stringHttpResponse.getBody());
            }).start();
        } else {
            mainScreenController.loginPaneFromHome.setVisible(true);
        }
    }

    private void goToArtist(String idArtist) {

        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/artist/findArtistById/" + idArtist).asJson();

            FXMLLoader artistScreenFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/ComponentScreen.fxml"));
            AnchorPane anchorPane = artistScreenFxmlLoader.load();
            ComponentScreenController artistScreenController = artistScreenFxmlLoader.getController();
            artistScreenController.mainScreenController = mainScreenController;
            mainScreenController.mainBoard.setContent(anchorPane);
            mainScreenController.mainBoard.setVvalue(0);
            artistScreenController.setImageBanner("http://localhost:8080/image/bannerArtist.jpg");
            artistScreenController.bannerTitle.setText("Artist");
            artistScreenController.setBanner(jsonNodeHttpResponse);
            mainScreenController.searchBarPane.setVisible(false);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public AnchorPane albumItem(String idAlbum, String imgUrl, String name) {
        AnchorPane anchorPane = setAnchorPane(160, 200);
        anchorPane.getStyleClass().add("background_shadow");

        ImageView imageView = setImageView(10, 10, imgUrl);

        Label titleLabel = createLabel(name);
        Label descriptionLabel = createDescription("Album");


        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        Button button =  createButton(fontAwesomeIconView, idAlbum);

        if (MainScreenController.albumFollowed!= null){
            if (MainScreenController.albumFollowed.contains(idAlbum)){
                System.out.println("hello");
                fontAwesomeIconView.setGlyphName("CHECK");
                button.setOnAction(actionEvent -> unFollowAlbum(idAlbum, button, fontAwesomeIconView));
            }else{

                fontAwesomeIconView.setGlyphName("SHARE");
                button.setOnAction(actionEvent -> followAlbum(idAlbum, button, fontAwesomeIconView));
            }
        }else{
            fontAwesomeIconView.setGlyphName("SHARE");
            button.setOnAction(actionEvent -> followAlbum(idAlbum, button, fontAwesomeIconView));
        }

        anchorPane.getChildren().add(titleLabel);
        anchorPane.getChildren().add(descriptionLabel);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(button);
        anchorPane.setCursor(Cursor.HAND);
        anchorPane.setOnMouseClicked(mouseEvent -> goToAlbum(idAlbum));
        return anchorPane;
    }

    private void followAlbum(String idAlbum, Button button, FontAwesomeIconView fontAwesomeIconView) {
        if (mainScreenController.login) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Save Album success");
            alert.show();
            MainScreenController.albumFollowed.add(idAlbum);
            button.setOnAction(actionEvent -> unFollowAlbum(idAlbum, button, fontAwesomeIconView));
            fontAwesomeIconView.setGlyphName("CHECK");
            new Thread(() -> {
                HttpResponse<String> stringHttpResponse = null;
                try {
                    stringHttpResponse = Unirest.put("http://localhost:8080/user/saveAlbum?idUser=" + MainScreenController.idUser + "&idAlbum=" + idAlbum).asString();
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(stringHttpResponse.getBody());
            }).start();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Please login to Save Album");
            alert.show();
            mainScreenController.loginPaneFromHome.setVisible(true);
        }
    }

    private void unFollowAlbum(String idAlbum, Button button, FontAwesomeIconView fontAwesomeIconView) {
        if (mainScreenController.login) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Remove Saved Album success");
            alert.show();

            MainScreenController.albumFollowed.remove(idAlbum);
            button.setOnAction(actionEvent -> followAlbum(idAlbum, button, fontAwesomeIconView));
            fontAwesomeIconView.setGlyphName("SHARE");
            new Thread(() -> {
                HttpResponse<String> stringHttpResponse = null;
                try {
                    stringHttpResponse = Unirest.put("http://localhost:8080/user/removeAlbum?idUser=" + MainScreenController.idUser + "&idAlbum=" + idAlbum).asString();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (mainScreenController.albumBtn.isSelected()){
                                mainScreenController.albumBtn.fire();
                            }
                        }
                    });
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(stringHttpResponse.getBody());
            }).start();
        } else {
            mainScreenController.loginPaneFromHome.setVisible(true);
        }
    }

    private void goToAlbum(String idAlbum) {
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/album/findAlbumById/" + idAlbum).asJson();

            FXMLLoader albumScreenFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/ComponentScreen.fxml"));
            AnchorPane anchorPane = albumScreenFxmlLoader.load();
            ComponentScreenController albumController = albumScreenFxmlLoader.getController();
            albumController.mainScreenController = mainScreenController;
            mainScreenController.mainBoard.setContent(anchorPane);
            mainScreenController.mainBoard.setVvalue(0);
            albumController.setImageBanner("http://localhost:8080/image/bannerAlbum.jpg");
            albumController.bannerTitle.setText("Album");
            albumController.setBanner(jsonNodeHttpResponse);
            mainScreenController.searchBarPane.setVisible(false);

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AnchorPane genreItem(String idGenre, String imgUrl, String name) {
        AnchorPane anchorPane = setAnchorPane(160, 190);
        ImageView imageView = setImageView(10, 10, imgUrl);
        Label titleLabel = createLabel(name);

        anchorPane.getChildren().add(titleLabel);
        anchorPane.getChildren().add(imageView);
        anchorPane.setOnMouseClicked(mouseEvent -> goToGenre(name, imgUrl, name));
        anchorPane.setCursor(Cursor.HAND);
        return anchorPane;
    }

    private void goToGenre(String idGenre, String img, String name) {
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/song/findSongByGenre/" + idGenre).asJson();
            ObjectMapper mapper = new ObjectMapper();
            List<Song> listSong = mapper.readValue(jsonNodeHttpResponse.getBody().toString(), new TypeReference<>() {
            });
            FXMLLoader genreFXML = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/ComponentScreen.fxml"));
            AnchorPane anchorPane = genreFXML.load();
            ComponentScreenController genreController = genreFXML.getController();
            genreController.mainScreenController = mainScreenController;

            mainScreenController.mainBoard.setVvalue(0);
            mainScreenController.mainBoard.setContent(anchorPane);
            genreController.setImageBanner("http://localhost:8080/image/bannerGenre.jpg");
            genreController.bannerTitle.setText("Genre");
            genreController.imageArtist.setImage(new Image(img));
            genreController.nameArtist.setText(name);
            genreController.setListSong(listSong);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  static void addDataToTable(VBox table, List<Song> songs){
        table.getChildren().clear();
        for (int i = 0; i < songs.size(); i++) {
            FXMLLoader cardFxmlLoader = new FXMLLoader(PublicController.class.getResource("/com/doan/client/View/Component/TopCard.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = cardFxmlLoader.load();
                TopCardController topCardController = cardFxmlLoader.getController();
                topCardController.mainScreenController = mainScreenController;
                topCardController.setCard(songs.get(i), i + 1);

                table.getChildren().add(anchorPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static   List<Song> getSongFromJson(HttpResponse<JsonNode> jsonNodeHttpResponse){
        JSONArray jsonArray = new JSONArray(jsonNodeHttpResponse.getBody().getObject().get("songs").toString());
        List<Song> songs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = jsonArray.getJSONObject(i);
            Song song = new Gson().fromJson(String.valueOf(jsonobject), Song.class);
            songs.add(song);
        }
        return songs;
    }
    public static MoreOptionCardController createMoreOptionCard(FXMLLoader fxmlLoader, AnchorPane layer, Song curSong){
        MoreOptionCardController moreOptionCardController = fxmlLoader.getController();
        moreOptionCardController.idSong = curSong.getId();
        moreOptionCardController.mainScreenController = mainScreenController;
        moreOptionCardController.layer = layer;
        moreOptionCardController.file= curSong.getFile();
        moreOptionCardController.songName= curSong.getName();
        moreOptionCardController.setThumpLikeBtn();
        moreOptionCardController.initMenu();
        return moreOptionCardController;
    }
}
