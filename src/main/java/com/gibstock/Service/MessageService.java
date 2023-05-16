package com.gibstock.Service;

import java.util.List;

import com.gibstock.Model.Message;

public interface MessageService {
  List<Message> getAllMessages();

  Message addNewMessage(Message message);

  Message getMessageById(int message_id);

  Message updateMessage(Message message);

  Message deleteMessage(int message_id);

  List<Message> getAllMessagesByUser(int posted_by);
}
