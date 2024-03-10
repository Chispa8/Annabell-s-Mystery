package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Pool implements Investigate {

    private final PrintWriter printWriter;
    private final Prompt prompt;
    protected Garden garden;


    public Pool(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        this.garden = new Garden(printWriter, clientSocket);
    }

    @Override
    public void investigate() {
        Graphics.graphicPool();
        Graphics.graphicSwimmingPoolTitle();
        printMessage(Messages.POOL_INVESTIGATION_MESSAGE);

        printMessage(Messages.POOL_INTRODUCTION);

        String[] options = {Messages.POOL_CALL_COPS, Messages.POOL_WAKEUP_EVERYBODY, Messages.POOL_INVESTIGATE_BODY};
        MenuInputScanner story = new MenuInputScanner(options);
        story.setMessage(Messages.POOL_OPTIONS);
        int answer = prompt.getUserInput(story);

        if (answer == 3) {
            printMessage(Messages.POOL_INVESTIGATION_BODY);

            String[] options2 = {Messages.POOL_SAND_BAG, Messages.POOL_ANABELLS_NECKLACE, Messages.POOL_WATCH, Messages.POOL_CELL_PHONE};
            MenuInputScanner story2 = new MenuInputScanner(options2);
            story2.setMessage(Messages.POOL_INVESTIGATION_LOOKAROUND);
            int answer2 = prompt.getUserInput(story2);

            if (answer2 == 1) {
                printMessage(Messages.POOL_INVESTIGATION_LOCATION);

                String[] options3 = {Messages.GARDEN, Messages.KITCHEN};
                MenuInputScanner story3 = new MenuInputScanner(options3);
                story3.setMessage(Messages.POOL_CHOICES_MESSAGE);
                int answer3 = prompt.getUserInput(story3);

                if (answer3 == 1) {
                    garden.investigate();
                }
            }
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }
}
