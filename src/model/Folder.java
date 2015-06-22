package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Khaled Reguieg s813812 Khaled.Reguieg@gmail.com on 26.05.2015.
 */
public class Folder extends FileSystemItem {

    ObservableList<TreeItem> childList;

    public Folder(File file) throws IOException {
        super(file);
        if (!this.file.isDirectory()) {
            throw new IOException("Path " + this.file.getAbsolutePath() + " is no directory!");
        }
        this.childList = FXCollections.observableArrayList();
        ImageView folderIcon = new ImageView(new Image("src\\res\\ic_folder_black_18dp.png"));
        folderIcon.setFitHeight(16);
        folderIcon.setFitWidth(16);
        this.setGraphic(folderIcon);
        scanSubFolders();
    }

    public ObservableList<TreeItem> getChildList() {
        return this.childList;
    }

    private void scanSubFolders() throws IOException {
        this.childList = super.getChildren();
        File[] folders = this.file.listFiles((dir, name) -> {
            return new File(dir, name).isDirectory();
        });
        for (File folder : folders) {
            this.addChildren(new Folder(folder));
        }
    }

    private void addChildren(FileSystemItem item) {
        this.childList.add(item);
    }


    @Override
    public String toString() {
        return file.getName();
    }
}
