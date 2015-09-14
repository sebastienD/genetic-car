package fr.genetic.server;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by sebastien on 10/01/2015.
 */
public class Client {

    @Test
    @Ignore
    public void should_connect() throws Exception {
        Socket socket = new Socket("localhost", 9090);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println(in.readLine() + "\n");

        out.println("je suis red");


    }

}
