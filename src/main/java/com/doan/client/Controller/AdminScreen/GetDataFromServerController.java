package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Album;
import com.doan.client.Model.Artist;
import com.doan.client.Model.Genre;
import com.doan.client.Model.Song;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class GetDataFromServerController {
    public VBox listNotAddedAlbum;
    public VBox listNotAddedArtist;
    public HBox listAddedAlbum;
    public HBox listAddedArtist;
    public VBox listNotAddedGenre;
    public HBox listAddedGenre;
    public VBox listNotAddedSong;
    public HBox listAddedSong;
    public List<Artist> artists;
    public List<Genre> genres;
    public List<Album> albums;
    public TextField addGenreField;
    public TextField addAlbumField;
    public TextField addSongArtistField;
    public TextField addSongField;
    public TextField addArtistField;

    public ImageView imageDisplay;
    public File currentFileImage;
    public ToggleButton addModeBtn;
    public ToggleButton editModeBtn;
    public Button addBtn;
    public Button editBtn;

    public Button deleteBtn;
    public ToggleGroup Group1;
    public ScrollPane scrollPaneSearchArtist;
    public ScrollPane scrollPaneSearchGenre;
    public ScrollPane scrollPaneSearchAlbum;
    public ScrollPane scrollPaneSongName;



    public void initGenre() {

        addGenreToSearchFilter(AdminScreenController.listGenre);

    }

    public void initArtist() {
        addArtistToSearchFilter(AdminScreenController.listArtist);
    }

    public void initAlbum() {
        addAlbumToSelectForm(AdminScreenController.listAlbum);

    }


    public Button createNewBtnItem(String name, String id) {
        Button button = new Button();
        button.setText(name);
        button.setId(id);
        button.getStyleClass().add("itemSearch");
        button.setPrefWidth(280);
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setCursor(Cursor.HAND);
        return button;
    }

    public void initSong() {
        addSongToSearchFilter(AdminScreenController.listSong);

    }
    public void addAlbumToSelectForm(List<Album> albums) {
        for (Album album : albums) {
            Button button = createNewBtnItem(album.getName(), album.getId());
            button.setOnAction(this::clickItemSearchAlbum);
            listNotAddedAlbum.getChildren().add(button);
        }
    }

    public void addArtistToSearchFilter(List<Artist> artists) {
        for (Artist artist : artists) {
            Button button = createNewBtnItem(artist.getName(), artist.getId());
            button.setOnAction(this::clickItemSearchArtist);
            listNotAddedArtist.getChildren().add(button);
        }
    }

    public void addGenreToSearchFilter(List<Genre> genres) {
        for (Genre genre : genres) {
            Button button = createNewBtnItem(genre.getName(), genre.getId());
            button.setOnAction(this::clickItemSearchGenre);
            listNotAddedGenre.getChildren().add(button);
        }
    }
    public void addSongToSearchFilter(List<Song> songs) {
        for (Song song : songs) {
            Button button = createNewBtnItem(song.getName(), song.getId());
            button.setOnAction(this::clickItemSearchSong);
            listNotAddedSong.getChildren().add(button);
        }
    }

    public Button convertButton(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(Region.USE_COMPUTED_SIZE);
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        fontAwesomeIconView.setGlyphName("REMOVE");
        button.setGraphic(fontAwesomeIconView);
        button.setContentDisplay(ContentDisplay.RIGHT);
        return button;
    }

    public void clickItemSearchGenre(ActionEvent actionEvent) {
        Button button = convertButton(actionEvent);
        listAddedGenre.getChildren().add(button);
        listNotAddedGenre.getChildren().remove(button);
        button.setOnAction(this::returnSongToListGenre);
    }


    public void clickItemSearchArtist(ActionEvent actionEvent) {
        Button button = convertButton(actionEvent);
        listAddedArtist.getChildren().add(button);
        listNotAddedArtist.getChildren().remove(button);
        button.setOnAction(this::returnSongToListArtist);
    }
    public void clickItemSearchAlbum(ActionEvent actionEvent) {
        if (listAddedAlbum.getChildren().size() == 0) {
            Button button = convertButton(actionEvent);
            listAddedAlbum.getChildren().setAll(button);
            listNotAddedAlbum.getChildren().remove(button);
            button.setOnAction(this::returnSongToListAlbum);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("A song just belongs to one Album, Please remove album first!!!");
            alert.show();
        }
    }

    public void clickItemSearchSong(ActionEvent actionEvent) {

        Button button = convertButton(actionEvent);
        listAddedSong.getChildren().add(button);
        listNotAddedSong.getChildren().remove(button);
        button.setOnAction(this::returnSongToListSong);
    }

    public Button returnButton(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(280);
        button.setGraphic(null);
        return button;
    }

    public void returnSongToListAlbum(ActionEvent actionEvent) {
        Button button = returnButton(actionEvent);
        listNotAddedAlbum.getChildren().add(button);
        listAddedAlbum.getChildren().remove(button);
        button.setOnAction(this::clickItemSearchAlbum);
    }

    public void returnSongToListArtist(ActionEvent actionEvent) {
        Button button = returnButton(actionEvent);
        listNotAddedArtist.getChildren().add(button);
        listAddedArtist.getChildren().remove(button);
        button.setOnAction(this::clickItemSearchArtist);
    }

    public void returnSongToListGenre(ActionEvent actionEvent) {
        Button button = returnButton(actionEvent);
        listNotAddedGenre.getChildren().add(button);
        listAddedGenre.getChildren().remove(button);
        button.setOnAction(this::clickItemSearchGenre);
    }

    public void returnSongToListSong(ActionEvent actionEvent) {
        Button button = returnButton(actionEvent);
        listNotAddedSong.getChildren().add(button);
        listAddedSong.getChildren().remove(button);
        button.setOnAction(this::clickItemSearchAlbum);
    }

    public void filterArtist(KeyEvent keyEvent) {
        List<Artist> listFilter = new ArrayList<>(AdminScreenController.listArtist.stream().filter(p -> p.getName().startsWith(addSongArtistField.getText())).toList());
        listNotAddedArtist.getChildren().clear();
        for (int i = listFilter.size()-1; i >-1; i--){
            int finalI = i;
            listAddedArtist.getChildren().forEach(e->{
                if (e.getId().equals(listFilter.get(finalI).getId())){
                    listFilter.remove(finalI);
                }
            });
        }

        addArtistToSearchFilter(listFilter);
    }

    public void filterGenre(KeyEvent keyEvent) {
        List<Genre> listFilter = new ArrayList<>(AdminScreenController.listGenre.stream().filter(p -> p.getName().startsWith(addGenreField.getText())).toList());
        listNotAddedGenre.getChildren().clear();
        for (int i = listFilter.size()-1; i >-1; i--){
            int finalI = i;
            listAddedGenre.getChildren().forEach(e->{
                if (e.getId().equals(listFilter.get(finalI).getId())){
                    listFilter.remove(finalI);
                }
            });
        }
        addGenreToSearchFilter(listFilter);
    }

    public void filterAlbum(KeyEvent keyEvent) {

        List<Album> listFilter = new ArrayList<>(AdminScreenController.listAlbum.stream().filter(p -> p.getName().startsWith(addAlbumField.getText())).toList());
        listNotAddedAlbum.getChildren().clear();
        for (int i = listFilter.size()-1; i >-1; i--){
            int finalI = i;
            listAddedAlbum.getChildren().forEach(e->{
                if (e.getId().equals(listFilter.get(finalI).getId())){
                    listFilter.remove(finalI);
                }
            });
        }

        addAlbumToSelectForm(listFilter);
    }

    public void filterSong(KeyEvent keyEvent) {

        List<Song> listFilter = new ArrayList<>(AdminScreenController.listSong.stream().filter(p -> p.getName().startsWith(addSongField.getText())).toList());
        listNotAddedSong.getChildren().clear();
        for (int i = listFilter.size()-1; i >-1; i--){
            int finalI = i;
            listAddedSong.getChildren().forEach(e->{
                if (e.getId().equals(listFilter.get(finalI).getId())){
                    listFilter.remove(finalI);
                }
            });
        }
        addSongToSearchFilter(listFilter);
    }

    public abstract void resetAllField();

    public void showSearchFormSong(MouseEvent mouseEvent){}

    public abstract void addAction(ActionEvent actionEvent);

    public abstract void closeSearchForm(MouseEvent mouseEvent) ;

    public void convertMode(ActionEvent actionEvent) {

        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource();
        if (toggleButton.getId().equals("addModeBtn")) {
            addModeBtn.setStyle("-fx-background-color:  #3b75ff; -fx-text-fill: white");
            editModeBtn.setStyle("-fx-background-color: #d3dadb; -fx-text-fill: black");
            addBtn.setVisible(true);
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);
            setSearchField(false);
            resetAllField();
        } else {
            deleteBtn.setVisible(true);
            editModeBtn.setStyle("-fx-background-color:  #3b75ff; -fx-text-fill: white");
            addModeBtn.setStyle("-fx-background-color: #d3dadb; -fx-text-fill: black");
            addBtn.setVisible(false);
            editBtn.setVisible(true);
            setSearchField(true);
            resetAllField();
        }
    }

    public void setSearchField(Boolean isSet) {
        if (isSet) {
            addSongField.setPromptText("Search for song name");
            addSongField.setOnKeyTyped(this::filterSong);
            addSongField.setOnMouseClicked(this::showSearchFormSong);
        } else {
            addSongField.setPromptText("");
            addSongField.setOnKeyTyped(null);
            addSongField.setOnMouseClicked(null);
        }
    }
    public void getFileImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        currentFileImage = fc.showOpenDialog(null);
        if (currentFileImage != null) {
            imageDisplay.setImage(new Image(currentFileImage.getAbsolutePath()));
        }
    }
    public List<String> getAddedSong() {
        List<String> songIdList = new ArrayList<>();
        listAddedSong.getChildren().forEach(e -> {
            Button button = (Button) e;
            songIdList.add(button.getId());
        });
        return songIdList;
    }
}
