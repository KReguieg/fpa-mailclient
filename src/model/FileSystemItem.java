package model;

import javafx.scene.control.TreeItem;

import java.io.File;

/**
 * Created by Khaled Reguieg on 26.05.2015.
 */
public abstract class FileSystemItem extends TreeItem {

    protected File file;

    public FileSystemItem(File file) {
        super(file.getName());
        this.file = file;
    }
}
