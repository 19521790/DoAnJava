package com.doan.client.Controller.Screen;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
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
    public MaterialIconView navigateBtn;
    public AnchorPane banner11;
    public AnchorPane banner21;
    public ChoiceBox<String> choiceBoxSongs;
    public VBox billBoardTable;
    private SingleSelectionModel<Tab> selectionModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectionModel = tabPaneTop.getSelectionModel();
        Rectangle rectangle = new Rectangle(clipBanner.getPrefWidth(), clipBanner.getPrefHeight());
        clipBanner.setClip(rectangle);
        String[] stringList= {"Title", "View", "Album"};
        choiceBoxSongs.getItems().addAll(stringList);
        choiceBoxSongs.setValue("Title");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/TopCard.fxml"));
        try {
            AnchorPane anchorPane= fxmlLoader.load();
            billBoardTable.getChildren().add(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        
    }

    public void setTab(String buttonPane) {
        if (buttonPane.equals("offerBtn")) {
            selectionModel.select(tabOffer);
        } else if (buttonPane.equals("categoryBtn")) {
            selectionModel.select(tabCategory);
        } else {
            selectionModel.select(tabBillboard);
        }
    }

    public void navigateNextBanner(MouseEvent mouseEvent) {
        if (banner1.isVisible()){
            banner1.setVisible(false);
            banner2.setVisible(false);
        }else {
            banner1.setVisible(true);
            banner2.setVisible(true);
        }
    }

}
