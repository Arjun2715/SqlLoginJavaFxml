package com.example.loginsql;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField tf_su_username;

    @FXML
    private PasswordField tf_su_password;

    @FXML
    private Button btn_signup;
    @FXML
    private Button btn_aau_Login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_su_username.getText().trim().isEmpty() && !tf_su_password.getText().trim().isEmpty()) {
                    DBUtils.signUpUser(event, tf_su_username.getText(), tf_su_password.getText());
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all the information to sign up!");
                    alert.show();

                }
            }
        });

        btn_aau_Login.setOnAction(new EventHandler<ActionEvent>() {
                                      @Override
                                      public void handle(ActionEvent event) {
                                          DBUtils.changeScene(event, "sample.fxml", "Log in!", null);
                                      }
                                  }
        );
    }
}
