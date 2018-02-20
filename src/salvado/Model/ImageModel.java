package salvado.Model;

import javafx.scene.paint.Color;
import salvado.Model.Interfaces.IImageModel;
import salvado.Model.Interfaces.ITerminal;
import salvado.Utilities.Constants;
import salvado.Utilities.PathUtilities;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.Callable;
import java.util.function.Function;

import static salvado.Utilities.Constants.COMMAND;
import static salvado.Utilities.Constants.QUOTES;

public class ImageModel implements IImageModel{

    private ITerminal terminal;

    public ImageModel(ITerminal terminal){
        this.terminal = terminal;
    }

    @Override
    public String convertRGBtoHEX(Color color){
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ));
    }

    @Override
    public void getPreview(File file, Function<File, Void> onSuccess, Callable<Void> onFailure) throws Exception {
        String out = PathUtilities.createPreviewPath(file, getClass());

        String command = COMMAND + " -trim " + QUOTES + file.getAbsolutePath() + QUOTES + " " + QUOTES + out + QUOTES;
        terminal.run(command, ()-> {
            onSuccess.apply(new File(out));
            return null;
        }, onFailure);
    }
}
