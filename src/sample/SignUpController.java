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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController {

    private static Stage signUpStage;

    @FXML
    private TextField SUEmail;

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

        if (!USPass.getText().equals(USPass1.getText())) {
            MassageController.getInstance().show("Password mismatch");
            return;
        }

        if (DataBaseHelper.getInstance().signUp(SUUserName.getText(), USPass.getText(), SUFirstName.getText(),
                SUSecondName.getText(), SUEmail.getText(), PhoneAdd.getText(), SUSHAdd.getText())){
            SignInController signInController = new SignInController();
            signInController.show();
            MassageController.getInstance().show("Signed up successfully");
            signUpStage.close();
        }
    }

    public void initialize(){
        PhoneAdd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    PhoneAdd.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

}
