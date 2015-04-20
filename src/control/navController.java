package control;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Created by Khale_000 on 14.04.2015.
 */
public class NavController {

    @FXML
    private TreeView<String> treeViewId;

    public void initialize() {
        loadTreeItems("Spam", "Trash", "Spam", "Subfolder4", "Subfolder5");
    }

    public void loadTreeItems(String... rootItems){
        TreeItem<String> treeRoot = new TreeItem<String>("Inbox");
        treeViewId.setRoot(treeRoot);
        treeRoot.setExpanded(true);
        treeRoot.setExpanded(true);
        for (String itemString: rootItems) {
            treeRoot.getChildren().add(new TreeItem<String>(itemString));
        }
    }
}
