package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.ChatRoomDAO;
import hu.alkfejl.dao.ChatRoomDAOImpl;
import hu.alkfejl.dao.RuleDAO;
import hu.alkfejl.dao.RuleDAOImpl;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import hu.alkfejl.model.ChatRoom;
import hu.alkfejl.model.Rule;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class EditRoomController implements Initializable {
    private ChatRoom chatRoom;
    private ChatRoomDAO chatRoomDAO = new ChatRoomDAOImpl();
    private RuleDAO ruleDAO = new RuleDAOImpl();

    @FXML
    private TextField chatRoomName;

    @FXML
    ListView<Rule> chatRoomRules;

    @FXML
    private TextField chatRoomCategory;

    public void setChatRoom(ChatRoom cr) {
        this.chatRoom = cr;

        List<Rule> rulesList = ruleDAO.findAllRules(cr.getId());
        chatRoom.setRules(FXCollections.observableArrayList(rulesList));

        chatRoomName.textProperty().bindBidirectional(chatRoom.roomNameProperty());
        chatRoomRules.itemsProperty().bindBidirectional(chatRoom.rulesProperty());
        chatRoomCategory.textProperty().bindBidirectional(chatRoom.categoryProperty());
    }

    @FXML
    public void onCancel() {
        App.loadFXML("/fxml/main_window.fxml");
    }

    @FXML
    public void onSave() {
        chatRoom = chatRoomDAO.save(chatRoom);
        ruleDAO.deleteAll(chatRoom.getId());
        chatRoom.getRules().forEach(rule -> {
            rule.setId(0);
            ruleDAO.save(rule, chatRoom.getId());
        });
        App.loadFXML("/fxml/main_window.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatRoomRules.setCellFactory(param -> {
            ListCell<Rule> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Szerkesztés");
            MenuItem deleteItem = new MenuItem("Törlés");

            contextMenu.getItems().addAll(editItem, deleteItem);

            editItem.setOnAction(event -> {
                Rule item = cell.getItem();
                showRuleDialog(item);
            });

            deleteItem.setOnAction(event -> {
                chatRoom.getRules().remove(cell.getItem());
            });

            StringBinding cellTextBinding = new When(cell.itemProperty().isNotNull())
                    .then(cell.itemProperty().asString())
                    .otherwise("");
            cell.textProperty().bind(cellTextBinding);

            cell.emptyProperty().addListener((observableValue, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            return cell;
        });
    }

    @FXML
    public void addNewRule() {
        showRuleDialog();
    }

    private void showRuleDialog(Rule rule) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_rule.fxml"));

        try {
            Parent root = loader.load();
            AddRuleController controller = loader.getController();
            controller.init(stage, rule, chatRoom);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRuleDialog() {
        showRuleDialog(new Rule());
    }
}
