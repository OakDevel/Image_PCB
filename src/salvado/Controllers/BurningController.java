package salvado.Controllers;

import salvado.Model.BurningModel;
import salvado.Model.Interfaces.IBurningModel;

public class BurningController {
    private IBurningModel model;

    public BurningController() {
        this.model = new BurningModel();
    }
}
