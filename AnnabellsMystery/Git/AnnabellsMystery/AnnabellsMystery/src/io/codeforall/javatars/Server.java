package io.codeforall.javatars;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static final int PORT = 9090;
    private static final int MAX_PLAYERS = 2;
    private final ConcurrentHashMap<Socket, PrintWriter> clients = new ConcurrentHashMap<>();
    private int connectedPlayers = 0;

    public void start() {

        //Graphics.mainTitle();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            boolean acceptingClients = true;

            while (acceptingClients) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket);

                if (connectedPlayers >= MAX_PLAYERS) {
                    System.out.println("Maximum number of players reached.");
                    clientSocket.close();
                    continue;
                }

                connectedPlayers++;

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final PrintWriter writer;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.put(clientSocket, writer);

                sendMessage(Graphics.ANSI_CYAN + " __      __            _                                        _____                                                              \n" +
                        " \\ \\    / /    ___    | |    __     ___     _ __      ___      |_   _|    ___                                                      \n" +
                        "  \\ \\/\\/ /    / -_)   | |   / _|   / _ \\   | '  \\    / -_)       | |     / _ \\                                                     \n" +
                        "   \\_/\\_/     \\___|   |_|   \\__|   \\___/   |_|_|_|   \\___|  _    |_|     \\___/ __                     _                            \n" +
                        "   /_\\      _ _      _ _      __ _    | |__   ___    | |   | |    ___     |  \\/  |    _  _     ___   | |_     ___     _ _     _  _ \n" +
                        "  / _ \\    | ' \\    | ' \\    / _` |   | '_ \\ / -_)   | |   | |   (_-<     | |\\/| |   | || |   (_-<   |  _|   / -_)   | '_|   | || |\n" +
                        " /_/ \\_\\   |_||_|   |_||_|   \\__,_|   |_.__/ \\___|   |_|   |_|   /__/     |_|  |_|    \\_, |   /__/    \\__|   \\___|   |_|      \\_, |\n" +
                        "                                                                                      |__/                                    |__/ " + Graphics.ANSI_RESET);
            } catch (IOException e) {
                throw new RuntimeException("Error getting output stream" + e);
            }
        }

        @Override
        public void run() {
            try {
                AnnabellsHouse annabellsHouse = new AnnabellsHouse(clientSocket, clients);
                annabellsHouse.start();
            } catch (IOException e) {
                System.out.println("Error in Annabel's run method" + e);;
            } finally {
                clients.remove(clientSocket);
                closeConnectionIfPoliceCalled();
            }
        }

        private void closeConnectionIfPoliceCalled() {
            if (writer.checkError()) {
                System.out.println("Client disconnected.");
                clients.remove(clientSocket);
            }
        }
        private void sendMessage(String message) {
            writer.println(message);
        }
    }

}
