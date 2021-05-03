package hu.alkfejl.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public enum Gender {

    MALE("Férfi"),
    FEMALE("Nő"),
    OTHER("Egyéb"),
    UNKNOWN("Ismeretlen");

    private final StringProperty value = new SimpleStringProperty(this, "value");

    Gender(String value) {
        this.value.set(value);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

}
