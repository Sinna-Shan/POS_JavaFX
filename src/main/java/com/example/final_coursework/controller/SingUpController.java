package com.example.final_coursework.controller;

import com.example.final_coursework.db.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SingUpController implements Initializable {

    @FXML
    private AnchorPane paneUserSignup;

    @FXML
    private JFXTextField inpAdminUserName;

    @FXML
    private JFXTextField inpAdminPassword;

    @FXML
    private ImageView imgBack;

    @FXML
    private Label lblEmpId;

    @FXML
    private JFXTextField inpOtp;

    @FXML
    private JFXTextField inpName;

    @FXML
    private JFXTextField inpUserName;

    @FXML
    private JFXTextField inpEmail;

    @FXML
    private JFXPasswordField inpPassword;

    @FXML
    private JFXPasswordField inpConPassword;

    @FXML
    private JFXComboBox<String> cmbUserType;


    @FXML
    private JFXButton btnCreateAccount;

    @FXML
    private JFXButton btnCheck;

    @FXML
    private JFXButton btnRecive;

    @FXML
    void check(ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE user_name = ? AND password=?");
        pstm.setString(1,inpAdminUserName.getText());
        pstm.setString(2,inpAdminPassword.getText());

        ResultSet rst = pstm.executeQuery();
        if(rst.next() && rst.getString(6).equals("admin")){
            inpName.setDisable(false);
            cmbUserType.setDisable(false);
            inpEmail.setDisable(false);
            inpPassword.setDisable(false);
            inpUserName.setDisable(false);
            inpConPassword.setDisable(false);
            btnRecive.setDisable(false);
            btnCreateAccount.setDisable(false);
        }else{
            new Alert(Alert.AlertType.ERROR,"Inavild User name or Password..!").show();
        }
    }

    @FXML
    void createAccount(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(inpPassword.getText().equals(inpConPassword.getText())){
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
            pstm.setString(1,lblEmpId.getText());
            pstm.setString(2,inpName.getText());
            pstm.setString(3,inpEmail.getText());
            pstm.setString(4,inpUserName.getText());
            pstm.setString(5,inpPassword.getText());
            pstm.setString(6,cmbUserType.getValue().toString());

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"User Added Success..!").show();
                try {
                    Stage stage = (Stage)paneUserSignup.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/landingPage.fxml"))));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }else{
            new Alert(Alert.AlertType.ERROR,"Passwords doesn't match !").show();
        }

    }

    @FXML
    void goBack(MouseEvent event) {
        Stage stage = (Stage)paneUserSignup.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminLogin.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void recive(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genId();
        inpOtp.setDisable(true);
        inpName.setDisable(true);
        cmbUserType.setDisable(true);
        inpEmail.setDisable(true);
        inpPassword.setDisable(true);
        inpUserName.setDisable(true);
        inpConPassword.setDisable(true);
        btnRecive.setDisable(true);
        btnCreateAccount.setDisable(true);
        loadType();
    }

    private void genId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();

            if(rst.next()){
                int num = Integer.parseInt(rst.getString(1).split("_")[1]);
                num++;
                lblEmpId.setText(String.format("sup_%04d",num));

            }else{
                lblEmpId.setText("emp_0001");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadType() {
        ObservableList<String> types = FXCollections.observableArrayList();
        types.addAll("admin","user");
        cmbUserType.setItems(types);
    }
}
