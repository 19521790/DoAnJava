package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.Component.DownloadCardController;
import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Song;
import com.sun.mail.auth.MD4;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DownloadScreenController implements Initializable {
    public Label listSongName;
    public Label songCount;
    public VBox downloadVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File directory= new File("src/main/resources/com/doan/client/Music/");
        File[] files= directory.listFiles();

        ArrayList<File> songs= new ArrayList<>();
        if (files != null){
            songs.addAll(Arrays.asList(files));
        }
        String arrayName= "";
        for (int i =0 ; i < songs.size(); i++){
            File file =  songs.get(i);
            arrayName+= file.getName().replace(".mp3","")+" • ";
            FXMLLoader downloadFXML=new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/DownloadCard.fxml"));
            try {
                AnchorPane anchorPane= downloadFXML.load();

                DownloadCardController downloadCardController= downloadFXML.getController();
                downloadCardController.indexSong.setText(Integer.toString(i));
                downloadCardController.labelSong.setText(file.getName().replace(".mp3",""));
                Media media= new Media(file.toURI().toString());
                MediaPlayer mediaPlayer =new MediaPlayer(media);
                mediaPlayer.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        downloadCardController.durationSong.setText(PublicController.setTimePlay(media.getDuration().toSeconds()));
                        downloadVbox.getChildren().add(anchorPane);
                    }
                });


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        listSongName.setText(arrayName);
        songCount.setText(songs.size() +" songs");

    }
}
