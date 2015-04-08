package control;

import javafx.fxml.FXML;

public class Controller {

    @FXML
    public void onClickExit(){
        System.out.println("Exit application");
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
    }
}
