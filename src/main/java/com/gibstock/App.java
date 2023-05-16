package com.gibstock;

import com.gibstock.Controller.MessageController;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) throws Exception {
        MessageController controller = new MessageController();

        Javalin app = controller.startApi();
        app.start(9000);

    }
}
