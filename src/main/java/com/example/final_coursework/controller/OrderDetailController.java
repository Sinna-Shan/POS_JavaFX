package com.example.final_coursework.controller;

import com.example.final_coursework.db.DBConnection;
import com.example.final_coursework.model.tm.OrderDetailTM;
import com.example.final_coursework.model.tm.OrderItemTM;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderDetailController implements Initializable {

    @FXML
    private JFXTreeTableView<OrderDetailTM> tblOrderDetails;

    @FXML
    private TreeTableColumn colOrderId;

    @FXML
    private TreeTableColumn colDate;

    @FXML
    private TreeTableColumn colTotal;

    @FXML
    private TreeTableColumn colCustName;

    @FXML
    private TreeTableColumn colContact;

    @FXML
    private TreeTableColumn colEmail;

    @FXML
    private TreeTableColumn colEmployee;

    @FXML
    private TreeTableColumn colarrears;

    @FXML
    private JFXTreeTableView<OrderItemTM> tblOrderItem;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colDesc;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private TreeTableColumn date;

    @FXML
    private TreeTableColumn colDiscount;

    @FXML
    private TreeTableColumn colType;

    @FXML
    private TreeTableColumn colSize;

    @FXML
    private TreeTableColumn colAmount;


    public void refreshOnAction(ActionEvent actionEvent) {
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new TreeItemPropertyValueFactory<>("ord_id"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new TreeItemPropertyValueFactory<>("total"));
        colCustName.setCellValueFactory(new TreeItemPropertyValueFactory<>("cus_name"));
        colContact.setCellValueFactory(new TreeItemPropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colEmployee.setCellValueFactory(new TreeItemPropertyValueFactory<>("emp"));
        colarrears.setCellValueFactory(new TreeItemPropertyValueFactory<>("arrears"));

        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("item_code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("u_price"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colDiscount.setCellValueFactory(new TreeItemPropertyValueFactory<>("discount"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));

        loadOrderDetails();

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                loadOrderItems(newValue);
            }
        });
    }

    private void loadOrderItems(TreeItem<OrderDetailTM> newValue) {
        ObservableList<OrderItemTM> orderItemTMS = FXCollections.observableArrayList();
        String sql = "SELECT orderdetails.item_code, item.description, orderdetails.qty, orderdetails.unit_price, orders.date, orderdetails.discount, item.type, item.size, orderdetails.total FROM orderdetails INNER JOIN item ON  orderdetails.item_code = item.item_code INNER JOIN orders ON orderdetails.order_id = orders.order_id WHERE orders.order_id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,newValue.getValue().getOrd_id());
            ResultSet rst = pstm.executeQuery();
            while(rst.next()){
                orderItemTMS.add(new OrderItemTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3),
                        rst.getDouble(4),
                        rst.getString(5),
                        rst.getDouble(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getDouble(9)
                ));
            }

            TreeItem<OrderItemTM> treeItem = new RecursiveTreeItem<>(orderItemTMS, RecursiveTreeObject::getChildren);
            tblOrderItem.setRoot(treeItem);
            tblOrderItem.setShowRoot(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderDetails() {
        ObservableList<OrderDetailTM> orderDetailTMS = FXCollections.observableArrayList();
        String sql = "SELECT orders.order_id, orders.date, orders.total, customer.name, customer.contact, customer.email, user.name, orders.arrears   FROM orders INNER JOIN customer ON orders.cust_contact = customer.contact INNER JOIN user ON orders.user_id = user.user_id";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rst = pstm.executeQuery();
            while(rst.next()){
                orderDetailTMS.add(new OrderDetailTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getDouble(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getDouble(8)
                ));
            }

            TreeItem<OrderDetailTM> treeItem = new RecursiveTreeItem<>(orderDetailTMS, RecursiveTreeObject::getChildren);
            tblOrderDetails.setRoot(treeItem);
            tblOrderDetails.setShowRoot(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
