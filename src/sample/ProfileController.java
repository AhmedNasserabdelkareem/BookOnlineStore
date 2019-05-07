package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public void show(){
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

    }

}