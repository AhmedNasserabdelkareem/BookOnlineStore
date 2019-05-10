package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.BookSearchResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
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
    private Button addToCart;

    @FXML
    private Button manageBtn;

    @FXML
    private TableView searchResultTable;

    @FXML
    private TableView cartTable;

    @FXML
    private TableColumn searchTableTitle;

    @FXML
    private TableColumn searchTableYear;

    @FXML
    private TableColumn searchTablePrice;

    @FXML
    private TableColumn searchTableQuantity;

    @FXML
    private TableColumn cartTableTitle;

    @FXML
    private TableColumn cartTableYear;

    @FXML
    private TableColumn cartTablePrice;

    @FXML
    private TableColumn cartTableQuantity;

    @FXML
    private TextField quantityTxt;
    @FXML
    private Label totalMoney;

    private LinkedList<BookSearchResult> cart = new LinkedList<>();

    public static String userName;
    public static boolean manager;

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

        Integer date = null, priceMin = null, priceMax = null;
        String bookTitle = null, author = null, publisher = null, cat = null;
        try {
            if (filterDateCheck.isSelected())
                date = Integer.valueOf(publishDate.getText());
            if (filterPriceCheck.isSelected()) {
                priceMin = Integer.valueOf(priceMinTxt.getText());
                priceMax = Integer.valueOf(priceMaxTxt.getText());
            }
        } catch (NumberFormatException e) {
        }

        if (filterBookTitleCheck.isSelected() && !bookTitleTxt.getText().equals(""))
            bookTitle = bookTitleTxt.getText();

        try {
            if (filterCategoryCheck.isSelected())
                cat = categoriesMenu.getSelectionModel().getSelectedItem();
        } catch (NullPointerException e) {
        }

        if (filterAuthorCheck.isSelected() && !authorTxt.getText().equals(""))
            author = authorTxt.getText();

        if (filterPublisherCheck.isSelected() && !publisherTxt.getText().equals(""))
            publisher = publisherTxt.getText();

        ResultSet rs = DataBaseHelper.getInstance().searchBook(bookTitle, author, publisher,
                cat, date, priceMin, priceMax);
        makeSearchTable(rs);
    }

    private void makeSearchTable(ResultSet rs) {
        try {

            while (rs.next()) {
                BookSearchResult book = new BookSearchResult(rs.getInt(1), rs.getString(2), rs.getString(4),
                        rs.getString(5), rs.getString(8));

                searchResultTable.getItems().add(book);
            }
            DataBaseHelper.getInstance().closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manage() {
        ManagerController managerController = new ManagerController();
        managerController.show();
    }

    @FXML
    void removeFromCart(ActionEvent event) {
        BookSearchResult selectedItem = (BookSearchResult) cartTable.getSelectionModel().getSelectedItem();
        cart.remove(isBookInCart(selectedItem.getIsbn()));
        cartTable.getItems().remove(selectedItem);
    }

    @FXML
    void addToCart(ActionEvent event) {
        BookSearchResult selectedItem = (BookSearchResult) searchResultTable.getSelectionModel().getSelectedItem();

        int storeQuantity;
        try {
            storeQuantity = Integer.valueOf(selectedItem.getQuantity());
        } catch (NullPointerException e) {
            MassageController.getInstance().show("Please select a book");
            return;
        }

        int quantity = 1;
        if (!quantityTxt.getText().equals(""))
            quantity = Integer.valueOf(quantityTxt.getText());

        if (quantity > storeQuantity)
            MassageController.getInstance().show("Maximum Quantity is + " + selectedItem.getQuantity());

        int index = isBookInCart(selectedItem.getIsbn());
        if (index == -1) {//notFound
            BookSearchResult book = new BookSearchResult(selectedItem.getIsbn(), selectedItem.getBookTitle(), selectedItem.getYear(),
                    String.valueOf(quantity * Integer.valueOf(selectedItem.getPrice())), String.valueOf(quantity));
            cart.add(book);
            cartTable.getItems().add(book);
        } else {
            BookSearchResult inCart = cart.get(index);
            quantity += Integer.valueOf(inCart.getQuantity());
            inCart.setQuantity(String.valueOf(quantity));
            inCart.setPrice(String.valueOf(quantity * Integer.valueOf(selectedItem.getPrice())));
            cartTable.refresh();
        }

        //calculate total money
        int total = 0;
        for (Iterator i = cart.iterator(); i.hasNext(); )
            total += Integer.valueOf(((BookSearchResult) i.next()).getPrice());
        totalMoney.setText(String.valueOf(total));
    }

    public void initialize() {
        searchTableTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        searchTableYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        searchTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        searchTableQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        cartTableTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        cartTableYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        cartTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartTableQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        setNumsOnly(publishDate);
        setNumsOnly(quantityTxt);
        setNumsOnly(priceMaxTxt);
        setNumsOnly(priceMinTxt);

        categoriesMenu.getItems().removeAll(categoriesMenu.getItems());
        categoriesMenu.getItems().addAll("Science", "Art", "Religion", "History", "Geography");
        categoriesMenu.getSelectionModel().select("Science");

        if (!manager)
            manageBtn.setVisible(false);

    }

    @FXML
    void setNumsOnly(TextField textField) {
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

    public void show() {
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

    private int isBookInCart(int isbn) {
        int i = -1;
        Iterator iterator = cart.iterator();
        while (iterator.hasNext()) {
            i++;
            if (((BookSearchResult) iterator.next()).getIsbn() == isbn)
                return i;
        }
        return i;
    }

}
