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
    }


    @Override
    public void investigate() {
        Graphics.graphicGarden();
        Graphics.graphicGardenTitle();
        printMessage(Messages.GARDEN_INVESTIGATION_MESSAGE);

        printMessage(Messages.GARDEN_INTRODUCTION_MESSAGE);

        String[] options = {Messages.GARDEN_BOOK, Messages.GARDEN_LEASH, Messages.GARDEN_SHOE};
        MenuInputScanner story = new MenuInputScanner(options);
        story.setMessage(Messages.POOL_OPTIONS);
        int answer = prompt.getUserInput(story);

        if (answer == 1) {
            printMessage(Messages.GARDEN_NOTE);

            String[] options2 = {Messages.GARDEN_ANSWER, Messages.GARDEN_ANSWER_2};
            MenuInputScanner story2 = new MenuInputScanner(options2);
            story2.setMessage(Messages.GARDEN_BINARY_NUMBER);
            int answer2 = prompt.getUserInput(story2);

            if (answer2 == 2) {
                printMessage(Messages.GARDEN_INVESTIGATION_LOCATION);

                String[] options3 = {Messages.LIBRARY, Messages.SUITE};
                MenuInputScanner story3 = new MenuInputScanner(options3);
                story3.setMessage(Messages.GARDEN_CHOICES_MESSAGE);
                int answer3 = prompt.getUserInput(story3);

                if (answer3 == 1) {
                    library.investigate();
                }
            }
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }
}
