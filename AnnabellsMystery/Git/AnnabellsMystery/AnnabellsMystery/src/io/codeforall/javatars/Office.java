package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Office implements Investigate {

    private final PrintWriter printWriter;
    private final Prompt prompt;
    private final DinnerRoom dinnerRoom;

    public Office(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        this.dinnerRoom = new DinnerRoom(printWriter, clientSocket);
    }

    @Override
    public void investigate() {
        displayGraphicsAndIntroduction();

        String[] options = {Messages.STRANGE_PEN, Messages.WORLD_GLOBE, Messages.MISPLACED_BOOK};
        MenuInputScanner story = new MenuInputScanner(options);
        story.setMessage(Messages.OFFICE_OPTIONS);
        int answer = prompt.getUserInput(story);

        handleUserChoice(answer);
    }

    private void displayGraphicsAndIntroduction() {
        Graphics.graphicOffice();
        Graphics.graphicOfficeTitle();
        printMessage(Messages.OFFICE_ENTERING_MESSAGE);
        printMessage(Messages.OFFICE_INTRODUCTION);
    }

    private void handleUserChoice(int answer) {
        if (answer == 2) {
            exploreWorldGlobe();
        }
    }

    private void exploreWorldGlobe() {
        printMessage(Messages.WORLD_GLOBE_LOOK);

        String[] options2 = {Messages.OFFICE_GLOBE_OPTION, Messages.OFFICE_GLOBE_OPTION2, Messages.OFFICE_GLOBE_OPTION3};
        MenuInputScanner story2 = new MenuInputScanner(options2);
        story2.setMessage(Messages.OFFICE_OPTIONS2);
        int answer2 = prompt.getUserInput(story2);

        if (answer2 == 2) {
            printMessage(Messages.OFFICE_CHECK_ITEM);
            exploreNextRoom();
        }
    }

    private void exploreNextRoom() {
        String[] options3 = {Messages.OFFICE_OPTIONS2_DINNER_ROOM, Messages.OFFICE_OPTIONS3_ROOM1};
        MenuInputScanner story3 = new MenuInputScanner(options3);
        story3.setMessage(Messages.OFFICE_OPTIONS3);
        int answer3 = prompt.getUserInput(story3);

        if (answer3 == 1) {
            dinnerRoom.investigate();
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }
}

