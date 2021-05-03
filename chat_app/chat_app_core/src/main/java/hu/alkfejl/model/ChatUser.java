package hu.alkfejl.model;

import javafx.beans.property.*;

public class ChatUser {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty nickname = new SimpleStringProperty(this, "nickname");
    private StringProperty password = new SimpleStringProperty(this, "password");
    private ObjectProperty<Gender> gender = new SimpleObjectProperty<>(this,"gender");
    private IntegerProperty age = new SimpleIntegerProperty(this, "age");
    private StringProperty interest = new SimpleStringProperty(this, "interest");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNickname() {
        return nickname.get();
    }

    public StringProperty nicknameProperty() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Gender getGender() {
        return gender.get();
    }

    public ObjectProperty<Gender> genderProperty() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender.set(gender);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getInterest() {
        return interest.get();
    }

    public StringProperty interestProperty() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest.set(interest);
    }
}
