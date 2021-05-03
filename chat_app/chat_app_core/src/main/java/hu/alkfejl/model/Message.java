package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Message {
    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty message = new SimpleStringProperty(this, "message");
    private IntegerProperty chatUserId = new SimpleIntegerProperty(this, "chatUserId");
    private IntegerProperty chatRoomId = new SimpleIntegerProperty(this, "chatRoomId");
    private StringProperty chatUserName = new SimpleStringProperty(this, "chatUserName");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public int getChatUserId() {
        return chatUserId.get();
    }

    public IntegerProperty chatUserIdProperty() {
        return chatUserId;
    }

    public void setChatUserId(int chatUserId) {
        this.chatUserId.set(chatUserId);
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

    public String getChatUserName() {
        return chatUserName.get();
    }

    public StringProperty chatUserNameProperty() {
        return chatUserName;
    }

    public void setChatUserName(String chatUserName) {
        this.chatUserName.set(chatUserName);
    }
}
