package salvado.Model.Interfaces;

import javafx.scene.paint.Color;

import java.io.File;

public interface IMainModel {
    void processSelectedFile(File file);
    void convert(Color inValue, Color outValue, double fuzz, double scale, double density, String format);
    void convertAndBurn(Color inColor, Color outColor, double fuzz, double scale, double density, String format);
}
