package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rule {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty rule = new SimpleStringProperty(this, "rule");
    private IntegerProperty chatRoomId = new SimpleIntegerProperty(this, "chatRoomId");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getRule() {
        return rule.get();
    }

    public StringProperty ruleProperty() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule.set(rule);
    }

    public int getChatRoomId() {
        return chatRoomId.get();
    }

    public IntegerProperty chatRoomIdProperty() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId.set(chatRoomId);
    }

    @Override
    public String toString() {
        return rule.getValue();
    }
}
