package com.gibstock.Controller;

import java.util.ArrayList;
import java.util.List;

// import java.util.ArrayList;
// import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibstock.Model.Account;
import com.gibstock.Model.Message;
import com.gibstock.Service.AccountService;
import com.gibstock.Service.AccountServiceImpl;
import com.gibstock.Service.MessageService;
import com.gibstock.Service.MessageServiceImpl;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class MessageController {
  private AccountService accounts;
  private MessageService messages;

  public MessageController() {
    accounts = new AccountServiceImpl();
    messages = new MessageServiceImpl();
  }

  public Javalin startApi() {
    Javalin app = Javalin.create();
    app.post("register", this::registerAccountHandler);
    app.post("login", this::loginHandler);

    app.get("messages", this::getAllMessagesHandler);
    app.get("messages/{message_id}", this::getMessageByIdHandler);
    app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
    app.post("messages", this::addMessageHandler);
    app.patch("messages/{message_id}", this::updateMessageHandler);
    app.delete("messages/{message_id}", this::deleteHandler);

    return app;
  }

  private void registerAccountHandler(Context ctx) {
    try {
      ObjectMapper om = new ObjectMapper();
      Account account = om.readValue(ctx.body(), Account.class);
      Account accountToAdd = accounts.addNewAccount(account);
      if (accountToAdd != null) {
        ctx.json(accountToAdd);
        ctx.status(200);
      } else {
        ctx.status(400);
      }
    } catch (JsonProcessingException e) {
      ctx.status(422);
    }
  }

  private void loginHandler(Context ctx) {
    try {
      ObjectMapper om = new ObjectMapper();
      Account account = om.readValue(ctx.body(), Account.class);
      Account accountExists = accounts.accountLogin(account.getUsername(), account.getPassword());
      if (accountExists != null) {
        ctx.json(accountExists);
        ctx.status(200);
      } else {
        ctx.status(400);
      }
    } catch (JsonProcessingException e) {
      ctx.status(422);
    }
  }

  private void getAllMessagesHandler(Context ctx) {
    ctx.json(messages.getAllMessages());
    ctx.status(200);
  }

  private void getAllMessagesByUserHandler(Context ctx) {
    List<Message> userMessages = new ArrayList<>();
    try {
      ObjectMapper om = new ObjectMapper();
      int postedBy = om.readValue(ctx.pathParam("account_id"), int.class);
      userMessages = messages.getAllMessagesByUser(postedBy);
      if (userMessages != null) {
        ctx.json(userMessages);
        ctx.status(200);
      } else {
        ctx.json("");
        ctx.status(200);
      }
    } catch (JsonProcessingException jpe) {
      ctx.status(422);
    }
  }

  private void getMessageByIdHandler(Context ctx) {
    try {
      ObjectMapper om = new ObjectMapper();
      int messageId = om.readValue(ctx.body(), int.class);
      Message retrievedMessage = messages.getMessageById(messageId);
      if (retrievedMessage != null) {
        ctx.json(retrievedMessage);
        ctx.status(200);
      } else {
        ctx.status(400);
      }
    } catch (JsonProcessingException jpe) {
      ctx.status(422);
    }
  }

  private void addMessageHandler(Context ctx) {
    try {
      ObjectMapper om = new ObjectMapper();
      Message newMessage = om.readValue(ctx.body(), Message.class);
      Message addedMessage = messages.addNewMessage(newMessage);
      if (addedMessage != null) {
        ctx.json(addedMessage);
        ctx.status(200);
      } else {
        ctx.status(400);
      }
    } catch (JsonProcessingException e) {
      ctx.status(422);
    }
  }

  private void updateMessageHandler(Context ctx) {
    try {
      ObjectMapper om = new ObjectMapper();
      Message newMessage = om.readValue(ctx.body(), Message.class);
      Message updatedMessage = messages.updateMessage(newMessage);
      if (updatedMessage != null) {
        ctx.json(updatedMessage);
        ctx.status(200);
      } else {
        ctx.status(400);
      }
    } catch (JsonProcessingException jpe) {
      ctx.status(422);
    }
  }

  private void deleteHandler(Context ctx) {
    try {
      ObjectMapper om = new ObjectMapper();
      int messageId = om.readValue(ctx.pathParam("message_id"), int.class);
      Message deletedMessage = messages.deleteMessage(messageId);
      if (deletedMessage != null) {
        ctx.json(deletedMessage);
        ctx.status(200);
      } else {
        ctx.status(400);
      }
    } catch (JsonProcessingException jpe) {
      ctx.status(422);
    }
  }
}
