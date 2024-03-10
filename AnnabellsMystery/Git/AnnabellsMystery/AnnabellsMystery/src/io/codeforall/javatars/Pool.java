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
    private final Garden garden;
    private final Kitchen kitchen;
    private final Server server;

    public Pool(PrintWriter printWriter, Socket clientSocket) throws IOException {
        this.printWriter = printWriter;
        this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        this.garden = new Garden(printWriter, clientSocket);
        this.kitchen = new Kitchen(printWriter, clientSocket);
        this.server = new Server();
        init();
    }

    private void init() {
        Graphics.graphicPool();
        Graphics.graphicSwimmingPoolTitle();
        //printMessage(Messages.POOL_INVESTIGATION_MESSAGE);
    }

    @Override
    public void investigate() {
        boolean pool = true;

        while (pool) {
            printMessage(Graphics.ANSI_BLUE + Graphics.graphicPool() + Graphics.ANSI_RESET);
            printMessage("                                                                                                                                                              \n" +
                    " @@@@@@      @@@  @@@  @@@     @@@     @@@@@@@@@@      @@@@@@@@@@      @@@     @@@  @@@      @@@@@@@@        @@@@@@@       @@@@@@       @@@@@@      @@@       \n" +
                    "@@@@@@@      @@@  @@@  @@@     @@@     @@@@@@@@@@@     @@@@@@@@@@@     @@@     @@@@ @@@     @@@@@@@@@        @@@@@@@@     @@@@@@@@     @@@@@@@@     @@@       \n" +
                    "!@@          @@!  @@!  @@!     @@!     @@! @@! @@!     @@! @@! @@!     @@!     @@!@!@@@     !@@              @@!  @@@     @@!  @@@     @@!  @@@     @@!       \n" +
                    "!@!          !@!  !@!  !@!     !@!     !@! !@! !@!     !@! !@! !@!     !@!     !@!!@!@!     !@!              !@!  @!@     !@!  @!@     !@!  @!@     !@!       \n" +
                    "!!@@!!       @!!  !!@  @!@     !!@     @!! !!@ @!@     @!! !!@ @!@     !!@     @!@ !!@!     !@! @!@!@        @!@@!@!      @!@  !@!     @!@  !@!     @!!       \n" +
                    " !!@!!!      !@!  !!!  !@!     !!!     !@!   ! !@!     !@!   ! !@!     !!!     !@!  !!!     !!! !!@!!        !!@!!!       !@!  !!!     !@!  !!!     !!!       \n" +
                    "     !:!     !!:  !!:  !!:     !!:     !!:     !!:     !!:     !!:     !!:     !!:  !!!     :!!   !!:        !!:          !!:  !!!     !!:  !!!     !!:       \n" +
                    "    !:!      :!:  :!:  :!:     :!:     :!:     :!:     :!:     :!:     :!:     :!:  !:!     :!:   !::        :!:          :!:  !:!     :!:  !:!      :!:      \n" +
                    ":::: ::       :::: :: :::       ::     :::     ::      :::     ::       ::      ::   ::      ::: ::::         ::          ::::: ::     ::::: ::      :: ::::  \n" +
                    ":: : :         :: :  : :       :        :      :        :      :       :       ::    :       :: :: :          :            : :  :       : :  :      : :: : :  \n" +
                    "                                                                                                                                                              ");
            printMessage(Messages.POOL_INTRODUCTION);

            String[] options = {"Call the cops", "Wake up everybody", "Investigation"};
            MenuInputScanner story = new MenuInputScanner(options);
            story.setMessage("What should I do?");
            int answer = prompt.getUserInput(story);

            if (answer == 1) {
                printMessage(Messages.CALLING_THE_COPS);
            } else if (answer == 2) {
                printMessage(Messages.WAKE_UP_EVERYBODY);
            } else if (answer == 3) {
                investigateBody();
                pool = false;
            }

            pool = answer >= 1 && answer <= 3;
        }
    }

    private void investigateBody() {
        printMessage(Messages.POOL_INVESTIGATION_BODY);

        String[] options = {"Sand bag", "Cell Phone"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage(Messages.POOL_INVESTIGATION_LOOKAROUND);
        int answer = prompt.getUserInput(scanner);

        if (answer == 1) {
            investigateLocation();
        } else if (answer == 2) {
        }
    }

    private void investigateLocation() {
        printMessage(Messages.POOL_INVESTIGATION_LOCATION);

        String[] options = {"Garden", "Kitchen"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("Choose: ");
        int answer = prompt.getUserInput(scanner);

        if (answer == 1) {
            garden.investigate();
        } else {
            kitchen.investigate();
        }
    }

    private void printMessage(String message) {
        printWriter.println(message);
    }
}
