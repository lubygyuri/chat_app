package hu.alkfejl.dao;

import hu.alkfejl.config.Configuration;
import hu.alkfejl.model.ChatRoom;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.*;

public class ChatRoomDAOImpl implements ChatRoomDAO {

    private static final String SELECT_ALL_ROOMS = "SELECT * FROM CHATROOM";
    private static final String DELETE_CHAT_ROOM = "DELETE FROM CHATROOM WHERE id=?";
    private static final String INSERT_CHAT_ROOM = "INSERT INTO CHATROOM (roomName, category) VALUES (?, ?)";
    private static final String UPDATE_CHAT_ROOM = "UPDATE CHATROOM SET roomName = ?, category = ? WHERE id = ?";
    private static final String SELECT_ROOM_BY_ID = "SELECT * FROM CHATROOM WHERE id = ?";
    private static final String SEARCH_BY_ROOM_NAME = "SELECT * FROM CHATROOM WHERE roomName LIKE ?";
    private static final String SEARCH_BY_CATEGORY = "SELECT * FROM CHATROOM WHERE category LIKE ?";
    private static ChatRoomDAOImpl instance;
    private RuleDAO ruleDAO = new RuleDAOImpl();
    private String connectionURL;

    public ChatRoomDAOImpl(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionURL = Configuration.getValue("db.url");
    }

    public static ChatRoomDAO getInstance() {
        if(instance == null){
            instance = new ChatRoomDAOImpl();
        }
        return instance;
    }

    @Override
    public ChatRoom findRoomById(int roomId) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SELECT_ROOM_BY_ID)
        ) {

            pst.setInt(1, roomId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setId(rs.getInt("id"));
                chatRoom.setRoomName(rs.getString("roomName"));
                chatRoom.setCategory(rs.getString("category"));
                chatRoom.setRules(FXCollections.observableArrayList(ruleDAO.findAllRules(chatRoom.getId())));

                return chatRoom;
            }


        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public List<ChatRoom> findAll() {
        List<ChatRoom> rooms = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_ROOMS);
        ) {

            while (rs.next()) {
                ChatRoom room = new ChatRoom();
                room.setId(rs.getInt("id"));
                room.setRoomName(rs.getString("roomName"));
                room.setCategory(rs.getString("category"));

                rooms.add(room);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return rooms;
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = chatRoom.getId() <= 0 ?
                     conn.prepareStatement(INSERT_CHAT_ROOM, Statement.RETURN_GENERATED_KEYS) :
                     conn.prepareStatement(UPDATE_CHAT_ROOM)
        ) {

            if (chatRoom.getId() > 0) { // UPDATE
                stmt.setInt(3, chatRoom.getId());
            }

            stmt.setString(1, chatRoom.getRoomName());
            stmt.setString(2, chatRoom.getCategory());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Nem sikerült adatbázisba történő beszúrás!");
                return null;
            }

            if (chatRoom.getId() <= 0) { // INSERT
                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys.next()) {
                    chatRoom.setId(genKeys.getInt(1));
                }
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
            return null;
        }

        return chatRoom;
    }

    @Override
    public void deleteChatRoom(ChatRoom chatRoom) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_CHAT_ROOM);
        ) {

            stmt.setInt(1, chatRoom.getId());
            stmt.executeUpdate();

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }
    }

    @Override
    public List<ChatRoom> searchByRoomName(String roomName) {
        List<ChatRoom> searchResult = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SEARCH_BY_ROOM_NAME);
        ) {

            pst.setString(1, roomName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setId(rs.getInt("id"));
                chatRoom.setRoomName(rs.getString("roomName"));
                chatRoom.setCategory(rs.getString("category"));

                searchResult.add(chatRoom);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return searchResult;
    }

    @Override
    public List<ChatRoom> searchByCategory(String category) {
        List<ChatRoom> searchResult = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SEARCH_BY_CATEGORY);
        ) {

            pst.setString(1, category);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setId(rs.getInt("id"));
                chatRoom.setRoomName(rs.getString("roomName"));
                chatRoom.setCategory(rs.getString("category"));

                searchResult.add(chatRoom);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return searchResult;
    }
}
