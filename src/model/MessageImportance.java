package model;

/**
 * Created by Benjamin Haupt on 03.04.15.
 */
public enum MessageImportance {

    LOW("low"),
    NORMAL("normal"),
    HIGH("high");

    private String importanceAsString;

    private MessageImportance(String importanceAsString) {
        this.importanceAsString = importanceAsString;

    }

    public String getImportanceAsString() {
        return importanceAsString;
    }
}
