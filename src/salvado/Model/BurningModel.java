package salvado.Model;

import javafx.application.Platform;
import salvado.Controllers.Interfaces.IBurningController;
import salvado.Model.Interfaces.IBurningModel;

import java.util.Timer;
import java.util.TimerTask;

public class BurningModel implements IBurningModel {
    private Timer timer;
    private final int MINUTES = 15;
    private IBurningController controller;

    public BurningModel(IBurningController controller) {
        timer = new Timer();
        this.controller = controller;
    }

    @Override
    public void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    stopTimerUI();
                });
            }
        }, MINUTES * 60 * 1000);
    }

    private void stopTimerUI(){
        controller.close();
    }
}
