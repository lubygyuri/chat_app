package hu.alkfejl.controller;

import hu.alkfejl.model.ChatRoom;
import hu.alkfejl.model.Rule;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddRuleController {
    private Stage stage;
    private Rule rule;
    private ChatRoom chatRoom;

    @FXML
    private TextArea ruleArea;

    public void init(Stage stage, Rule rule, ChatRoom chatRoom) {
        this.stage = stage;
        this.rule = rule;
        this.chatRoom = chatRoom;

        ruleArea.textProperty().bindBidirectional(this.rule.ruleProperty());
    }

    @FXML
    public void onCancel() {
        stage.close();
    }

    @FXML
    public void onSave() {
        chatRoom.getRules().remove(rule);
        chatRoom.getRules().add(rule);
        stage.close();
    }

}
