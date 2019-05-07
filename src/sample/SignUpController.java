package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController {

    private static Stage signUpStage;

    @FXML
    private Button signUP;

    @FXML
    private TextField SUUserName;

    @FXML
    private TextField SUFirstName;

    @FXML
    private TextField SUSecondName;

    @FXML
    private PasswordField USPass;

    @FXML
    private PasswordField USPass1;

    @FXML
    private TextField SUSHAdd;

    @FXML
    private TextField PhoneAdd;

    public void show() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign_up_layout.fxml"));
            Parent root1 = fxmlLoader.load();
            signUpStage = new Stage();
            signUpStage.setScene(new Scene(root1));
            signUpStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openSignIn(MouseEvent event) {
        SignInController signInController = new SignInController();
        signInController.show();
        signUpStage.close();
    }

    @FXML
    void signUp(ActionEvent event) {

    }

}
