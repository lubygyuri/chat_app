package hu.alkfejl.dao;

import hu.alkfejl.model.Message;

import java.util.List;

public interface MessageDAO {

    List<Message> findAllMessagesByRoom(int chatRoomId);

    Message save(Message message);

}
