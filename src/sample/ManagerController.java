package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private TextField addbookcat;

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
    private Pane addAuthorPane;
    @FXML
    private Pane addAuthorPane1;

    @FXML
    private  TextField addAuthName;

    @FXML
    private TextField addAddress;
    @FXML
    private TextField addPhone;

    private int lastAddedBookISBN=-1;

    private static Stage managerStage;
    private String authorNameToAdd;


    public void show(){
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
        if(!DataBaseHelper.getInstance().addAuthor(lastAddedBookISBN,authorName)){
            //TODO error in author name
        }

    }

    @FXML
    void addBook(ActionEvent event) {
        try {
            int pyear =Integer.valueOf( pubyear.getText());
            String cat = addbookcat.getText();
            int price =Integer.valueOf(addbookprice.getText());
            int quan =  Integer.valueOf( addbookquant.getText());
            String pubName = addbookpubname.getText();
            int isbn =Integer.valueOf( addBookISBN.getText());
            String title =( addbooktitle.getText());
            int threshold =  Integer.valueOf(addbookthres.getText());
            if(!DataBaseHelper.getInstance().addbook(isbn,title,pubName,pyear,price,quan,threshold,cat)){
                //TODO error from database
            }else{
                addAuthorLabelISBN.setText(String.valueOf(isbn));
                lastAddedBookISBN = isbn;
                show(addBookpane2);
            }
        }catch (Exception e){
            //TODO error in parsing to int
        }

    }

    @FXML
    void confirmOrder(ActionEvent event) {
        int isbn = Integer.valueOf(cOrderISBN.getText());
        String pubName = cOrderPubN.getText();
        if(!DataBaseHelper.getInstance().confirmOrder(isbn,pubName)){
            //TODO error
        }
    }

    @FXML
    void modify(ActionEvent event) {
       int isbn = Integer.valueOf( modifyISBN.getText());
       int quan = Integer.valueOf(modifyQuantity.getText());
       if(!DataBaseHelper.getInstance().modifyBook(isbn,quan)){
           //TODO error modifyng
       }
    }

    @FXML
    void placeOrder(ActionEvent event) {
        int isbn = Integer.valueOf(orderISBN.getText());
        String name = orderPubName.getText();
        int quan = Integer.valueOf(orderQuan.getText());
        if(!DataBaseHelper.getInstance().placeOrder(isbn,name,quan)){
            //TODO errorplacing order
        }
    }

    @FXML
    void promote(ActionEvent event) {
        String un = username.getText();
        if(!DataBaseHelper.getInstance().promote(un)){
            //TODO username not exists
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
    }
    @FXML
    void STpromote(ActionEvent event) {
        show(promote);
    }

    private void  show(Pane show){

        author2Pane.setDisable(true);
        author2Pane.setVisible(false);
        addAuthorPane.setVisible(false);
        addAuthorPane.setDisable(true);
        addAuthorPane1.setVisible(false);
        addAuthorPane1.setDisable(true);

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
        if(show!=null) {
            show.setVisible(true);
            show.setDisable(false);
        }
    }

    private  void showScrollView(){
        show(null);
        analysis.setVisible(true);
        analysis.setDisable(false);
    }
    @FXML
    void totSalesPrevMonth(ActionEvent event){
      showScrollView();
    }

    @FXML
    void top5CustomersIn3Monthes(ActionEvent event){
        showScrollView();

    }

    @FXML
    void top10SellingBooksIn3Monthes(ActionEvent event){
        showScrollView();

    }

    @FXML
    void viewAddAuth(){
        show(addAuthorPane);
    }



    @FXML
    void  addAuthor(){

        String pn = addAuthName.getText();
        if(DataBaseHelper.getInstance().addNewAuthor(pn)){
            authorNameToAdd = pn;
            //TODO move on
            show(addAuthorPane);
        }else {
            //TODO error pub name does exist !!
        }


    }

    @FXML
    void addAuthAddress(){
       if(! DataBaseHelper.getInstance().addAddressToAuth(authorNameToAdd,addAddress.getText())){
            //TODO err
       }else{
           addAddress.setText("another !");
       }
    }

    @FXML
    void addAuthPhone(){
        if(! DataBaseHelper.getInstance().addPhoneToAuth(authorNameToAdd,addPhone.getText())){
            //TODO err
        }else {
            addPhone.setText("another !");
        }
    }
    @FXML
    void resetView(){
        show(addBookpane1);
    }

    @FXML
    void addAuthor2(){
        if(!DataBaseHelper.getInstance().addAuthor2(addAUTHNAME.getText())){
            //TODO error
        }


    }
    @FXML
    void viewAuth(){
        show(author2Pane);
    }
}
