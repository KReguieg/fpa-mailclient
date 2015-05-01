package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Message;
import model.MessageImportance;
import model.MessageStakeholder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by Khaled_000 on 14.04.2015.
 */
public class ContentController {

    private ObservableList<Message> messageData = FXCollections.observableArrayList();

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void initialize() {
        System.out.println("Initialize Controller...");
        createExampleMessages();
        loadTableItems();
        tableViewId.setItems(getMessageData());
        System.out.println("Controller initialized...");
    }

    public void loadTableItems() {
        priorityColoumnId.setCellValueFactory(cellData -> cellData.getValue().importanceOfMessageProperty());
        priorityColoumnId.setCellFactory(cellData -> new TableCell<Message, MessageImportance>() {
            @Override
            protected void updateItem(MessageImportance item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    ImageView priorityIconView = null;
                    if (item == MessageImportance.HIGH) {
                        priorityIconView = new ImageView(new Image("res/ic_call_made_black_18dp.png"));
                    } else if (item == MessageImportance.NORMAL) {
                        priorityIconView = new ImageView(new Image("res/ic_remove_black_18dp.png"));
                    } else if (item == MessageImportance.LOW) {
                        priorityIconView = new ImageView(new Image("res/ic_call_received_black_18dp.png"));
                    }
                    setGraphic(priorityIconView);
                }
            }
        });
        receivedColoumnId.setCellValueFactory(cellData -> cellData.getValue().receivedAtProperty());
        receivedColoumnId.setCellFactory(cellData -> new TableCell<Message, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                }
                else setText(formatter.format(item));
            }
        });
        readColoumnId.setCellValueFactory(cellData -> cellData.getValue().readStatusProperty());
        readColoumnId.setCellFactory(cellData -> new TableCell<Message, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    ImageView messageReadImageView = null;
                    if(item) messageReadImageView = new ImageView(new Image("res/ic_drafts_black_18dp.png"));
                    else messageReadImageView = new ImageView((new Image("res/ic_mail_black_18dp.png")));
                    setGraphic(messageReadImageView);
                }
            }
        });
        senderColoumnId.setCellValueFactory(cellData -> cellData.getValue().senderProperty().get().nameProperty());
        subjectColoumnId.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
    }

    /**
     * This method creates the 1st example Message, by setting the values
     * of the message instance over the setter methods
     */
    public void createExampleMessage1() {
        Message m1 = new Message();
        m1.setId("1");
        m1.setImportanceOfMessage(MessageImportance.LOW);
        m1.setReadStatus(true);
        System.out.println(ZoneId.systemDefault());
        System.out.println(Clock.system(ZoneId.systemDefault()));
        m1.setReceivedAt(LocalDateTime.now());
        m1.setSubject("Omg Zeig das an!");
        m1.setText("BLABLABLABLA");
        MessageStakeholder ms1 = new MessageStakeholder();
        ms1.setName("Khaled Reguieg");
        ms1.setMailAddress("Khaled.Reguieg@gmail.com");
        m1.setSender(ms1);
        messageData.add(m1);
    }

    /**
     * This method creates the 2nd example Message, by setting the values
     * of the message instance over the setter methods
     */
    public void createExampleMessage2() {
        Message m2 = new Message();
        m2.setId("2");
        m2.setImportanceOfMessage(MessageImportance.HIGH);
        m2.setReadStatus(true);
        m2.setReceivedAt(LocalDateTime.now());
        m2.setSubject("Omg Zeig das an!");
        m2.setText("YAAAAAAAAAAAAAAAAAAAAAAAAY");
        MessageStakeholder ms1 = new MessageStakeholder();
        ms1.setName("Khaled Reguieg");
        ms1.setMailAddress("Khaled.Reguieg@gmail.com");
        m2.setSender(ms1);
        messageData.add(m2);
    }

    /**
     * This method calls ALL createExampleMessage methods
     */
    public void createExampleMessages() {
        createExampleMessage1();
        createExampleMessage2();
    }

    /**
     * Returns the observableList object
     * @return ObservableList<Message>
     */
    public ObservableList<Message> getMessageData() {
        return messageData;
    }
}
