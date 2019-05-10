package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {

    private static Stage profileStage;

    @FXML
    private Button SaveBtn;

    @FXML
    private Label typelbl;
    @FXML
    private PasswordField prOldPass;

    @FXML
    private PasswordField prConfirmPass;

    @FXML
    private TextField prShippingAddress;

    @FXML
    private TextField usernametxt;
    @FXML
    private TextField firstnametxt;
    @FXML
    private TextField lastnametxt;
    @FXML
    private TextField emailtxt;
    @FXML
    private TextField prPhoneNumber;

    @FXML
    private PasswordField prNewPass;

    @FXML
    private Button cancelBtn;

    public void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profileLayout.fxml"));
            Parent root1 = fxmlLoader.load();
            profileStage = new Stage();
            profileStage.setScene(new Scene(root1));
            profileStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO fill old info

    }

    @FXML
    void cancel(ActionEvent event) {
        profileStage.close();
    }

    @FXML
    void save(ActionEvent event) {

        if (!isValidEmailAddress(emailtxt.getText())){
            MassageController.getInstance().show("Invalid Email address");
            return;
        }

        if (prNewPass.getText().length() < 8){
            MassageController.getInstance().show("Password must be at least 8 digits");
            return;
        }

        if (!prConfirmPass.getText().equals(prNewPass.getText())) {
            MassageController.getInstance().show("Password mismatch");
            return;
        }

        DataBaseHelper.getInstance().updateUserInfo(
                UserController.userName,usernametxt.getText()
                , prOldPass.getText(), prNewPass.getText(),
                firstnametxt.getText(),lastnametxt.getText(),emailtxt.getText(), prShippingAddress.getText(), prPhoneNumber.getText());

        MassageController.getInstance().show("Data updated successfully");
    }

    public void initialize() {
        System.out.println("----"+UserController.userName);
        ResultSet rs = DataBaseHelper.getInstance().profileInfo(UserController.userName);

        try {
            rs.next();
            if(rs.getBoolean(9)){
                typelbl.setText("user type : Manager");
            }else {
                typelbl.setText("user type : normal user");
            }
            prShippingAddress.setText(rs.getString(8));
            prPhoneNumber.setText(rs.getString(7));
            emailtxt.setText(rs.getString(6));
            firstnametxt.setText(rs.getString(4));
            lastnametxt.setText(rs.getString(5));
            usernametxt.setText(rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        onlyLetters(firstnametxt);
        onlyLetters(lastnametxt);

        prPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    prPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private void onlyLetters (TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
    }

}
