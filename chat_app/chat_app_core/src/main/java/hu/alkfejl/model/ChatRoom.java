package hu.alkfejl.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;


public class ChatRoom {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty roomName = new SimpleStringProperty(this, "roomName");
    private ObjectProperty<ObservableList<Rule>> rules = new SimpleObjectProperty<>(this, "rules");
    private StringProperty category = new SimpleStringProperty(this, "category");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getRoomName() {
        return roomName.get();
    }

    public StringProperty roomNameProperty() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }

    public ObservableList<Rule> getRules() {
        return rules.get();
    }

    public ObjectProperty<ObservableList<Rule>> rulesProperty() {
        return rules;
    }

    public void setRules(ObservableList<Rule> rules) {
        this.rules.set(rules);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }
}
