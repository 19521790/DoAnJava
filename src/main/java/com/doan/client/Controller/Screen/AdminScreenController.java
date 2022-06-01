package com.doan.client.Controller.Screen;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {


    public ToggleButton addMusicTabBtn;
    public ToggleGroup Group1;
    public AnchorPane mainBoard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addMusicTabBtn.fire();
    }
    public void addMusicTab(ActionEvent actionEvent) {

        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource();
        if (!toggleButton.isSelected()) {
            toggleButton.fire();
        }
        List<Toggle> toggleButtonList = toggleButton.getToggleGroup().getToggles();

        for (int i = 0; i < toggleButtonList.size(); i++) {
            ToggleButton indexToggleButton = (ToggleButton) toggleButtonList.get(i);
            if (indexToggleButton.isSelected()) {
                indexToggleButton.setStyle("-fx-background-color: #3b75ff; -fx-text-fill: white");

                FontAwesomeIconView fontAwesomeIconView = (FontAwesomeIconView) indexToggleButton.getGraphic();
                fontAwesomeIconView.setFill(Paint.valueOf("white"));
//
//                pushScreen(indexToggleButton);
            } else {
                indexToggleButton.setStyle("-fx-background-color: white; -fx-text-fill: black");

                FontAwesomeIconView fontAwesomeIconView = (FontAwesomeIconView) indexToggleButton.getGraphic();
                fontAwesomeIconView.setFill(Paint.valueOf("black"));

            }
        }

    }

    public void pushScreen(ToggleButton toggleButton) {
        if (toggleButton.getId().equals("homeBtn")) {

        }
    }


}
