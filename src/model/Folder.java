package model;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;

/**
 * Created by Khaled Reguieg s813812 Khaled.Reguieg@gmail.com on 26.05.2015.
 */
public class Folder extends FileSystemItem {

    public Folder(File file) {
        super(file);
    }

    @Override
    public ObservableList<TreeItem> getChildren() {
        ObservableList<TreeItem> childList = super.getChildren();
        File[] fileList = file.listFiles();
        // if directory create new folder and pass him a file as argument
        for (File file : fileList) {
            if (file.isDirectory() && !(childList.size() == fileList.length)) {
                childList.add(new Folder(file));
            }
        }
        return childList;
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
