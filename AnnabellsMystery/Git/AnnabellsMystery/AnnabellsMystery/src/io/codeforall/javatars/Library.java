package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Library implements Investigate{
    private final PrintWriter printWriter;
    private final Prompt prompt;
    protected LivingRoom livingRoom;

    public Library(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        this.livingRoom = new LivingRoom(printWriter, clientSocket);
    }


    @Override
    public void investigate() {
        printMessage(Graphics.ANSI_YELLOW + Graphics.graphicLibrary() + Graphics.ANSI_RESET);
        printMessage(Graphics.ANSI_YELLOW + Graphics.graphicLibraryTitle() + Graphics.ANSI_RESET);
        printMessage(Messages.LIBRARY_INVESTIGATION_MESSAGE);

        printMessage(Messages.LIBRARY_INTRODUCTION_MESSAGE);

        String[] options = {Messages.LIBRARY_KEY, Messages.LIBRARY_PHOTO, Messages.LIBRARY_RUBBIK};
        MenuInputScanner story = new MenuInputScanner(options);
        story.setMessage("choose");
        int answer = prompt.getUserInput(story);

        if (answer == 2) {
            printMessage(Messages.LIBRARY_PHOTO_RIDDLE);

            String[] options2 = {Messages.LIBRARY_ANSWER, Messages.LIBRARY_ANSWER_2};
            MenuInputScanner story2 = new MenuInputScanner(options2);
            story2.setMessage(Messages.LIBRARY_THOUGHT);
            int answer2 = prompt.getUserInput(story2);

            if (answer2 == 1 || answer2 == 2) {
                printMessage(Messages.LIBRARY_INVESTIGATION_LOCATION);

                String[] options3 = {Messages.LIVING_ROOM, Messages.WALK};
                MenuInputScanner story3 = new MenuInputScanner(options3);
                story3.setMessage(Messages.LIBRARY_CHOICES_MESSAGE);
                int answer3 = prompt.getUserInput(story3);

                if (answer3 == 1) {
                    livingRoom.investigate();
                }
            }
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }
}
