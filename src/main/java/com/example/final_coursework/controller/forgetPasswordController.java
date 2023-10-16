package com.example.final_coursework.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class forgetPasswordController {

    @FXML
    private AnchorPane paneForget;

    @FXML
    private ImageView imgBack;

    @FXML
    private Label inpOTP;

    @FXML
    private JFXTextField inpPassword;

    @FXML
    private JFXTextField inpConPassword;

    @FXML
    private JFXTextField inpPassword1;

    @FXML
    void checkOTP(ActionEvent event) {

    }

    @FXML
    void goBack(MouseEvent event) {
        Stage stage = (Stage)paneForget.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/selectUser.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void recive(ActionEvent event) {

    }

    @FXML
    void restPassword(ActionEvent event) {
        Stage stage = (Stage)paneForget.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/selectUser.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

