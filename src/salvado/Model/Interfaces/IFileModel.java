package salvado.Model.Interfaces;

import javafx.scene.paint.Color;

import java.io.File;

public interface IFileModel {
    void processSelectedFile(File file);
    void convert(Color inValue, Color outValue, double fuzz, double scale, double density, String selectedItem);
}
