package salvado.Model;

import javafx.scene.paint.Color;
import salvado.Controllers.Interfaces.IController;
import salvado.Model.Interfaces.IImageModel;
import salvado.Model.Interfaces.IMainModel;
import salvado.Model.Interfaces.ITerminal;

import java.io.File;
import java.io.UnsupportedEncodingException;

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
            controller.setLoading();
            try {
                imageModel.getPreview(file, (f) -> controller.onSuccessFileSelected(f), () -> controller.onFailedSelectedFile());
            } catch (Exception e) {
                controller.onFailedSelectedFile();
            }
        }else{
            controller.onFailedSelectedFile();
        }
    }

    @Override
    public void convert(Color inValue, Color outValue, double fuzz, double scale, double density, String format) {
        String in = QUOTES + file.getAbsolutePath() + QUOTES;
        String out = QUOTES + createOutputFile(format) + QUOTES;

        String inColour = QUOTES + imageModel.convertRGBtoHEX(outValue) + QUOTES;
        String outColour = QUOTES + imageModel.convertRGBtoHEX(inValue) + QUOTES;

        String command = "convert -density " + density + " -resize " + scale + "% " + in +  " -fuzz " + fuzz + "% -fill " + inColour + " -opaque " + outColour + " " + out;

        terminal.run(command, controller::showSuccess, () -> controller.showError("Error converting svg!"));
    }

    @Override
    public void convertAndBurn(Color inColor, Color outColor, double fuzz, double scale, double density, String format) {

    }

    private String createOutputFile(String format){
        String finalFile = file.getAbsolutePath();

        if(".".equals(finalFile.charAt(finalFile.length() - 4) + ""))
            finalFile = finalFile.substring(0, finalFile.length() - 4);

        return finalFile + format;
    }
}
