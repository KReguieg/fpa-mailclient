package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Message;

import java.io.File;
import java.util.List;

/**
 * Created by Khale_000 on 30.06.2015.
 */
public class ContainsSenderMailFilter extends Filter {

    public ContainsSenderMailFilter(FPAMessageLoader fpaMessageLoader, String filterCriteria) {
        super(fpaMessageLoader, filterCriteria);
    }

    @Override
    public List<Message> getMessages(File path) {
        ObservableList<Message> messageList = (ObservableList<Message>) fpaMessageLoader.getMessages(path);
        ObservableList<Message> filteredMessageList = FXCollections.observableArrayList();
        for (Message msg: messageList) {
            if(msg.getSender().getMailAddress().contains(filterCriteria)) {
                filteredMessageList.add(msg);
            }
        }
        return filteredMessageList;
    }
}
