package hu.alkfejl.dao;

import hu.alkfejl.config.Configuration;
import hu.alkfejl.model.ChatRoom;
import hu.alkfejl.model.Rule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RuleDAOImpl implements RuleDAO {

    private static final String SELECT_RULES_BY_CHATROOM_ID = "SELECT * FROM RULE WHERE chatRoomId = ?";
    private static final String INSERT_RULE = "INSERT INTO RULE (rule, chatRoomId) VALUES (?, ?)";
    private static final String UPDATE_RULE = "UPDATE RULE SET rule = ? WHERE id = ?";
    private static final String DELETE_RULE = "DELETE FROM RULE WHERE id = ?";
    private String connectionURL;

    public RuleDAOImpl() {
        this.connectionURL = Configuration.getValue("db.url");
    }

    @Override
    public List<Rule> findAllRules(ChatRoom chatRoom) {
        return findAllRules(chatRoom.getId());
    }

    @Override
    public List<Rule> findAllRules(int chatRoomId) {
        List<Rule> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = conn.prepareStatement(SELECT_RULES_BY_CHATROOM_ID)
        ) {

            stmt.setInt(1, chatRoomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rule rule = new Rule();
                rule.setId(rs.getInt("id"));
                rule.setRule(rs.getString("rule"));

                result.add(rule);
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }

        return result;
    }

    @Override
    public Rule save(Rule rule, int chatRoomId) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = rule.getId() <= 0 ?
                    conn.prepareStatement(INSERT_RULE, Statement.RETURN_GENERATED_KEYS) :
                    conn.prepareStatement(UPDATE_RULE)
        ) {

            if (rule.getId() > 0) { // UPDATE
                stmt.setInt(2, rule.getId());
            } else { // INSERT
                stmt.setInt(2, chatRoomId);
            }

            stmt.setString(1, rule.getRule());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Nem sikerült az adatbázisba történő beszúrás!");
                return null;
            }

            if (rule.getId() <= 0) { // INSERT
                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys.next()) {
                    rule.setId(genKeys.getInt(1));
                }
            }

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
            return null;
        }

        return rule;
    }

    @Override
    public void delete(Rule rule) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_RULE);
        ) {

            stmt.setInt(1, rule.getId());
            stmt.executeUpdate();

        } catch (SQLException exception) {
            System.err.println("Nem sikerült kapcsolódni az adatbázishoz: " + exception.getMessage());
        }
    }

    @Override
    public void deleteAll(int chatRoomId) {
        findAllRules(chatRoomId).forEach(this::delete);
    }
}
