package com.client;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static DataInputStream inMessage;
    // исходящее сообщение
    static DataOutputStream outMessage;

    public static void main(String[] args) {
        try {

            //outMessage = new PrintWriter();
            Socket client = new Socket("127.0.0.1",8081);
            inMessage = new DataInputStream(client.getInputStream());
            outMessage = new DataOutputStream(client.getOutputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));


            while(!client.isClosed()){

                outMessage.writeUTF("1+1");

                String ret = inMessage.readUTF();
                System.out.println(ret);
            }

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
