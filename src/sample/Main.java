package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

        UserController userController = new UserController();
        userController.show(true);

//        SignInController signInController = new SignInController();
//        signInController.show();

//        Parent root = FXMLLoader.load(getClass().getResource("user_layout.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}