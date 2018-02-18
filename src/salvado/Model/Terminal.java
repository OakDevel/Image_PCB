package salvado.Model;

import javafx.application.Platform;
import salvado.Model.Interfaces.ITerminal;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class Terminal implements ITerminal {

    @Override
    public void run(String command, Callable<Void> onSuccess, Callable<Void> onFailure){
        new Thread(() -> {
            try {
                System.out.println("Command to execute: " + command);

                String[] args = new String[]{"/bin/bash", "-c", command};
                Process proc = new ProcessBuilder(args).start();

                int res = proc.waitFor();

                //Caso o resultado seja != 0, o commando nao foi bem sucedido.Lancamos excepcao para irmos por catch
                if(res != 0)
                    throw new IOException();

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
                Platform.runLater(() -> {
                    try {
                        onFailure.call();
                    } catch (Exception e1) { }
                });
            }
        }).start();
    }
}
