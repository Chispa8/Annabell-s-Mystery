package io.codeforall.javatars;
import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

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
        start();
    }

    public void start() {
        printMessage(Graphics.ANSI_CYAN + " __      __            _                                        _____                                                              \n" +
                " \\ \\    / /    ___    | |    __     ___     _ __      ___      |_   _|    ___                                                      \n" +
                "  \\ \\/\\/ /    / -_)   | |   / _|   / _ \\   | '  \\    / -_)       | |     / _ \\                                                     \n" +
                "   \\_/\\_/     \\___|   |_|   \\__|   \\___/   |_|_|_|   \\___|  _    |_|     \\___/ __                     _                            \n" +
                "   /_\\      _ _      _ _      __ _    | |__   ___    | |   | |    ___     |  \\/  |    _  _     ___   | |_     ___     _ _     _  _ \n" +
                "  / _ \\    | ' \\    | ' \\    / _` |   | '_ \\ / -_)   | |   | |   (_-<     | |\\/| |   | || |   (_-<   |  _|   / -_)   | '_|   | || |\n" +
                " /_/ \\_\\   |_||_|   |_||_|   \\__,_|   |_.__/ \\___|   |_|   |_|   /__/     |_|  |_|    \\_, |   /__/    \\__|   \\___|   |_|      \\_, |\n" +
                "                                                                                      |__/                                    |__/ " + Graphics.ANSI_RESET);
        printMessage(Messages.HOUSE_INTRODUCTION);
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
        scanner.setMessage("Are you a Sir or a Madam? \n");
        String gender = prompt.getUserInput(scanner);
        printMessage(Graphics.graphicMansion());
        return gender.equalsIgnoreCase("Sir") ? "Sir" : "Madam";
    }

    private String askForWine() {
        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage("Barata's butler: Would you like a glass of wine? (yes/no) \n");
        printMessage(Graphics.ANSI_RED + Graphics.wineGlass() + Graphics.ANSI_RESET);
        String answer = prompt.getUserInput(scanner).toLowerCase();
        if (answer.equals("yes")) {
            writer.println(Messages.WINE);
        }

        return " ";
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
