package com.doan.client.Controller;

import com.doan.client.Model.User;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountSettingController implements Initializable {

    public TextField nameInf;
    public TextField emailInf;
    public TextField fullnameInf;
    public ImageView imageInf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//
    }
    public void setUser(User user){
        nameInf.setText(user.getName());
        emailInf.setText(user.getEmail());
        fullnameInf.setText(user.getDisplayName());
        imageInf.setImage(new Image(user.getImage()));
    }
}
