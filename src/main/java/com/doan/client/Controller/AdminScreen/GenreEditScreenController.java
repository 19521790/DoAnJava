package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Genre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.ResourceBundle;

public class GenreEditScreenController extends PublicAdminMethodController implements Initializable {
    public VBox listNotAddedSong;
    public HBox listAddedSong;
    public TextField addSongField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void resetAllField() {
        addGenreField.setText("");
        imageDisplay.setImage(null);
    }

    @Override
    public void showSearchFormSong(MouseEvent mouseEvent) {

    }

    @Override
    public void addAction(ActionEvent actionEvent) {
        if (addGenreField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Genre name can't be empty");
            alert.show();

        } else if (imageDisplay.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please choose Image file");
            alert.show();
        } else {
            addBtn.setText("Adding... Please wait");
            ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            addBtn.setGraphic(imageView);
            new Thread(() -> {
                Genre genre = new Genre();
                genre.setName(addGenreField.getText());
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = null;
                try {
                    json = ow.writeValueAsString(genre);

                    HttpResponse<JsonNode> apiResponse = Unirest.post("http://localhost:8080/genre/addGenre").field("genre", json).field("image", currentFileImage).asJson();

                    if (apiResponse.getStatus() == 200) {
                        AdminScreenController.getGenreData();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                initArtist();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Successful add");
                                alert.show();
                                addBtn.setText("Add Genre");
                                addBtn.setGraphic(null);
                                resetAllField();
                            }
                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Can't add Genre");
                                alert.show();
                                addBtn.setText("Add Artist");
                                addBtn.setGraphic(null);
                                resetAllField();
                            }
                        });
                    }


                } catch (JsonProcessingException | UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();


        }
    }
    @Override
    public void closeSearchForm(MouseEvent mouseEvent) {

    }
}
