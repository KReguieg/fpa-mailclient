package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by Khaled Reguieg s813812 Khaled.Reguieg@gmail.com on 26.05.2015.
 */
public class Folder extends FileSystemItem {

    public Folder(File file, boolean leaf) throws IOException {
        super(file, leaf);
    }

    @Override
    public ObservableList<TreeItem> getChildren() {
        ObservableList<TreeItem> childList = super.getChildren();
        File[] listFiles = file.listFiles();
        Folder item;
        for(File f : listFiles) {
            if(f.isDirectory() && leaf == false) {
                try {
                    item = new Folder(f, leaf);
                    childList.add(item);
                    item.getChildren();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        leaf = true;
        return childList;
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
