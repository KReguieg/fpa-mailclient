package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private final Node rootIcon = new ImageView(new Image("res/ic_inbox_black_18dp.png"));
    private final Node readIcon = new ImageView(new Image("res/ic_drafts_black_18dp.png"));
    private final Node favIcon = new ImageView(new Image("res/ic_favorite_black_18dp.png"));
    private final Node spamIcon = new ImageView(new Image("res/ic_warning_black_18dp.png"));
    private final Node trashIcon = new ImageView(new Image("res/ic_delete_black_18dp.png"));
    private final Node folderIcon = new ImageView(new Image("res/ic_folder_black_18dp.png"));
    /**
     * A formatter which creates a ofPattern Date in german format.
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private ObservableList<Message> messageData = FXCollections.observableArrayList();
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
    @FXML
    private TreeView<String> treeViewId;
    @FXML
    private ContextMenu treeViewContextMenu;
    @FXML
    private MenuItem contextFilterMessages;
    /**
     * Holds the root Path as {@link File}.
     */
    private File rootPath;
    /**
     * A observable of {@link FolderSelectionObservable}.
     */
    private FolderSelectionObservable observable;

    public Controller() {
        this.rootPath = new File("src/mes");
        System.out.println(rootPath.getAbsolutePath());
        observable = FolderSelectionObservable.getInstance();
        observable.addObserver(this);
    }

    @FXML
    public void onClickExit() {
        System.out.println("Exit application");
        System.exit(0);
    }

    @FXML
    public void onClickSetBasePath() {
        System.out.println("Set Base Path...");
    }

    @FXML
    public void onClickSetFilter() {
        System.out.println("Set Filter...");
    }

    @FXML
    public void onClickAbout() {
        System.out.println("Paper Jets v0.1");
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/about.fxml"));
            Stage aboutWindow = new Stage();
            aboutWindow.setTitle("About PaperJets");
            aboutWindow.setScene(new Scene(root, 300, 250));
            aboutWindow.getIcons().add(new Image("res/ic_send_black_48dp.png"));
            aboutWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        System.out.println("Initialize Controller...");
        messageData = createExampleMessages(rootPath);
        loadTableItems();
        setOnClickListeners();
        loadTreeItems();
        addChangeListenersToTreeView();
        tableViewId.setItems(getMessageData());
        System.out.println("Controller initialized...");
    }

    public ObservableList<Message> createExampleMessages(File rootPath) {
        FPAMessageLoaderImpl loader = new FPAMessageLoaderImpl();
        System.out.println("In create messages" + loader.createExampleMessages(rootPath));
        return (ObservableList<Message>) loader.createExampleMessages(rootPath);
    }

    @FXML
    void markMessageAsUnread(ActionEvent event) {
        if (tableViewId.getSelectionModel().getSelectedItem() != null) {
            tableViewId.getSelectionModel().getSelectedItem().setReadStatus(false);
            try {
                saveMessage(tableViewId.getSelectionModel().getSelectedItem());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void filterMessages(ActionEvent event) {
        System.out.println("FILTER MESSAGES NOW!!!");
        FPAMessageLoader fpaMessageLoader = new FPAMessageLoaderImpl();
        IsSubjectFilter isSubjectFilter = new IsSubjectFilter(fpaMessageLoader, "xing-profil");
        ContainsSenderMailFilter containsSenderMailFilter = new ContainsSenderMailFilter(isSubjectFilter, "beuth-hochschule.de");

        ObservableList<Message> filteredMessagesList = (ObservableList<Message>) containsSenderMailFilter.getMessages(rootPath);
        tableViewId.setItems(filteredMessagesList);
    }


    public void setOnClickListeners() {

        tableViewId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String recipients = "";
            if (newValue == null)
                return;
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
                    setGraphic(null);
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
                    setGraphic(null);
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
        final File currentDir = rootPath;
        return currentDir.listFiles((File pathname) -> pathname.getName().endsWith(extension));
    }

    /**
     * This method creates Message objects out of XML-files.
     */
//    public void createExampleMessages() {
//        File[] files = loadFiles();
//        for (File file : files) {
//            try {
//                JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
//                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//                Message msg = (Message) jaxbUnmarshaller.unmarshal(file);
//                messageData.add(msg);
//            } catch (JAXBException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public void saveMessage(Message msg) throws JAXBException {
        final File currentDir = rootPath;
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

    /**
     * This method loads all tree items.
     */
    public void loadTreeItems() {
        ImageView rootIcon = new ImageView(new Image("res/ic_folder_black_18dp.png"));
        rootIcon.setFitHeight(16);
        rootIcon.setFitWidth(16);
        FileSystemItem treeRoot;
        try {
            treeRoot = new Folder(this.rootPath, false);
            treeRoot.setExpanded(true);
            treeRoot.setGraphic(rootIcon);
            System.out.println(treeRoot.getChildren());
            treeViewId.setRoot(treeRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addChangeListenersToTreeView() {

        treeViewId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Folder selectedItem = (Folder) newValue;

            Controller.this.observable.newFolder(selectedItem.getFile());
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        File file = (File) arg;
        rootPath = file;
        tableViewId.getItems().clear();
        tableViewId.getItems().addAll(createExampleMessages(rootPath));
        clearLabels();
    }

    private void clearLabels() {
        recipientsLbl.setText("");
        subjectLbl.setText("");
        dateLbl.setText("");
        fromLbl.setText("");
        mailContentTextArea.setText("");
    }
}
