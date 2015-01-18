package fr.seb.games.geneticcar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sebastien on 10/01/2015.
 */
public class ServeurSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServeurSocket.class);
    
      public static void mainMethod (String[] args) throws Exception {
            ServerSocket listener = new ServerSocket(9090);
           LOGGER.info("fr.seb.games.geneticcar.ServeurSocket is Running");
            try {
                  while (true) {
                        new Player(listener.accept(), "red").start();
                        new Player(listener.accept(), "blue").start();
                        new Player(listener.accept(), "green").start();
                        new Player(listener.accept(), "yellow").start();
                        new Player(listener.accept(), "gray").start();
                  }
            } finally {
                  listener.close();
                  LOGGER.info("fr.seb.games.geneticcar.ServeurSocket is closed");
            }
      }

      private static class Player extends Thread {

            private Socket socket;
            private String color;

            public Player(Socket socket, String color) {
                  this.socket = socket;
                  this.color = color;
            }

            public void run() {
                  try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        // Send a welcome message to the client.
                        out.println("Hello, you're the "+ color + "team.");

                        while (true) {
                              LOGGER.info("Attente d'une demande cliente");
                              String input = in.readLine();
                              LOGGER.info("Le client indique "+ input);
                              if (input == null || input.equals(".")) {
                                    break;
                              }
                              out.println(input.toUpperCase());
                        }

                  } catch (IOException e) {
                        LOGGER.info("Error handling player " + color + ": " + e);
                  } finally {
                        try {
                              socket.close();
                        } catch (IOException e) {
                              LOGGER.info("Couldn't close a socket, what's going on?");
                        }
                        LOGGER.info("Connection with player " + color + " closed");
                  }
            }
      }
}
