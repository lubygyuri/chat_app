package hu.alkfejl.dao;

import hu.alkfejl.config.Configuration;
import hu.alkfejl.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    private static final String SELECT_MESSAGES_BY_CHATROOM_ID = "SELECT * FROM MESSAGES WHERE chatRoomId = ?";
    private static final String INSERT_MESSAGE = "INSERT INTO MESSAGES (message, chatUserId, chatRoomId) VALUES (?, ?, ?)";
    private static final String DB_CONN_STR = Configuration.getValue("db.url");
    private ChatUserDAO chatUserDAO = ChatUserDAOImpl.getInstance();
    private static MessageDAOImpl instance;

    public static MessageDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new MessageDAOImpl();
        }
        return instance;
    }

    private MessageDAOImpl() {
    }

    @Override
    public List<Message> findAllMessagesByRoom(int chatRoomId) {
        List<Message> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_CONN_STR);
             PreparedStatement pst = conn.prepareStatement(SELECT_MESSAGES_BY_CHATROOM_ID)
        ) {

            pst.setInt(1, chatRoomId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setMessage(rs.getString("message"));
                message.setChatUserId(rs.getInt("chatUserId"));
                message.setChatRoomId(rs.getInt("chatRoomId"));
                message.setChatUserName(chatUserDAO.findChatUserNameById(message.getChatUserId()));

                result.add(message);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return result;
    }

    @Override
    public Message save(Message message) {
        try (Connection conn = DriverManager.getConnection(DB_CONN_STR);
             PreparedStatement pst = conn.prepareStatement(INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS)
        ) {
            pst.setString(1, message.getMessage());
            pst.setInt(2, message.getChatUserId());
            pst.setInt(3, message.getChatRoomId());

            int affectedRows = pst.executeUpdate();
            if(affectedRows == 0){
                System.err.println("Nem sikerült az adatbázisba történő beszúrás!");
                return null;
            }

            if(message.getId() <= 0){ // INSERT
                ResultSet genKeys = pst.getGeneratedKeys();
                if(genKeys.next()){
                    message.setId(genKeys.getInt(1));
                }
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return message;
    }
}
