package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * Created by Khaled Reguieg on 30.06.2015.
 */
public class FPAMessageLoaderImpl implements FPAMessageLoader{

    /**
     * Returns all .xml files from the given path as an Array filled with files.
     *
     * @return File[]
     */
    public File[] loadFiles(File rootPath) {
        final String extension = ".xml";
        final File currentDir = rootPath;
        System.out.println("CurrentDir: " + rootPath.getAbsolutePath());
        return currentDir.listFiles((File pathname) -> pathname.getName().endsWith(extension));
    }

    /**
     * This method creates Message objects out of XML-files.
     */
    public ObservableList<Message> createExampleMessages(File rootPath) {
        File[] files = loadFiles(rootPath);
        ObservableList<Message> messageData = FXCollections.observableArrayList();
        if( files.length == 0) {
            return messageData;
        }
        System.out.println(files[0]);
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
        return messageData;
    }

    @Override
    public List<Message> getMessages(File path) {
        return createExampleMessages(path);
    }
}
