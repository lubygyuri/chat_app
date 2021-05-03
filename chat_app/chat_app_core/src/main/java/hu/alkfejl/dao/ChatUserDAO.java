package hu.alkfejl.dao;

import hu.alkfejl.model.ChatUser;

import java.util.List;

public interface ChatUserDAO {

    List<ChatUser> findAll();

    ChatUser findUserByNickname(String nickname);

    ChatUser save(ChatUser chatUser);

    ChatUser login(String nickname, String password);

    void deleteChatUser(ChatUser chatUser);

    String findChatUserNameById(int chatUserId);

    List<ChatUser> searchByNickname(String nickname);

    List<ChatUser> searchByInterest(String interest);
}
