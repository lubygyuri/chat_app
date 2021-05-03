package hu.alkfejl.dao;

import hu.alkfejl.model.ChatRoom;
import hu.alkfejl.model.Rule;

import java.util.List;

public interface RuleDAO {

    List<Rule> findAllRules(ChatRoom chatRoom);

    List<Rule> findAllRules(int chatRoomId);

    Rule save(Rule rule, int chatRoomId);

    void delete(Rule rule);

    void deleteAll(int chatRoomId);
}
