package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserController {

    private static Stage userStage;

    @FXML
    private TextField bookTitleTxt;

    @FXML
    private ComboBox<String> categoriesMenu;

    @FXML
    private TextField authorTxt;

    @FXML
    private TextField publisherTxt;

    @FXML
    private CheckBox filterBookTitleCheck;

    @FXML
    private CheckBox filterAuthorCheck;

    @FXML
    private CheckBox filterPublisherCheck;

    @FXML
    private TextField publishDate;

    @FXML
    private CheckBox filterDateCheck;

    @FXML
    private CheckBox filterPriceCheck;

    @FXML
    private Label lblFrom;

    @FXML
    private TextField priceMinTxt;

    @FXML
    private Label lblTo;

    @FXML
    private TextField priceMaxTxt;

    @FXML
    private CheckBox filterCategoryCheck;

    @FXML
    private AnchorPane searchResultView;

    @FXML
    private ScrollPane cartView;

    @FXML
    private Button orderBtn;

    @FXML
    private Button manageBtn;

    @FXML
    private TableView searchResultTable;

    @FXML
    private TableView cartTable;

    private LinkedList<ResultSet> cartItem = new LinkedList<>();

    @FXML
    void EditProfile(ActionEvent event) {
        ProfileController profileController = new ProfileController();
        profileController.show();
    }

    @FXML
    void filterAuthor(ActionEvent event) {
        authorTxt.setDisable(!filterAuthorCheck.isSelected());
    }

    @FXML
    void filterBookTitle(ActionEvent event) {
        bookTitleTxt.setDisable(!filterBookTitleCheck.isSelected());
    }

    @FXML
    void filterCategories(ActionEvent event) {
        categoriesMenu.setDisable(!filterCategoryCheck.isSelected());
    }

    @FXML
    void filterDate(ActionEvent event) {
        publishDate.setDisable(!filterDateCheck.isSelected());
    }

    @FXML
    void filterPrice(ActionEvent event) {
        priceMaxTxt.setDisable(!filterPriceCheck.isSelected());
        priceMinTxt.setDisable(!filterPriceCheck.isSelected());
        lblTo.setDisable(!filterPriceCheck.isSelected());
        lblFrom.setDisable(!filterPriceCheck.isSelected());
    }

    @FXML
    void filterPublisher(ActionEvent event) {
        publisherTxt.setDisable(!filterPublisherCheck.isSelected());
    }

    @FXML
    void order(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {
        ResultSet rs = DataBaseHelper.getInstance().searchBook(bookTitleTxt.getText(), authorTxt.getText(), publisherTxt.getText(),
                categoriesMenu.getValue(), Integer.valueOf(publishDate.getText()), Integer.valueOf(priceMinTxt.getText()), Integer.valueOf(priceMaxTxt.getText()));
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                String title = rs.getString(2);
                String year = rs.getString(4);
                String price = rs.getString(5);
                String quantity = rs.getString(8);
                searchResultTable.getColumns().add(0, title);
                searchResultTable.getColumns().add(1, rs.getString(year));
                searchResultTable.getColumns().add(2, rs.getString(price));
                searchResultTable.getColumns().add(3, rs.getString(quantity));

                Button cartBtn = new Button("+");
                cartBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        cartItem.add(rs);
                        cartTable.getColumns().add(0, title);//title
                        cartTable.getColumns().add(1,year);//year
                        cartTable.getColumns().add(2, price);//price
                        cartTable.getColumns().add(3, "5");//quantitiy

                        Button cartRemove = new Button("X");
                        cartRemove.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event){
                                cartTable.
                            }
                        });
                    }
                });

                searchResultTable.getColumns().add(4, cartBtn);//title

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manage() {
        ManagerController managerController = new ManagerController();
        managerController.show();
    }


    public void show(boolean manager) {
        if (!manager)
            manageBtn.setVisible(false);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user_layout.fxml"));
            Parent root1 = fxmlLoader.load();
            userStage = new Stage();
            userStage.setScene(new Scene(root1));
            userStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
