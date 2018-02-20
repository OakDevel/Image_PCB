package salvado.Model;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import salvado.Controllers.BurningController;
import salvado.Controllers.Interfaces.IController;
import salvado.Model.Interfaces.IImageModel;
import salvado.Model.Interfaces.IMainModel;
import salvado.Model.Interfaces.ITerminal;
import salvado.Utilities.Constants;
import salvado.Utilities.PathUtilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static salvado.Utilities.Constants.COMMAND;
import static salvado.Utilities.Constants.QUOTES;

public class MainModel implements IMainModel {

    private File file;
    private ITerminal terminal;
    private IImageModel imageModel;
    private IController controller;

    public MainModel(IController controller) {
        this.controller = controller;
        this.terminal = new Terminal();
        this.imageModel = new ImageModel(terminal);
    }

    @Override
    public void processSelectedFile(File file) {
        if(file != null){
            this.file = file;
            System.out.println(file.getAbsolutePath());
            controller.setLoading();
            try {
                imageModel.getPreview(file, (f) -> controller.onSuccessFileSelected(f), () -> controller.onFailedSelectedFile());
            } catch (Exception e) {
                controller.onFailedSelectedFile();
            }
        }
    }

    @Override
    public void convert(Color inColor, Color outColor, double fuzz, double scale, double density, String format) {
        convertCommand(inColor, outColor, fuzz,
                       scale, density, format,
                       (f) -> controller.showSuccess(), () -> controller.showError("Error converting svg!"));
     }

    @Override
    public void convertAndBurn(Color inColor, Color outColor, double fuzz, double scale, double density, String format) {
        convertCommand(inColor, outColor, fuzz,
                scale, density, format,
                (f) -> showBurningWindow(new File(PathUtilities.revertTerminalPath2FilePath(f))),
                () -> controller.showError("Cannot init timer Window"));
    }

    private void convertCommand(Color inColor, Color outColor, double fuzz, double scale, double density,
                                String format, Consumer<String> onSuccess, Callable<Void> onFailure){
        String in = PathUtilities.createTerminalInputPath(file);
        String out = PathUtilities.createTerminalOutputPath(file, format);

        String inColour = QUOTES + imageModel.convertRGBtoHEX(outColor) + QUOTES;
        String outColour = QUOTES + imageModel.convertRGBtoHEX(inColor) + QUOTES;

        String command = COMMAND + " -density " + density + " -resize " + scale + "% " + in +
                         " -fuzz " + fuzz + "% -fill " + inColour + " -opaque " + outColour + " " + out;

        terminal.run(command, () -> {
                    onSuccess.accept(out);
                    return null;
                }, onFailure);
    }

    private Void showBurningWindow(File file){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./../Views/BurningView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Timer");

            BurningController burningController = new BurningController(file, stage);

            loader.setController(burningController);
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
