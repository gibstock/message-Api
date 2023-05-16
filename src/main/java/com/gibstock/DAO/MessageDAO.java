package com.gibstock.DAO;

import java.util.List;

import com.gibstock.Model.Message;

public interface MessageDAO {
  Message addNewMessage(Message message);

  Message updateMessage(Message message);

  Message getMessageById(int message_id);

  List<Message> getAllMessages();

  List<Message> getAllMessagesByUser(int posted_by);

  boolean delete(int message_id);
}
