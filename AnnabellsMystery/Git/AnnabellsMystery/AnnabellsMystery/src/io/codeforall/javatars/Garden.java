package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Garden  implements Investigate{

    private final PrintWriter printWriter;
    private final Prompt prompt;
    private final Library library;


    public Garden(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        this.library = new Library(printWriter, clientSocket);
        init();
    }
    private void init() {
     //   printMessage(Messages.GARDEN_INVESTIGATION_MESSAGE);
    }
    @Override
    public void investigate() {

        boolean garden = true;

        while (garden) {
            printMessage(Graphics.ANSI_GREEN + Graphics.graphicGarden() + Graphics.ANSI_RESET);
            printMessage(Graphics.ANSI_RED + Graphics.graphicGardenTitle() + Graphics.ANSI_RESET);
            printMessage(Messages.GARDEN_INTRODUCTION_MESSAGE);

            String[] options = {"Little book", "Leash from an animal", "Blue shoe"};
            MenuInputScanner story = new MenuInputScanner(options);
            story.setMessage("what should I do?");
            int answer = prompt.getUserInput(story);

            if (answer == 1) {
                handleBook();
                garden = false;
            }
            if (answer == 2) {
                printMessage(Messages.LEASH);
            }
            if (answer == 3) {
                printMessage(Messages.BLUE_SHOE);

            }
            garden = answer >= 1 && answer <= 3;
        }
    }

    private void handleBook() {

        printMessage(Messages.GARDEN_NOTE);

        String[] options = {"Translate", "Throw to garbage"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("Choose an option:");
        int choice = prompt.getUserInput(scanner);

        if (choice == 1) {
            translateBinary();
            library.investigate();
        }
        if (choice == 2) {
            printMessage("You threw the book to the garbage.");

        } else {
            printMessage("Invalid choice. Please choose again.");
            handleBook();
        }
    }
    private void translateBinary() {
        String binaryString = Messages.GARDEN_BINARY_NUMBER;

        String[] bytes = binaryString.split(" ");

        StringBuilder translated = new StringBuilder();
        for (String byteString : bytes) {
            int decimal = Integer.parseInt(byteString, 2);
            translated.append((char) decimal);
        }
        System.out.println("Message: " + translated.toString());
    }


    private void printMessage(String message) {
        printWriter.println(message);
    }
}
