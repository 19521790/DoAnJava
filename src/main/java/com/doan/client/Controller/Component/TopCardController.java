package com.doan.client.Controller.Component;

import com.doan.client.Controller.PublicController;
import com.doan.client.Controller.UserScreen.MainScreenController;
import com.doan.client.Controller.UserScreen.PlayListScreenController;
import com.doan.client.Model.Artist;
import com.doan.client.Model.Object.ArtistOtd;
import com.doan.client.Model.Song;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TopCardController {
    public MainScreenController mainScreenController;
    public Label indexSong;
    public ImageView imageSong;
    public Label artistSong;
    public Label songName;
    public Label albumSong;
    public Label viewSong;
    public Label durationSong;
    public Button moreBtn;
    public int currentIndex;
    public PlayListScreenController playListScreenController;
    Song curSong;
    public  void setCard(Song song, int index){

        indexSong.setText(Integer.toString(index));
        imageSong.setImage(new Image(song.getAlbum().getImage()));
        String artistString="";
        for (int i = 0 ; i< song.getArtists().size(); i++){
            if (i== song.getArtists().size()-1){
                artistString += song.getArtists().get(i).getName();
            }else{
                artistString += song.getArtists().get(i).getName()+ ", ";
            }
        }
        artistSong.setText(artistString);
        songName.setText(song.getName());
        albumSong.setText(song.getAlbum().getName());
        viewSong.setText(Integer.toString(song.getTotalView()));
        durationSong.setText(PublicController.setTimePlay(song.getDuration()));
        currentIndex= index;
        curSong= song;
    }

    public void playSong(ActionEvent actionEvent) {
        mainScreenController.setMediaPlaying(curSong);
    }

    public AnchorPane createLayer(AnchorPane parentAnchorPane, AnchorPane card){
        AnchorPane layer = new AnchorPane();
        layer.setPrefWidth(parentAnchorPane.getPrefWidth());
        layer.setPrefHeight(parentAnchorPane.getPrefHeight());
        layer.getChildren().add(card);
        parentAnchorPane.getChildren().add(layer);
        layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                parentAnchorPane.getChildren().remove(layer);
                moreBtn.getStyleClass().remove("add_opacity");
            }
        });
        return layer;
    }
    public void showMoreCard(ActionEvent actionEvent) {
        if (mainScreenController.login){
            AnchorPane parentAnchorPane= mainScreenController.loveScreenController.parentAnchorPane;
            moreBtn.getStyleClass().add("add_opacity");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/MoreOptionCard.fxml"));
            try {
                AnchorPane card = fxmlLoader.load();
                card.setLayoutY(420 + (currentIndex-1)*44);
                card.setLayoutX(750);
                AnchorPane layer = createLayer(parentAnchorPane, card);
                MoreOptionCardController moreOptionCardController= PublicController.createMoreOptionCard(fxmlLoader, layer, curSong);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            mainScreenController.loginPaneFromHome.setVisible(true);
        }


    }

    public void showMoreCardPlaylist() {
        if (mainScreenController.login){
            AnchorPane parentAnchorPane= playListScreenController.playlistCover;
            moreBtn.getStyleClass().add("add_opacity");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/MoreOptionCard.fxml"));
            try {
                AnchorPane card = fxmlLoader.load();

                card.setLayoutY(530 + (currentIndex-1)*44);
                card.setLayoutX(750);

                AnchorPane layer = createLayer(parentAnchorPane, card);
                MoreOptionCardController moreOptionCardController= PublicController.createMoreOptionCard(fxmlLoader, layer, curSong);

                moreOptionCardController.removeBtn.setText("Remove");
                moreOptionCardController.playListScreenController= playListScreenController;
                moreOptionCardController.removeBtn.setOnAction(actionEvent ->
                {

                    playListScreenController.playlistTable.getChildren().remove(currentIndex -1);
                    parentAnchorPane.getChildren().remove(layer);
                    moreBtn.getStyleClass().remove("add_opacity");
                    ToggleButton toggleButton = (ToggleButton)mainScreenController.Group1.getSelectedToggle();
                    toggleButton.fire();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Successful remove");
                    alert.show();
                    try {
                        HttpResponse<String> httpResponse= Unirest.put("http://localhost:8080/playlist/removeSongFromPlaylist?idPlaylist="+playListScreenController.curId+"&idSong=" + curSong.getId()).asString();
                    } catch (UnirestException e) {
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            mainScreenController.loginPaneFromHome.setVisible(true);
        }

    }
}
