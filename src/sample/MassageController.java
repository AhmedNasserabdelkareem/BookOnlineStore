package sample;

/**
 * Created by sharaf on 09/05/2019.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MassageController {

    private static Stage massage;
    private static MassageController massageController;
    private static String txt;

    @FXML
    private Label massageLbl;

    @FXML
    void dismiss(ActionEvent event) {
        massage.close();
    }

    public static  MassageController getInstance(){
        if (massageController == null)
            massageController = new MassageController();

        return massageController;
    }

    public void show(String massageTxt) {
        try {
            txt = massageTxt;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("massage.fxml"));
            Parent root1 = fxmlLoader.load();
            massage = new Stage();
            massage.setScene(new Scene(root1));
            massage.initModality(Modality.APPLICATION_MODAL);
            massage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        massageLbl.setText(txt);
    }

}

