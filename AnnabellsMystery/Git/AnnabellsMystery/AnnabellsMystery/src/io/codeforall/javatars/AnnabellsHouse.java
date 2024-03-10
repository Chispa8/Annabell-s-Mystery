package io.codeforall.javatars;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

public class AnnabellsHouse implements Runnable {

    private final PrintWriter writer;
    private final Prompt prompt;
    private final Pool pool;
    private final Socket clientSocket;

    public AnnabellsHouse(Socket clientSocket, Map<Socket, PrintWriter> clients) throws IOException {
        this.clientSocket = clientSocket;
        this.writer = clients.get(clientSocket);
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        this.pool = new Pool(writer, clientSocket);
    }

    @Override
    public void run() {
        printMessage(Messages.HOUSE_INTRODUCTION);
        start();
    }

    public void start() {

        String username = askUserName(Messages.HOUSE_ASK_NAME);

        String gender = askUserGender();

        printMessage(String.format(Messages.HOUSE_ENTRY_MESSAGE, gender, username));
        askForWine();

        printMessage(Messages.HOUSE_STORY);
        printMessage(Messages.HOUSE_WAKEUP);

        int choice = askUserOption("What do you want to do?", new String[]{"Resolve the mystery", "Call the police"});

        handleUserChoice(choice);

    }

    private String askUserName(String message) {
        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage(message);
        return prompt.getUserInput(scanner);
    }

    private String askUserGender() {
        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage("Are you a Sir or a Madam?");
        String gender = prompt.getUserInput(scanner);

        return gender.equalsIgnoreCase("Sir") ? "Sir" : "Madam";
    }

    private String askForWine() {
        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage("Would you like a glass of wine? (yes/no)");

        String answer = prompt.getUserInput(scanner).toLowerCase();

        return answer.equals("yes") ? Messages.WINE : "";
    }

    private int askUserOption(String message, String[] options) {
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage(message);
        return prompt.getUserInput(scanner);
    }

    private void handleUserChoice(int choice) {
        if (choice == 1) {
            solveMystery();
            return;
        }
        callPolice();
    }

    private void solveMystery() {
        pool.investigate();
    }

    private void callPolice() {
        printMessage("You decided to call the police. The game ends here.");
        closeConnection();
    }

    private void closeConnection() {
        try {
            writer.println("Game over");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printMessage(String message) {
        writer.println(message);
    }
}
