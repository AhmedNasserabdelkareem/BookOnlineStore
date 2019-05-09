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
        //TODO login in database
        int code=DataBaseHelper.getInstance().signin(usrNameTxt.getText(),passTxt.getText());
        switch (code){
            case -1:
                //TODO error
                break;
            case 0:
                //TODO user not found
                break;
            case 1:
                //TODO user found ,, wrong pass
                break;
            case 2:
                //TODO Manager found correct pass
                break;
            case 3:
                //TODO user found correct pass
                break;
            default:
                //TODO unexpected path errror
                break;


        }
        UserController.userName = usrNameTxt.getText();
        signInStage.close();
    }

    @FXML
    private void signUp() {
        SignUpController signUpController = new SignUpController();
        signUpController.show();
        signInStage.close();
    }

}
