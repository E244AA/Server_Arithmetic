package com.chat;

import java.io.IOException;

public class Main {

    private static Server server;

    public static void main(String[] args) {

        server = new Server();

        try {

            server.startServer();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}