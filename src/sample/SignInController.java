package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {

    private static Stage signInStage;

    @FXML
    private TextField usrNameTxt;
    @FXML
    private TextField passTxt;

    @FXML
    private TextField SUEmail;
    @FXML
    private TextField SUFirstName;
    @FXML
    private TextField SUSecondName;
    @FXML
    private TextField SUUserName;
    @FXML
    private TextField USPass;
    @FXML
    private TextField USPass1;
    @FXML
    private TextField SUSHAdd;
    @FXML
    private TextField PhoneAdd;

    public void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign_in_layout.fxml"));
            Parent root1 = fxmlLoader.load();
            signInStage = new Stage();
            signInStage.setScene(new Scene(root1));
            signInStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logIn() {

    }

    @FXML
    private void signUp() {
        SignUpController signUpController = new SignUpController();
        signUpController.show();
        signInStage.close();
    }

}
