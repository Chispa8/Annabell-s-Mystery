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
            System.out.println("Error to start" + e);;
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

            } catch (IOException e) {
                throw new RuntimeException("Error getting output stream", e);
            }
        }

        @Override
        public void run() {
            try {
                AnnabellsHouse annabellsHouse = new AnnabellsHouse(clientSocket, clients);
                annabellsHouse.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
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