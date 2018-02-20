package salvado.Model;

import javafx.application.Platform;
import salvado.Model.Interfaces.ITerminal;
import salvado.Utilities.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class Terminal implements ITerminal {

    @Override
    public void run(String command, Callable<Void> onSuccess, Callable<Void> onFailure){
        final Process[] proc = {null};

        new Thread(() -> {
            try {
                System.out.println("Command to execute: " + command);

                String[] args = new String[]{Constants.SHELL, Constants.SHELL_PARAM, command};
                proc[0] = new ProcessBuilder(args).inheritIO().start();

                int res = proc[0].waitFor();

                //Caso o resultado seja != 0, o commando nao foi bem sucedido.Lancamos excepcao para irmos por catch
                if(res != 0)
                    throw new IOException("There was an error executing the command");

                proc[0].destroy();

                Platform.runLater(() -> {
                    try {
                        onSuccess.call();
                    } catch (Exception e) {
                        try {
                            onFailure.call();
                        } catch (Exception e1) { }
                    }
                });
            } catch (InterruptedException | IOException e) {
                if(e instanceof IOException && proc[0] != null){
                    BufferedReader errinput = new BufferedReader(new InputStreamReader(proc[0].getErrorStream()));
                    errinput.lines().forEach(System.out::println);
                }

                Platform.runLater(() -> {
                    try {
                        onFailure.call();
                    } catch (Exception e1) { }
                });
            }
        }).start();
    }
}
