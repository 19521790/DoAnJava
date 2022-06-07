package com.doan.client.Controller.UserScreen;

import com.doan.client.Model.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountSettingScreenController implements Initializable {

    public TextField nameInf;
    public TextField emailInf;
    public TextField fullnameInf;
    public ImageView imageInf;
    public AnchorPane changeNewPasswordForm;
    public PasswordField oldPassword;
    public PasswordField newPassword;
    public PasswordField confirmPassword;
    public Button changeNewPasswordBtn;
    public User cur_user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeNewPasswordForm.setVisible(false);
//
    }

    public void setUser(User user) {
        nameInf.setText(user.getName());
        emailInf.setText(user.getEmail());
        fullnameInf.setText(user.getDisplayName());
        imageInf.setImage(new Image(user.getImage()));
        cur_user = user;
    }

    public void changeNewPassword(ActionEvent actionEvent) {

        if (changeNewPasswordBtn.getText().equals("Save")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if (oldPassword.getText().equals(cur_user.getPassword())) {
                if (newPassword.getText().length() < 4) {
                    alert.setHeaderText("Your new password must have more than 3 characters");
                    alert.show();
                } else if (!newPassword.getText().equals(confirmPassword.getText())) {
                    alert.setHeaderText("Your new password and confirm password are not match");
                    alert.show();
                } else {
                    cur_user.setPassword(newPassword.getText());
                    ImageView view = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
                    view.setFitHeight(30);
                    view.setFitWidth(30);
                    changeNewPasswordBtn.setGraphic(view);
                    changeNewPasswordBtn.setText("");
                    new Thread(() -> {
                        try {
                            HttpResponse<JsonNode> apiResponse = Unirest.post("http://localhost:8080/user/updatePassword").header("Content-Type", "application/json")
                                    .body(cur_user)
                                    .asJson();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Change password success");
                                    alert.show();
                                    changeNewPasswordBtn.setText("Change Password");
                                    changeNewPasswordBtn.setLayoutY(400);
                                    changeNewPasswordBtn.setGraphic(null);
                                }

                            });
                            changeNewPasswordForm.setVisible(false);



                        } catch (UnirestException e) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Oops, There is something wrong!");
                                    alert.show();
                                }
                            });
                            throw new RuntimeException(e);

                        }
                    }).start();

                }
            } else {
                alert.setHeaderText("You password is not correct");
                alert.show();
            }
        } else {
            changeNewPasswordForm.setVisible(true);


            changeNewPasswordBtn.setLayoutY(450);
            changeNewPasswordBtn.setText("Save");
        }

    }
}
