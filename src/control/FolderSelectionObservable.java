package control;

import java.io.File;
import java.util.Observable;

/**
 * Created by Ibrahim Khaled Reguieg, Khaled.Reguieg@gmail.com, github.com/KReguieg on 20.06.2015.
 */
public class FolderSelectionObservable extends Observable {
    private static FolderSelectionObservable observable;

    private FolderSelectionObservable() {

    }

    public static FolderSelectionObservable getInstance() {
        if(observable == null) {
            observable = new FolderSelectionObservable();
        }
        return observable;
    }

    public void newFolder(File file) {
        setChanged();
        notifyObservers(file);
    }
}
