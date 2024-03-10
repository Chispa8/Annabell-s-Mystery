/*
package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class GameRoom implements Investigate {

    private final PrintWriter printWriter;
    private final Prompt prompt;
    private final Office office;

    public GameRoom(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        this.office = new Office(printWriter, clientSocket);
    }


    @Override
    public void investigate() {

        Graphics.graphicLivingRoom();
        Graphics.graphicLivingRoomTitle();

        printMessage(Messages.LIVING_ROOM_INVESTIGATION_MESSAGE);
        printMessage(Messages.LIVING_ROOM_INTRODUCTION);

        boolean keepChoosing = true;

        while (keepChoosing) {
            String[] options = {Messages.LETTER, Messages.DAGGER, Messages.KEYS};
            MenuInputScanner story = new MenuInputScanner(options);
            story.setMessage(Messages.SELECTION);
            int answer = prompt.getUserInput(story);

            if (answer == 1) {
                printMessage(Messages.LETTER_MESSAGE);
            }

            if (answer == 2) {
                printMessage(Messages.DAGGER_MESSAGE);

                String[] options3 = {"Stay with it", "Leave it where it was."};
                MenuInputScanner story1 = new MenuInputScanner(options3);
                story1.setMessage(Messages.LIVING_ROOM_CHOISE);
                int answer2 = prompt.getUserInput(story1);

                if (answer2 == 1) {
                   // printMessage(MessagesLIVING_ROOM_CHOISE2);
                }

                if (answer2 == 3) {
                    continue;
                }
            }

            if (answer == 3) {
                printMessage(Messages.KEYS_DESCRIPTION);
                office.investigate();
                keepChoosing = false;
            }

            if (answer == 0 || answer > 3) {
                printMessage(Messages.DAGGAR_MESSAGE2);
            }
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }
}*/
