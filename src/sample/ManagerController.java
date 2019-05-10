package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BookSearchResult;
import model.PublisherOrder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerController {

    @FXML
    private Button addBookBTN;
    @FXML
    private TextField addAUTHNAME;

    @FXML
    private Pane author2Pane;
    @FXML
    private Button modifyBookBTN;

    @FXML
    private Button placeOrderBTN;

    @FXML
    private Button confirmOrderBTN;

    @FXML
    private Button promoteBTN;

    @FXML
    private Button viewTotSales;

    @FXML
    private Button viewTop5Customers;

    @FXML
    private Button viewTop10SellingBooks;

    @FXML
    private Pane addBookpane1;

    @FXML
    private TextField addbooktitle;

    @FXML
    private TextField pubyear;

    @FXML
    private TextField addbookpubname;

    @FXML
    private TextField addbookprice;

    @FXML
    private ComboBox<String> addbookcat;

    @FXML
    private TextField addbookquant;

    @FXML
    private TextField addbookthres;

    @FXML
    private TextField addBookISBN;

    @FXML
    private Button addbookcnfrmBTN;

    @FXML
    private Pane addBookpane2;

    @FXML
    private Button addAuthorBTN;

    @FXML
    private Button addAuthorDone;

    @FXML
    private TextField addAuthorTxt;

    @FXML
    private Label addAuthorLabelISBN;

    @FXML
    private Pane modifyPane;

    @FXML
    private TextField modifyISBN;

    @FXML
    private TextField modifyQuantity;

    @FXML
    private Button modifyBTN2;

    @FXML
    private Label modifyErrorLbl;

    @FXML
    private Pane placeOrderpane;

    @FXML
    private TextField orderPubName;

    @FXML
    private TextField orderISBN;

    @FXML
    private TextField orderQuan;

    @FXML
    private Button placeOrderBTN2;

    @FXML
    private TextField cOrderPubN;

    @FXML
    private TextField cOrderISBN;

    @FXML
    private Button cOrderConf;

    @FXML
    private Pane promote;

    @FXML
    private Pane confirmPane;

    @FXML
    private TextField username;

    @FXML
    private ScrollPane analysis;

    @FXML
    private Pane addpubPane;
    @FXML
    private Pane addpublisherPane1;

    @FXML
    private TextField addAuthName;

    @FXML
    private TextField addAddress;
    @FXML
    private TextField addPhone;

    @FXML
    private VBox analysisVbox;

    @FXML
    private TableView publisherOrdersTable;

    @FXML
    private TableColumn publisherOrdersIsbn;

    @FXML
    private TableColumn publisherOrderPublisher;



    private int lastAddedBookISBN = -1;

    public static Stage managerStage;
    private String authorNameToAdd;

    public Stage getStage(){
        return managerStage;
    }

    public void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manager_layout.fxml"));
            Parent root1 = fxmlLoader.load();
            managerStage = new Stage();
            managerStage.setScene(new Scene(root1));
            managerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void addAuthor(ActionEvent event) {
        String authorName = addAuthorTxt.getText();
        if (!DataBaseHelper.getInstance().addAuthor(lastAddedBookISBN, authorName)) {
            MassageController.getInstance().show("Name is invalid");
        } else {
            addAuthorTxt.setText("");
            MassageController.getInstance().show("Successfully added");
        }
    }

    @FXML
    void addBook(ActionEvent event) {
        try {
            int pyear = Integer.valueOf(pubyear.getText());
            String cat = addbookcat.getSelectionModel().getSelectedItem();
            int price = Integer.valueOf(addbookprice.getText());
            int quan = Integer.valueOf(addbookquant.getText());
            String pubName = addbookpubname.getText();
            int isbn = Integer.valueOf(addBookISBN.getText());
            String title = (addbooktitle.getText());
            int threshold = Integer.valueOf(addbookthres.getText());
            if (!DataBaseHelper.getInstance().addbook(isbn, title, pubName, pyear, price, quan, threshold, cat)) {
                //TODO error from database
            } else {
                addAuthorLabelISBN.setText(String.valueOf(isbn));
                lastAddedBookISBN = isbn;
                show(addBookpane2);

                MassageController.getInstance().show("Successfully added book");
                pubyear.setText("");
                addbookprice.setText("");
                addbookquant.setText("");
                addbookpubname.setText("");
                addBookISBN.setText("");
                addbooktitle.setText("");
                addbookthres.setText("");
            }
        } catch (Exception e) {
            MassageController.getInstance().show("Not a valid number");
        }

    }

    @FXML
    void confirmOrder(ActionEvent event) {
        PublisherOrder selectedItem = (PublisherOrder) publisherOrdersTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null)
            return;

        if (!DataBaseHelper.getInstance().confirmOrder(selectedItem.getIsbn(), selectedItem.getPublisherName())) {
            //TODO error
        } else {
            publisherOrdersTable.getItems().remove(selectedItem);
            MassageController.getInstance().show("Successfully Confirmed");
        }
    }

    @FXML
    void modify(ActionEvent event) {
        int isbn = Integer.valueOf(modifyISBN.getText());
        int quan = Integer.valueOf(modifyQuantity.getText());
        if (!DataBaseHelper.getInstance().modifyBook(isbn, quan)) {
            //TODO error modifyng
        } else {
            MassageController.getInstance().show("Successfully modified");
            modifyISBN.setText("");
            modifyQuantity.setText("");
        }
    }

    @FXML
    void placeOrder(ActionEvent event) {
        System.out.println("------------------>" + orderISBN.getText());
        int isbn = Integer.valueOf(orderISBN.getText());
        String name = orderPubName.getText();
        int quan = Integer.valueOf(orderQuan.getText());
        if (!DataBaseHelper.getInstance().placeOrder(isbn, name, quan)) {
            //TODO errorplacing order
        } else {
            MassageController.getInstance().show("Successfully placed order");
            orderISBN.setText("");
            orderPubName.setText("");
            orderQuan.setText("");
        }
    }

    @FXML
    void promote(ActionEvent event) {
        String un = username.getText();
        if (!DataBaseHelper.getInstance().promote(un)) {
            //TODO no usr
        } else {
            MassageController.getInstance().show("Successfully promoted user: " + un);
            username.setText("");
        }
    }


    @FXML
    void STbook(ActionEvent event) {
        show(addBookpane1); // show 2 on button click add book
    }

    @FXML
    void STModBook(ActionEvent event) {
        show(modifyPane);
    }

    @FXML
    void STplaceOrd(ActionEvent event) {
        show(placeOrderpane);
    }

    @FXML
    void STconfOrd(ActionEvent event) {
        show(confirmPane);
        ResultSet result = DataBaseHelper.getInstance().get_unconfirmed_orders();
        if (result == null)
            return;

        try {

            while (result.next()) {
                PublisherOrder book = new PublisherOrder(result.getInt(1), result.getString(2));
                publisherOrdersTable.getItems().add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void STpromote(ActionEvent event) {
        show(promote);
    }

    private void show(Pane show) {

        author2Pane.setDisable(true);
        author2Pane.setVisible(false);
        addpublisherPane1.setVisible(false);
        addpublisherPane1.setDisable(true);
        addpubPane.setVisible(false);
        addpubPane.setDisable(true);

        analysis.setVisible(false);
        analysis.setDisable(true);

        addBookpane1.setVisible(false);
        addBookpane1.setDisable(true);

        addBookpane2.setVisible(false);
        addBookpane2.setDisable(true);

        modifyPane.setVisible(false);
        modifyPane.setDisable(true);

        placeOrderpane.setDisable(true);
        placeOrderpane.setVisible(false);

        confirmPane.setVisible(false);
        confirmPane.setDisable(true);

        promote.setDisable(true);
        promote.setVisible(false);
        if (show != null) {
            show.setVisible(true);
            show.setDisable(false);
        }
    }

    private void showScrollView() {
        show(null);
        analysis.setVisible(true);
        analysis.setDisable(false);
    }

    @FXML
    void totSalesPrevMonth(ActionEvent event) {
        showScrollView();
        ResultSet rs = DataBaseHelper.getInstance().totalSalesPrevMonth();
        if (rs != null) {
            analysisVbox.getChildren().clear();
            analysisVbox.getChildren().add(new Label("ISBN\tQuantity\tUserID\tDate"));
            try {
            	while (rs.next()) {

                analysisVbox.getChildren().add(new Label(rs.getInt(2) + "\t\t" + rs.getInt(3) +
                "\t" + rs.getInt(4) + "\t"+rs.getDate(5)));
            	}
            } catch (SQLException e) {
                MassageController.getInstance().show(e.getMessage());
            }
        }
        try {
            DataBaseHelper.getInstance().closeConnection();
        } catch (Exception e) {
            MassageController.getInstance().show(e.getMessage());
        }
    }

    @FXML
    void top5CustomersIn3Monthes(ActionEvent event) {
        showScrollView();
        ResultSet rs = DataBaseHelper.getInstance().top5CustomersInlast3Monthes();
        if (rs != null) {
            analysisVbox.getChildren().clear();
            analysisVbox.getChildren().add(new Label("UserName\t\tQuantity"));
            try {
            	while (rs.next()) {
                analysisVbox.getChildren().add(new Label(rs.getString(2)+"\t\t"+rs.getInt(11)));
            	}
            } catch (SQLException e) {
                MassageController.getInstance().show(e.getMessage());
            }
        }
        try {
            DataBaseHelper.getInstance().closeConnection();
        } catch (Exception e) {
            MassageController.getInstance().show(e.getMessage());
        }
    }

    @FXML
    void top10SellingBooksIn3Monthes(ActionEvent event) {
        showScrollView();
        ResultSet rs = DataBaseHelper.getInstance().top10salesInLastThreeMonthes();
        if (rs != null) {
            analysisVbox.getChildren().clear();
            analysisVbox.getChildren().add(new Label("Title\t\tQuantity"));
            try {
            	while(rs.next()){
                analysisVbox.getChildren().add(new Label(rs.getString(2) + "\t\t" + rs.getInt(10)));
            }
            } catch (SQLException e) {
                MassageController.getInstance().show(e.getMessage());
            }
        }
        try {
            DataBaseHelper.getInstance().closeConnection();
        } catch (Exception e) {
            MassageController.getInstance().show(e.getMessage());
        }
    }

    @FXML
    void viewAddpub() {
        show(addpubPane);
    }


    @FXML
    void addpublisher() {

        String pn = addAuthName.getText();
        if (DataBaseHelper.getInstance().addNewPublisher(pn)) {
            authorNameToAdd = pn;
            show(addpublisherPane1);
        }
    }

    @FXML
    void addAuthAddress() {
        if (!DataBaseHelper.getInstance().addAddressToAuth(authorNameToAdd, addAddress.getText())) {
            //TODO err
        } else {
            MassageController.getInstance().show("Successfully added");
            addAddress.setText("another !");
        }
    }

    @FXML
    void addAuthPhone() {
        if (!DataBaseHelper.getInstance().addPhoneToAuth(authorNameToAdd, addPhone.getText())) {
            //TODO err
        } else {
            MassageController.getInstance().show("Successfully added");
            addPhone.setText("another !");
        }
    }

    @FXML
    void resetView() {
        show(addBookpane1);
    }

    @FXML
    void addAuthor2() {
        if (!DataBaseHelper.getInstance().addAuthor2(addAUTHNAME.getText())) {
            //TODO error
        }
    }

    @FXML
    void viewAuth() {
        show(author2Pane);
    }

    public void initialize() {

        publisherOrdersIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publisherOrderPublisher.setCellValueFactory(new PropertyValueFactory<>("publisherName"));

        addbookcat.getItems().removeAll(addbookcat.getItems());
        addbookcat.getItems().addAll("Science", "Art", "Religion", "History", "Geography");
        addbookcat.getSelectionModel().select("Science");

        onlyLetters(orderPubName);
        onlyLetters(addbookpubname);
        onlyLetters(addAuthName);
        onlyLetters(addAUTHNAME);

        onlyNums(orderISBN);
        onlyNums(orderQuan);
        onlyNums(addBookISBN);
        onlyNums(pubyear);
        onlyNums(addbookprice);
        onlyNums(addbookquant);
        onlyNums(addbookthres);
//        onlyNums(addAuthorTxt);
        onlyNums(modifyISBN);
        onlyNums(modifyQuantity);
        onlyNums(addPhone);

    }

    private void onlyLetters(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
    }

    void onlyNums(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
