package com.example.final_coursework.controller;

import com.example.final_coursework.db.DBConnection;
import com.example.final_coursework.model.tm.CartTm;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {

    @FXML
    private JFXTextField inpCusName;

    @FXML
    private JFXTextField inpCusContact;

    @FXML
    private JFXTextField inpCusEmail;

    @FXML
    private JFXTextField inpDesc;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblQty;

    @FXML
    private JFXTextField inpQtyOnHand;

    @FXML
    private JFXTextField inpSell;

    @FXML
    private JFXTextField inpType;

    @FXML
    private JFXTextField inpSize;

    @FXML
    private JFXTextField inpDiscount;

    @FXML
    private JFXTextField inpSearch;

    @FXML
    private Text lblTotal;

    @FXML
    private Text lblDiscount;

    @FXML
    private Text lblBalance;

    @FXML
    private Text lblAmount;

    @FXML
    private JFXTreeTableView<CartTm> tblPlaceOrder;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colDesc;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private TreeTableColumn colDate;

    @FXML
    private TreeTableColumn colDiscount;

    @FXML
    private TreeTableColumn colType;

    @FXML
    private TreeTableColumn colSize;

    @FXML
    private TreeTableColumn colAmount;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private Text lblOrderId;

    @FXML
    private JFXComboBox cmbEmpId;

    @FXML
    private JFXComboBox cmbItemCode;

    @FXML
    private JFXDatePicker inpDate;

    @FXML
    private RadioButton rbtnCash;

    @FXML
    private ToggleGroup payment;

    @FXML
    private RadioButton rbtnCard;

    public void placeOrderOnAction(ActionEvent actionEvent) {
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
        cmbEmpId.setValue(" ");
        cmbItemCode.setValue(" ");
        inpDesc.clear();
        lblQty.setText("0");
        inpSell.clear();
        inpSize.clear();
        inpType.clear();
        inpCusContact.clear();
        inpCusEmail.clear();
        inpCusName.clear();
        inpSearch.clear();
    }

    public void removeOrderOnAction(ActionEvent actionEvent) {
    }

    public void searchOnAction(ActionEvent actionEvent) {
        
    }

    public void cashOnAction(ActionEvent actionEvent) {
    }

    public void cardOnAction(ActionEvent actionEvent) {
    }

    public void itemCodeComboAction(ActionEvent actionEvent) {
    }

    public void empIDComboAction(ActionEvent actionEvent) {
    }

    public void itemSizeComboAction(ActionEvent actionEvent) {

    }

    public void supNameComboAction(ActionEvent actionEvent) {

    }

    public void supIDComboAction(ActionEvent actionEvent) {

    }

    public void addToStockOnAction(ActionEvent actionEvent) {

    }

    public void printOnAction(ActionEvent actionEvent) {

    }

    public void itemTypeComboAction(ActionEvent actionEvent) {

    }

    public void refreshOnAction(ActionEvent actionEvent) {
    }

    public void pay(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("item_code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colDiscount.setCellValueFactory(new TreeItemPropertyValueFactory<>("discount"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnDelete"));

        loadUsers();
        loadItems();
        genId();

        cmbItemCode.setOnAction(actionEvent -> {
            loadItemInfo();
        });
    }

    private void loadItemInfo() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM ITEM WHERE item_code=?");
            pstm.setString(1,cmbItemCode.getValue().toString());
            ResultSet rst = pstm.executeQuery();
            if(rst.next()){
                inpDesc.setText(rst.getString(3));
                lblQty.setText(rst.getString(4));
                inpSell.setText(rst.getString(6));
                inpType.setText(rst.getString(7));
                inpSize.setText(rst.getString(8));
            }


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void genId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT order_id FROM orders");
            ResultSet rst = pstm.executeQuery();

            if(rst.next()){
                int num = Integer.parseInt(rst.getString(1).split("_")[1]);
                num++;
                lblOrderId.setText(String.format("sup_%04d",num));
            }else{
                lblOrderId.setText("ord_0001");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItems() {
        ObservableList<Object> items = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT item_code FROM item");
            ResultSet rst = pstm.executeQuery();
            while(rst.next()){
                items.add(rst.getString(1));
            }

            cmbItemCode.setItems(items);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUsers() {
        ObservableList<Object> users = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT user_id FROM user");
            ResultSet rst = pstm.executeQuery();
            while(rst.next()){
                users.add(rst.getString(1));
            }

            cmbEmpId.setItems(users);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void qtyOnHandAction(ActionEvent actionEvent) {

    }
}
