package control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Message;
import model.MessageImportance;
import model.MessageStakeholder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Ibrahim Khaled Reguieg,
 * Khaled.Reguieg@gmail.com,
 * https://www.github.com/KReguieg
 * on 14.04.2015.
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

    @FXML
    private Label recipientsLbl;

    @FXML
    private Label subjectLbl;

    @FXML
    private Label dateLbl;

    @FXML
    private Label fromLbl;

    @FXML
    private TextArea mailContentTextArea;

    @FXML
    private MenuItem markAsUnreadContextMenu;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void initialize() {
        System.out.println("Initialize Controller...");
        createExampleMessages();
        loadTableItems();
        setOnClickListeners();
        tableViewId.setItems(getMessageData());
        System.out.println("Controller initialized...");
    }


    @FXML
    void markMessageAsUnread(ActionEvent event) {
        if(tableViewId.getSelectionModel().getSelectedItem() != null) {
            tableViewId.getSelectionModel().getSelectedItem().setReadStatus(false);
            try {
                saveMessage(tableViewId.getSelectionModel().getSelectedItem());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnClickListeners() {
        tableViewId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Message>() {
            @Override
            public void changed(ObservableValue<? extends Message> observable, Message oldValue, Message newValue) {
                String recipients = "";
                for (MessageStakeholder recipient : newValue.getRecipients()) {
                    recipients += recipient.getName() + " <\"" + recipient.getMailAddress() + "\">, ";
                }
                recipientsLbl.setText(recipients);
                fromLbl.setText(newValue.getSender().getName() + " <\"" + newValue.getSender().getMailAddress() + "\">");
                subjectLbl.setText(newValue.getSubject());
                dateLbl.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(newValue.getReceivedAt()));
                mailContentTextArea.setText(newValue.getText());
                newValue.setReadStatus(true);
                try {
                    saveMessage(tableViewId.getSelectionModel().getSelectedItem());
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });
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
                    priorityIconView.setFitHeight(15);
                    priorityIconView.setFitWidth(15);
                    setAlignment(Pos.CENTER);
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
                } else setText(formatter.format(item));
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
                    ImageView messageReadImageView;
                    if (item) messageReadImageView = new ImageView(new Image("res/ic_drafts_black_18dp.png"));
                    else messageReadImageView = new ImageView((new Image("res/ic_mail_black_18dp.png")));
                    setGraphic(messageReadImageView);
                    messageReadImageView.setFitHeight(15);
                    messageReadImageView.setFitWidth(15);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        senderColoumnId.setCellValueFactory(cellData -> cellData.getValue().senderProperty().get().nameProperty());
        subjectColoumnId.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
    }

    /**
     * Returns all .xml files from the given path as an Array filled with files.
     *
     * @return File[]
     */
    public File[] loadFiles() {
        final String extension = ".xml";
        final File currentDir = new File(".\\src\\mes");
        return currentDir.listFiles((File pathname) -> pathname.getName().endsWith(extension));
    }

    /**
     * This method creates Message objects out of XML-files.
     */
    public void createExampleMessages() {
        File[] files = loadFiles();
        for (File file : files) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Message msg = (Message) jaxbUnmarshaller.unmarshal(file);
                messageData.add(msg);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveMessage(Message msg) throws JAXBException{
        final File currentDir = new File(".\\src\\mes");
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(msg, new File(currentDir.getAbsolutePath() + "\\" + msg.getId() + ".xml"));
    }

    /**
     * Returns the observableList object
     *
     * @return ObservableList<Message>
     */
    public ObservableList<Message> getMessageData() {
        return messageData;
    }
}
