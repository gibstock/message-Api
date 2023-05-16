package com.gibstock.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gibstock.Model.Message;
import com.gibstock.Util.ConnectionUtil;

public class MessageDAOImpl implements MessageDAO {

  private Connection con;

  public MessageDAOImpl() {
    con = ConnectionUtil.getConnection();
  }

  @Override
  public Message addNewMessage(Message message) {
    Message addedMessage = null;
    try {
      String sql = "INSERT INTO message (posted_by, message_text) VALUES (?,?)";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setInt(1, message.getPosted_by());
      ps.setString(2, message.getMessage_text());

      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();

      while (rs.next()) {
        int generatedId = rs.getInt(1);
        addedMessage = new Message(
            generatedId,
            message.getPosted_by(),
            message.getMessage_text());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return addedMessage;
  }

  @Override
  public Message updateMessage(Message message) {
    Message updatedMessage = null;
    try {
      String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setString(1, message.getMessage_text());
      ps.setInt(2, message.getMessage_id());
      int affectedRows = ps.executeUpdate();
      if (affectedRows > 0) {
        updatedMessage = new Message(message.getMessage_id(), message.getPosted_by(), message.getMessage_text());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return updatedMessage;
  }

  @Override
  public Message getMessageById(int message_id) {
    try {
      String sql = "SELECT * FROM message WHERE message_id = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, message_id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new Message(rs.getInt(1), rs.getInt("posted_by"), rs.getString("message_text"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Message> getAllMessages() {
    List<Message> allMessages = new ArrayList<>();
    try {
      String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message";
      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        allMessages.add(new Message(
            rs.getInt("message_id"),
            rs.getInt("posted_by"),
            rs.getString("message_text"),
            rs.getTimestamp("time_posted_epoch")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allMessages;
  }

  @Override
  public List<Message> getAllMessagesByUser(int posted_by) {
    List<Message> allMessages = new ArrayList<>();
    try {
      String sql = "SELECT * FROM message WHERE posted_by = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, posted_by);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        allMessages.add(
            new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getTimestamp(4)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allMessages;
  }

  @Override
  public boolean delete(int message_id) {
    boolean deleted = false;
    try {
      String sql = "DELETE FROM message WHERE message_id = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, message_id);
      int affectedRows = ps.executeUpdate();
      if (affectedRows > 0) {
        deleted = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return deleted;
  }

}
