package salvado.Model.Interfaces;

import javafx.scene.paint.Color;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import java.util.function.Function;

public interface IImageModel {

    public String convertRGBtoHEX(Color color);
    void getPreview(File file, Function<File, Void> onSuccess, Callable<Void> onFailure) throws UnsupportedEncodingException;
}
