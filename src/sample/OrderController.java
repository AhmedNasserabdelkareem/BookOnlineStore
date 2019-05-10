package sample;

/**
 * Created by sharaf on 10/05/2019.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BookSearchResult;

import java.util.Iterator;
import java.util.LinkedList;

public class OrderController {

    private static Stage orderStage;
    public static LinkedList<BookSearchResult> cart;

    @FXML
    private TextField creditCardTxt;

    public void initialize() {
        creditCardTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    creditCardTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    void onOrder(ActionEvent event) {
        String num = creditCardTxt.getText();
        String prefix = num.substring(0, 2);
        if ((num.length() >= 13 || num.length() <= 16) && checkPrefix(prefix)) {
            long number = Long.valueOf(num);
            if ((sumOfDoubleEvenPlace(number, num.length()) +
                    sumOfOddPlace(number, num.length())) % 10 == 0) {
                orderBooks();
                MassageController.getInstance().show("valid");
                return;
            }

            MassageController.getInstance().show("Invalid Credit Card number");
        }
    }

    private void orderBooks() {
        try {
            DataBaseHelper.getInstance().openConnection();
            for (Iterator i = cart.iterator(); i.hasNext(); ) {
                BookSearchResult book = (BookSearchResult) i.next();
                DataBaseHelper.getInstance().orderBook(book.getIsbn(), Integer.valueOf(book.getQuantity()), UserController.userName);
            }
            DataBaseHelper.getInstance().closeConnection();
        } catch (Exception e) {
            MassageController.getInstance().show(e.getMessage());
        }
        MassageController.getInstance().show("Order placed successfully");
        orderStage.close();
    }

    // Get the result from Step 2
    private int sumOfDoubleEvenPlace(long number, int size) {
        int sum = 0;
        String num = number + "";
        for (int i = size - 2; i >= 0; i -= 2) {
            System.out.println(Integer.parseInt(num.charAt(i) + "") * 2);
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);
        }

        return sum;
    }

    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    private int getDigit(int number) {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    // Return sum of odd-place digits in number
    private int sumOfOddPlace(long number, int size) {
        int sum = 0;
        String num = number + "";
        for (int i = size - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    private Character singleDigit[] = {'4', '5', '6'};
    private String doubleDigit[] = {"37", "51", "52", "53", "54", "55"};

    private boolean checkPrefix(String prefix) {
        for (int i = 0; i < singleDigit.length; i++)
            if (prefix.charAt(0) == singleDigit[i])
                return true;

        for (int i = 0; i < doubleDigit.length; i++)
            if (doubleDigit[i].equals(prefix))
                return true;

        return false;
    }

    public void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order_layout.fxml"));
            Parent root1 = fxmlLoader.load();
            orderStage = new Stage();
            orderStage.setScene(new Scene(root1));
            orderStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

