package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
//
<<<<<<< HEAD
//        SignUpController signUpController = new SignUpController();
//        signUpController.show();

//        UserController userController = new UserController();
//        userController.show(true);
=======
     //   SignUpController signUpController = new SignUpController();
       // signUpController.show();
        UserController userController = new UserController();
        userController.show();
>>>>>>> baaf1cdc86ab87b7f073025bfc17f71452ed1b4c

        SignInController signInController = new SignInController();
        signInController.show();
//        ManagerController mg = new ManagerController();
//        mg.show();

//        Parent root = FXMLLoader.load(getClass().getResource("user_layout.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
