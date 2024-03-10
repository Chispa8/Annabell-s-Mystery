package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class DinnerRoom implements Investigate{

    private final PrintWriter printWriter;
    private final Prompt prompt;
 //   private final Bathroom bathroom = new Bathroom();

    private final Kitchen kitchen = new Kitchen();


    public DinnerRoom(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
    }

    @Override
    public void investigate() {
        Graphics.graphicDinnerRoom();
        Graphics.graphicDinnerRoomTitle();
        printMessage(Messages.DINNER_ENTERING_MESSAGE);

        printMessage(Messages.DINNER_INTRODUCTION);

        String[] options = {Messages.CHANDELIER, Messages.EMPTY_CHAIR, Messages.OLD_PORCELAIN_PLATE};
        MenuInputScanner story = new MenuInputScanner(options);
        story.setMessage(Messages.DINNER_OPTIONS);
        int answer = prompt.getUserInput(story);
        if (answer == 1) {
            String[] options4 = {Messages.DINNER_ROOM_CHANDELIER_OPTION, Messages.DINNER_ROOM_CHANDELIER_OPTION2, Messages.DINNER_ROOM_CHANDELIER_OPTION3};
            MenuInputScanner story4 = new MenuInputScanner(options4);
            story4.setMessage(Messages.CHANDELIER_LOOK);
            int answer4 = prompt.getUserInput(story4);
            if (answer4 == 1) {
                printMessage(Messages.DINNER_CHECK_CHANDELIER_BOTTOM);
                String[] options3 = {Messages.DINNER_OPTIONS3_BATHROOM, Messages.DINNER_OPTIONS2_KITCHEN};
                MenuInputScanner story3 = new MenuInputScanner(options3);
                story3.setMessage(Messages.DINNER_OPTIONS3);
                int answer3 = prompt.getUserInput(story3);
                if (answer3 == 1) {
                 //   bathroom.investigate();
                } else if (answer3 == 2) {
                    kitchen.investigate();
                }

            }
            if (answer4 == 2) {
               printMessage(Messages.DINNER_CHANDELIER_STEALING);
               closeGame();
            }

            if (answer4 == 3) {
               printMessage(Messages.DINNER_CHANDELIER_CHECK_OPTION3);
                String[] options5 = {Messages.DINNER_ROOM_CHANDELIER_OPTION, Messages.DINNER_ROOM_CHANDELIER_OPTION2, Messages.DINNER_ROOM_CHANDELIER_OPTION3};
                MenuInputScanner story5 = new MenuInputScanner(options5);
                story4.setMessage(Messages.CHANDELIER_LOOK);
                int answer5 = prompt.getUserInput(story5);
                if (answer5 == 3) {

                }

            }

        }

        if (answer == 3) {
            printMessage(Messages.PORCELAIN_PLATE_LOOK);

            String[] options2 = {Messages.DINNER_ROOM_PLATE_OPTION, Messages.DINNER_ROOM_PLATE_OPTION2, Messages.DINNER_ROOM_PLATE_OPTION3};
            MenuInputScanner story2 = new MenuInputScanner(options2);
            story2.setMessage(Messages.DINNER_ROOM_OPTIONS2);
            int answer2 = prompt.getUserInput(story2);

            if (answer2 == 3) {
                printMessage(Messages.DINNER_CHECK_PLATE);

                String[] options3 = {Messages.DINNER_OPTIONS3_BATHROOM, Messages.DINNER_OPTIONS2_KITCHEN};
                MenuInputScanner story3 = new MenuInputScanner(options3);
                story3.setMessage(Messages.DINNER_OPTIONS3);
                int answer3 = prompt.getUserInput(story3);

                if (answer3 == 1) {
                //    bathroom.investigate();
                }

                if (answer3 == 2) {
                    kitchen.investigate();
                }
            }
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }

    private void closeGame () {
        printWriter.close();

    }

}
