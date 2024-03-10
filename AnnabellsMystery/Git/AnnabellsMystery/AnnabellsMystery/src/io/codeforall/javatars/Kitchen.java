package io.codeforall.javatars;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.PrintWriter;

public class Kitchen implements Investigate {

    private final PrintWriter printWriter = new PrintWriter(System.out, true);
    private final Prompt prompt = new Prompt(System.in, System.out);

    @Override
    public void investigate() {
        Graphics.graphicKitchenTitle();
        Graphics.graphicKitchen();
        printMessage(Messages.KITCHEN_INVESTIGATION);

        StringInputScanner finalMessage = new StringInputScanner();
        finalMessage.setMessage(Messages.KITCHEN_INPUT_MESSAGE);
        String answer = prompt.getUserInput(finalMessage);

        if ("12.1".equals(answer)) {
            annabellFinalMessage();
        }
    }

    public void annabellFinalMessage() {
        printMessage(Messages.ANNABELL_FINAL_MESSAGE);
    }

    private void printMessage(String message) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                printWriter.println(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
