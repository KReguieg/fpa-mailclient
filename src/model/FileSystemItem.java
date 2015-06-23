package model;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Created by Khaled Reguieg on 26.05.2015.
 */
public abstract class FileSystemItem extends TreeItem {

    protected File file;
    protected boolean leaf;

    public FileSystemItem(File file, boolean leaf) {
        super(file.getName());
        ImageView icon = new ImageView(new Image("res/ic_folder_black_18dp.png"));
        this.file = file;
        this.leaf = leaf;
        setGraphic(icon);
    }

    public File getFile() {
        return file;
    }
}
