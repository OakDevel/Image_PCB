package salvado.Model.Interfaces;

import java.util.concurrent.Callable;

public interface ITerminal {
    public void run(String command, Callable<Void> onSuccess, Callable<Void> onFailure);
}
