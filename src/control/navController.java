package control;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Khale_000 on 14.04.2015.
 */
public class NavController {

    private final Node rootIcon = new ImageView(new Image("res/ic_inbox_black_18dp.png"));
    private final Node readIcon = new ImageView(new Image("res/ic_drafts_black_18dp.png"));
    private final Node favIcon = new ImageView(new Image("res/ic_favorite_black_18dp.png"));
    private final Node spamIcon = new ImageView(new Image("res/ic_warning_black_18dp.png"));
    private final Node trashIcon = new ImageView(new Image("res/ic_delete_black_18dp.png"));
    private final Node folderIcon = new ImageView(new Image("res/ic_folder_black_18dp.png"));

    @FXML
    private TreeView<String> treeViewId;

    public void initialize() {
        loadTreeItems("Unread", "Favs", "Spam", "Trash", "Subfolder5");
    }

    public void loadTreeItems(String... rootItems){
        TreeItem<String> treeRoot = new TreeItem<String>("Inbox", rootIcon);
        treeRoot.setExpanded(true);
        for (String itemString: rootItems) {
            treeRoot.getChildren().add(new TreeItem<String>(itemString));
        }
        treeRoot.getChildren().get(0).setGraphic(readIcon);
        treeRoot.getChildren().get(1).setGraphic(favIcon);
        treeRoot.getChildren().get(2).setGraphic(spamIcon);
        treeRoot.getChildren().get(3).setGraphic(trashIcon);
        treeRoot.getChildren().get(4).setGraphic(folderIcon);
        treeViewId.setRoot(treeRoot);
    }
}
