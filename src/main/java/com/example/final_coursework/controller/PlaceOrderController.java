package com.example.final_coursework.controller;

import com.example.final_coursework.db.DBConnection;
import com.example.final_coursework.entity.Customer;
import com.example.final_coursework.entity.Order;
import com.example.final_coursework.entity.OrderDetail;
import com.example.final_coursework.dto.tm.CartTm;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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



    ObservableList<CartTm> cartTMList = FXCollections.observableArrayList();

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartTm tm: cartTMList) {
            orderDetails.add( new OrderDetail(
                    tm.getItem_code(),
                    lblOrderId.getText(),
                    tm.getQty(),
                    Double.parseDouble(inpSell.getText()),
                    tm.getAmount(),
                    tm.getDiscount()
                    )
            );
        }
        Customer customer = new Customer(
                inpCusContact.getText(),
                inpCusEmail.getText(),
                inpCusName.getText()
        );

        Order order = new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cmbEmpId.getValue().toString(),
                customer.getContact(),
                Double.parseDouble(lblTotal.getText())
        );
        order.setArrears(Double.parseDouble(lblBalance.getText()));

        Connection connection = null;
        boolean isOrderPlaced = true;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?)");
            pstm.setString(1,customer.getContact());
            pstm.setString(2,customer.getEmail());
            pstm.setString(3,customer.getName());

            if(pstm.executeUpdate()>0){
                PreparedStatement orderPstm = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");
                orderPstm.setString(1,order.getOrdId());
                orderPstm.setString(2,order.getDate());
                orderPstm.setString(3,order.getEmpId());
                orderPstm.setString(4,order.getCustContact());
                orderPstm.setDouble(5,order.getTotal());
                orderPstm.setDouble(6,order.getArrears());

                if(orderPstm.executeUpdate()>0){
                    for (OrderDetail orderDetail: orderDetails ) {
                        PreparedStatement odPstm = connection.prepareStatement("INSERT INTO orderDetails VALUES(?,?,?,?,?,?)");
                        odPstm.setString(1,orderDetail.getItmCode());
                        odPstm.setString(2,orderDetail.getOrdId());
                        odPstm.setInt(3,orderDetail.getQty());
                        odPstm.setDouble(4,orderDetail.getUnitPrice());
                        odPstm.setDouble(5,orderDetail.getTotal());
                        odPstm.setDouble(6,orderDetail.getDiscount());

                        if(odPstm.executeUpdate()<=0){
                            isOrderPlaced= false;
                        }
                    }
                }else{
                    connection.rollback();
                    isOrderPlaced= false;
                    new Alert(Alert.AlertType.ERROR,"Somthing went Wrong").show();

                }
            }else{
                connection.rollback();
                isOrderPlaced= false;
                new Alert(Alert.AlertType.ERROR,"Somthing went Wrong").show();
            }

            if(isOrderPlaced){
                genId();
                new Alert(Alert.AlertType.INFORMATION,"Order Placed...!").show();
                cartTMList.clear();
                tblPlaceOrder.refresh();
                clearFields();
                connection.commit();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        double amount = Double.parseDouble(inpSell.getText())*Double.parseDouble(inpQtyOnHand.getText());
        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #3F1D38; -fx-text-fill: #fff");
        btn.setPrefSize(100,50);


        boolean isExsist = false;
        int qoh = Integer.parseInt(inpQtyOnHand.getText());
        int q = Integer.parseInt(lblQty.getText());
        for (CartTm tm: cartTMList) {
            if(tm.getItem_code().equals(cmbItemCode.getValue().toString())){

                if(qoh<=q && qoh<=(q-tm.getQty())){
                    tm.setQty(tm.getQty()+Integer.parseInt(inpQtyOnHand.getText()));
                    tm.setAmount(tm.getAmount()+Double.parseDouble(inpSell.getText())*Integer.parseInt(inpQtyOnHand.getText()));
                }else{
                    new Alert(Alert.AlertType.INFORMATION,"Insufficiant Quantity..!").show();
                }
                isExsist=true;
            }
        }

        if(!isExsist && qoh<=q){
            CartTm cartTm = new CartTm(
                    cmbItemCode.getValue().toString(),
                    inpDesc.getText(),
                    Integer.parseInt(inpQtyOnHand.getText()),
                    Double.parseDouble(inpSell.getText()),
                    lblDate.getText(),
                    Double.parseDouble(inpDiscount.getText()),
                    inpType.getText(),
                    inpSize.getText(),
                    amount,
                    btn
            );

            btn.setOnAction(actionEvent1 -> {
                cartTMList.remove(cartTm);
                lblTotal.setText(String.format("%.2f",findTotal()));
            });
            cartTMList.add(cartTm);

            TreeItem<CartTm> treeItem = new RecursiveTreeItem<>(cartTMList, RecursiveTreeObject::getChildren);
            tblPlaceOrder.setRoot(treeItem);
            tblPlaceOrder.setShowRoot(false);
        }
        lblTotal.setText(String.format("%.2f",findTotal()));
        tblPlaceOrder.refresh();
    }

    private double findTotal(){
        double total=0;
        for (CartTm tm : cartTMList) {
            total+=tm.getAmount();
        }
        return total;
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields(){
        cmbEmpId.setValue(" ");
        cmbItemCode.setValue(" ");
        inpDesc.clear();
        lblQty.setText("0");
        inpSell.clear();
        inpSize.clear();
        inpType.clear();
        inpQtyOnHand.clear();
        inpDiscount.clear();
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

        lblDate.setText(LocalDate.now().toString());

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
            PreparedStatement pstm = connection.prepareStatement("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();

            if(rst.next()){
                int num = Integer.parseInt(rst.getString(1).split("_")[1]);
                num++;
                lblOrderId.setText(String.format("ord_%04d",num));
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
            PreparedStatement pstm = connection.prepareStatement("SELECT user_id FROM user ORDER BY user_id ASC");
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
