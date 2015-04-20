package control;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Message;
import model.MessageImportance;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Khaled_000 on 14.04.2015.
 */
public class ContentController {

    private Main main;

    public ContentController() {

    }

    @FXML
    private TableView<Message> tableViewId;

    @FXML
    private TableColumn<Message, MessageImportance> priorityColoumnId;

    @FXML
    private TableColumn<Message, LocalDateTime> receivedColoumnId;

    @FXML
    private TableColumn<Message, Boolean> readColoumnId;

    @FXML
    private TableColumn<Message, String> senderColoumnId;

    @FXML
    private TableColumn<Message, String> subjectColoumnId;

    public void initialize(){
        loadTableItems();
    }

    public void loadTableItems(){
        priorityColoumnId.setCellValueFactory(cellData -> cellData.getValue().importanceOfMessageProperty());
        receivedColoumnId.setCellValueFactory(cellData -> cellData.getValue().receivedAtProperty());
        readColoumnId.setCellValueFactory(cellData -> cellData.getValue().readStatusProperty());
        senderColoumnId.setCellValueFactory(cellData -> cellData.getValue().senderProperty().get().nameProperty());
        subjectColoumnId.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
    }

    public void setMain(Main main) {
        this.main = main;
        tableViewId.setItems(main.getMessageData());
    }
}
