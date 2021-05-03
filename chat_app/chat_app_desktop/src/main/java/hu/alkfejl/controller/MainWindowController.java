package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.ChatRoomDAO;
import hu.alkfejl.dao.ChatRoomDAOImpl;
import hu.alkfejl.dao.ChatUserDAO;
import hu.alkfejl.dao.ChatUserDAOImpl;
import hu.alkfejl.model.ChatRoom;
import hu.alkfejl.model.ChatUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    ChatUserDAO chatUserDAO = new ChatUserDAOImpl();
    ChatRoomDAO chatRoomDAO = new ChatRoomDAOImpl();

    @FXML
    private TableView<ChatUser> chatUserTable;

    @FXML
    private TableColumn<ChatUser, String> nicknameColumn;

    @FXML
    private TableColumn<ChatUser, String> ageColumn;

    @FXML
    private TableColumn<ChatUser, String> genderColumn;

    @FXML
    private TableColumn<ChatUser, String> interestColumn;

    @FXML
    private TableColumn<ChatUser, Void> chatUserActionsColumn;

    @FXML
    private TableView<ChatRoom> chatRoomTable;

    @FXML
    private TableColumn<ChatRoom, String> roomNameColumn;

    @FXML
    private TableColumn<ChatRoom, String> roomCategoryColumn;

    @FXML
    private TableColumn<ChatRoom, Void> chatRoomActionsColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshUsersTable();
        refreshRoomsTable();

        // ChatUsers Block
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interest"));

        chatUserActionsColumn.setCellFactory(param -> new TableCell<>(){
            private final Button deleteButton = new Button("Törlés");

            {
                deleteButton.setOnAction(param -> {
                    ChatUser cu = getTableRow().getItem();
                    deleteChatUser(cu);
                    refreshUsersTable();
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(deleteButton);
                    container.setAlignment(Pos.CENTER);
                    setGraphic(container);
                }
            }
        });

        // ChatRooms Block
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        roomCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        chatRoomActionsColumn.setCellFactory(param -> new TableCell<>(){
            private final Button editButton = new Button("Szerkesztés");
            private final Button deleteButton = new Button("Törlés");

            {
                deleteButton.setOnAction(param -> {
                    ChatRoom cr = getTableRow().getItem();
                    deleteChatRoom(cr);
                    refreshRoomsTable();
                });

                editButton.setOnAction(param -> {
                    ChatRoom cr = getTableRow().getItem();
                    editChatRoom(cr);
                    refreshRoomsTable();
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(editButton, deleteButton);
                    container.setAlignment(Pos.CENTER);
                    container.setSpacing(10);
                    setGraphic(container);
                }
            }
        });

    }

    @FXML
    public void onAddNewRoom() {
        FXMLLoader loader = App.loadFXML("/fxml/edit_room.fxml");
        EditRoomController controller = loader.getController();
        controller.setChatRoom(new ChatRoom());
    }

    @FXML
    public void onExit() {
        Platform.exit();
    }

    private void refreshUsersTable() {
        chatUserTable.getItems().setAll(chatUserDAO.findAll());
    }

    private void refreshRoomsTable() {
        chatRoomTable.getItems().setAll(chatRoomDAO.findAll());
    }

    private void deleteChatUser(ChatUser c) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Biztos törölni szeretnéd " + c.getNickname()  + " felhasználót?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                chatUserDAO.deleteChatUser(c);
            }
        });
    }

    private void editChatRoom(ChatRoom cr) {
        FXMLLoader loader = App.loadFXML("/fxml/edit_room.fxml");
        EditRoomController controller = loader.getController();
        controller.setChatRoom(cr);
    }

    private void deleteChatRoom(ChatRoom cr) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Biztos törölni szeretnéd a(z) " + cr.getRoomName()  + " nevű szobát?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                chatRoomDAO.deleteChatRoom(cr);
            }
        });
    }
}
