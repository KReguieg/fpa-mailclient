package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private BorderPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/root.fxml"));
        primaryStage.setTitle("PaperJets");
        primaryStage.getIcons().add(new Image("res/ic_send_black_48dp.png"));
        primaryStage.setScene(new Scene(root, 800, 500));
        AnchorPane treeMenu = FXMLLoader.load(getClass().getResource("../view/nav.fxml"));
        root.setLeft(treeMenu);
        SplitPane contentWindow = FXMLLoader.load(getClass().getResource("../view/content.fxml"));
        root.setRight(contentWindow);
        primaryStage.show();
    }

//    public void showMessageOverview() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("../view/content.fxml"));
//            AnchorPane messageOverview = (AnchorPane) loader.load();
//
//            root.setCenter(messageOverview);
//
//            ContentController controller = loader.getController();
//            controller.setMain(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
