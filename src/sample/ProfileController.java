package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private PasswordField prOldPass;

    @FXML
    private PasswordField prConfirmPass;

    @FXML
    private TextField prShippingAddress;

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

        if (prNewPass.getText().length() < 8){
            MassageController.getInstance().show("Password must be at least 8 digits");
            return;
        }

        if (!prConfirmPass.getText().equals(prNewPass.getText())) {
            MassageController.getInstance().show("Password mismatch");
            return;
        }

        DataBaseHelper.getInstance().updateUserInfo(UserController.userName, prOldPass.getText(),
                prNewPass.getText(), prShippingAddress.getText(), prPhoneNumber.getText());

        MassageController.getInstance().show("Data updated successfully");
    }

    public void initialize() {
        ResultSet rs = DataBaseHelper.getInstance().profileInfo(UserController.userName);
        try {
            prShippingAddress.setText(rs.getString(2));
            prPhoneNumber.setText(rs.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

}
