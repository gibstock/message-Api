package com.gibstock.Service;

import java.util.ArrayList;
import java.util.List;

import com.gibstock.DAO.MessageDAO;
import com.gibstock.DAO.MessageDAOImpl;
import com.gibstock.Model.Message;

public class MessageServiceImpl implements MessageService {

  private MessageDAO messageDAO;

  public MessageServiceImpl() {
    messageDAO = new MessageDAOImpl();
  }

  @Override
  public List<Message> getAllMessages() {
    return messageDAO.getAllMessages();
  }

  @Override
  public Message addNewMessage(Message message) {
    if (message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255) {
      return messageDAO.addNewMessage(message);
    }
    return null;
  }

  @Override
  public Message getMessageById(int message_id) {
    if (messageDAO.getMessageById(message_id) != null) {
      return messageDAO.getMessageById(message_id);
    }
    return null;
  }

  @Override
  public Message updateMessage(Message message) {
    if (message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255
        && messageDAO.getMessageById(message.getMessage_id()) != null) {
      return messageDAO.updateMessage(message);
    }
    return null;
  }

  @Override
  public Message deleteMessage(int message_id) {
    Message deletedMessage = null;
    Message messageToDelete = messageDAO.getMessageById(message_id);
    if (messageToDelete != null) {
      deletedMessage = messageDAO.getMessageById(message_id);
      messageDAO.delete(message_id);
    }
    return deletedMessage;
  }

  @Override
  public List<Message> getAllMessagesByUser(int posted_by) {
    List<Message> userMessages = new ArrayList<>();
    if (messageDAO.getAllMessagesByUser(posted_by) != null) {
      userMessages = messageDAO.getAllMessagesByUser(posted_by);
      return userMessages;
    }
    return null;
  }

}
