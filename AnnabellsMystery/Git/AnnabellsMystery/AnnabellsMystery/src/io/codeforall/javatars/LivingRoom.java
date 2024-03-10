package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class LivingRoom implements Investigate {
    private final PrintWriter printWriter;
    private final Prompt prompt;
    //private final GameRoom gameRoom;
    private boolean hasDagger = false;

    public LivingRoom(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        //this.gameRoom = new GameRoom(printWriter, clientSocket);
        init();
    }

    private void init() {
        Graphics.graphicLivingRoom();
        Graphics.graphicLivingRoomTitle();
        //printMessage(Messages.LIVING_ROOM_INVESTIGATION_MESSAGE);
        //printMessage(Messages.LIVING_ROOM_INTRODUCTION);
    }

    @Override
    public void investigate() {

        boolean keepChoosing = true;

        while (keepChoosing) {

            String[] options = (!hasDagger) ?
                    new String[]{Messages.LETTER, Messages.DAGGER, Messages.KEYS} :
                    new String[]{Messages.LETTER, Messages.KEYS};


            MenuInputScanner story = new MenuInputScanner(options);
            story.setMessage(Messages.SELECTION);
            int answer = prompt.getUserInput(story);

            if (answer == 1) {
                printMessage(Messages.LETTER_MESSAGE);
            }

            if (answer == 2) {
                handleDagger();
            }

            if (answer == 3) {
                printMessage(Messages.KEYS_DESCRIPTION);
                //gameRoom.investigate();
                keepChoosing = false;
                break;
            }
            keepChoosing = answer >= 1 && answer <= 3;
        }
    }

    private void handleDagger() {

        if (hasDagger) {
            printMessage("You already have a dagger!");
            return;
        }

        printMessage(Messages.DAGGER_MESSAGE);

        String[] dagger = {"Stay with it", "Leave it where it was."};
        MenuInputScanner story = new MenuInputScanner(dagger);
        story.setMessage("What would you like to do?");
        int answer = prompt.getUserInput(story);

        if (answer == 1) {
            hasDagger = true;
            printMessage(Messages.DAGGAR_MESSAGE2);
            investigate();
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }
}
