package hu.alkfejl.dao;

import hu.alkfejl.model.ChatRoom;

import java.util.List;

public interface ChatRoomDAO {

    ChatRoom findRoomById(int roomId);

    List<ChatRoom> findAll();

    ChatRoom save(ChatRoom chatRoom);

    void deleteChatRoom(ChatRoom chatRoom);

    List<ChatRoom> searchByRoomName(String roomName);

    List<ChatRoom> searchByCategory(String category);
}
