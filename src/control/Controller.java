package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    public void onClickExit(){
        System.out.println("Exit application");
        System.exit(0);
    }

    @FXML
    public void onClickSetBasePath(){
        System.out.println("Set Base Path...");
    }

    @FXML
    public void onClickSetFilter(){
        System.out.println("Set Filter...");
    }

    @FXML
    public void onClickAbout(){
        System.out.println("Paper Jets v0.1");
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/about.fxml"));
            Stage aboutWindow = new Stage();
            aboutWindow.setTitle("About PaperJets");
            aboutWindow.setScene(new Scene(root, 300, 250));
            aboutWindow.getIcons().add(new Image("res/ic_send_black_48dp.png"));
            aboutWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
