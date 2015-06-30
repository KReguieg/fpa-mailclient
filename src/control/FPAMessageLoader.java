package control;

import model.Message;

import java.io.File;
import java.util.List;

/**
 * Created by Khaled Reguieg on 30.06.2015.
 */
public interface FPAMessageLoader {
    public List<Message> getMessages(File path);
}
