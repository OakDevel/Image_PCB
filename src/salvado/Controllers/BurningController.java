package salvado.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import salvado.Controllers.Interfaces.IBurningController;
import salvado.Model.BurningModel;
import salvado.Model.Interfaces.IBurningModel;
import java.io.File;
import java.io.FileInputStream;

public class BurningController implements IBurningController{
    private IBurningModel model;
    private File file;
    private Stage stage;

    @FXML
    private ImageView imageView;

    public BurningController(File file, Stage stage) {
        this.file = file;
        this.stage = stage;
        this.model = new BurningModel(this);
    }

    @FXML
    protected void initialize(){
        try {
            Image image = new Image(new FileInputStream(file));

            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
            imageView.setImage(image);
            imageView.setPreserveRatio(true);

            model.startTimer();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        stage.close();
    }
}
