package com.example.final_coursework.controller;

import com.example.final_coursework.db.DBConnection;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoginController {

    @FXML
    private AnchorPane paneUserLogin;

    @FXML
    private JFXTextField inpUserName;

    @FXML
    private JFXPasswordField inpPassword;

    @FXML
    private ImageView imgShow;

    @FXML
    private Label lblForgetPassword;

    @FXML
    private ImageView imgBack;

    @FXML
    void forgetPassword(ActionEvent event) {
        Stage stage = (Stage)paneUserLogin.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/forgetPassword.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goBack(MouseEvent event) {
        Stage stage = (Stage)paneUserLogin.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/selectUser.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loginOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE user_name = ? AND password= ?");
        pstm.setString(1,inpUserName.getText());
        pstm.setString(2,inpPassword.getText());

        ResultSet rst = pstm.executeQuery();
        if(rst.next() && rst.getString(6).equals("admin")){
            Stage stage = (Stage)paneUserLogin.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/landingPage.fxml"))));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Inavild User name or Password..!").show();
        }
    }

    @FXML
    void showPassword(MouseEvent event) {

    }

    @FXML
    void singUp(ActionEvent event) {
        Stage stage = (Stage)paneUserLogin.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/signUp.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
