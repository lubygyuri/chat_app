package hu.alkfejl.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.alkfejl.config.Configuration;
import hu.alkfejl.model.ChatUser;
import hu.alkfejl.model.Gender;

import java.sql.*;
import java.util.*;

public class ChatUserDAOImpl implements ChatUserDAO {

    private static final String SELECT_ALL_USERS = "SELECT * FROM CHATUSER";
    private static final String DELETE_CHAT_USER = "DELETE FROM CHATUSER WHERE id=?";
    private static final String INSERT_USER = "INSERT INTO CHATUSER (nickname, password, age, gender, interest) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_CHATUSER_NICKNAME_BY_ID = "SELECT nickname FROM CHATUSER WHERE id = ?";
    private static final String SEARCH_BY_NICKNAME = "SELECT * FROM CHATUSER WHERE nickname LIKE ?";
    private static final String SEARCH_BY_INTEREST = "SELECT * FROM CHATUSER WHERE interest LIKE ?";
    private static final String SELECT_CHATUSER_BY_NICKNAME = "SELECT * FROM CHATUSER WHERE nickname = ?";
    private static ChatUserDAOImpl instance;
    private String connectionURL;

    public ChatUserDAOImpl(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionURL = Configuration.getValue("db.url");
    }

    public static ChatUserDAO getInstance() {
        if(instance == null){
            instance = new ChatUserDAOImpl();
        }
        return instance;
    }

    @Override
    public ChatUser findUserByNickname(String nickname) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SELECT_CHATUSER_BY_NICKNAME)
        ) {

            pst.setString(1, nickname);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                ChatUser chatUser = new ChatUser();
                chatUser.setId(rs.getInt("id"));

                return chatUser;
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return null;
    }

    @Override
    public String findChatUserNameById(int chatUserId) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SELECT_CHATUSER_NICKNAME_BY_ID)
        ) {

            pst.setInt(1, chatUserId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("nickname");
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public List<ChatUser> findAll() {

        List<ChatUser> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS);
        ) {

            while (rs.next()) {
                ChatUser user = new ChatUser();
                user.setId(rs.getInt("id"));
                user.setNickname(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                user.setAge(rs.getInt("age"));

                int ordinalValue = rs.getInt("gender");
                Optional<Gender> gd = Arrays.stream(Gender.values()).filter(gender -> gender.ordinal() == ordinalValue).findAny();
                user.setGender(gd.orElse(Gender.UNKNOWN));

                user.setInterest(rs.getString("interest"));

                users.add(user);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return users;

    }

    @Override
    public ChatUser save(ChatUser chatUser) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)
        ) {
            pst.setString(1, chatUser.getNickname());

            String hashedPwd = BCrypt.withDefaults().hashToString(12, chatUser.getPassword().toCharArray());
            pst.setString(2, hashedPwd);

            pst.setInt(3, chatUser.getAge());
            pst.setInt(4, chatUser.getGender().ordinal());
            pst.setString(5, chatUser.getInterest());


            int affectedRows = pst.executeUpdate();
            if(affectedRows == 0){
                return null;
            }

            if(chatUser.getId() <= 0){ // INSERT
                ResultSet genKeys = pst.getGeneratedKeys();
                if(genKeys.next()){
                    chatUser.setId(genKeys.getInt(1));
                }
            }

            chatUser.setPassword("");

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return chatUser;
    }

    @Override
    public ChatUser login(String nickname, String password) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SELECT_CHATUSER_BY_NICKNAME)
        ) {
            pst.setString(1, nickname);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                String dbPass = rs.getString("password");

                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), dbPass);
                if(result.verified){
                    ChatUser chatUser = new ChatUser();
                    chatUser.setId(rs.getInt("id"));
                    chatUser.setNickname(rs.getString("nickname"));
                    chatUser.setPassword(rs.getString("password"));
                    chatUser.setAge(rs.getInt("age"));
                    int ordinalValue = rs.getInt("gender");
                    Optional<Gender> gd = Arrays.stream(Gender.values()).filter(gender -> gender.ordinal() == ordinalValue).findAny();
                    chatUser.setGender(gd.orElse(Gender.UNKNOWN));
                    chatUser.setInterest(rs.getString("interest"));

                    return chatUser;
                }
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return null;
    }

    @Override
    public void deleteChatUser(ChatUser chatUser) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_CHAT_USER);
        ) {

            stmt.setInt(1, chatUser.getId());
            stmt.executeUpdate();

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }
    }

    @Override
    public List<ChatUser> searchByNickname(String nickname) {
        List<ChatUser> searchResult = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SEARCH_BY_NICKNAME);
        ) {

            pst.setString(1, nickname);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ChatUser user = new ChatUser();
                user.setId(rs.getInt("id"));
                user.setNickname(rs.getString("nickname"));
                user.setAge(rs.getInt("age"));

                int ordinalValue = rs.getInt("gender");
                Optional<Gender> gd = Arrays.stream(Gender.values()).filter(gender -> gender.ordinal() == ordinalValue).findAny();
                user.setGender(gd.orElse(Gender.UNKNOWN));

                user.setInterest(rs.getString("interest"));

                searchResult.add(user);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return searchResult;
    }

    @Override
    public List<ChatUser> searchByInterest(String interest) {
        List<ChatUser> searchResult = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement(SEARCH_BY_INTEREST);
        ) {

            pst.setString(1, interest);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ChatUser user = new ChatUser();
                user.setId(rs.getInt("id"));
                user.setNickname(rs.getString("nickname"));
                user.setAge(rs.getInt("age"));

                int ordinalValue = rs.getInt("gender");
                Optional<Gender> gd = Arrays.stream(Gender.values()).filter(gender -> gender.ordinal() == ordinalValue).findAny();
                user.setGender(gd.orElse(Gender.UNKNOWN));

                user.setInterest(rs.getString("interest"));

                searchResult.add(user);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return searchResult;
    }
}
