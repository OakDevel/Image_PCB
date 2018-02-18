package salvado.Model;

import javafx.scene.paint.Color;
import salvado.Model.Interfaces.IImageModel;
import salvado.Model.Interfaces.ITerminal;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class ImageModel implements IImageModel{

    private ITerminal terminal;
    private static final String QUOTES = "\"";

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
    public void getPreview(File file, Function<File, Void> onSuccess, Callable<Void> onFailure) throws UnsupportedEncodingException {
        String out = getClass().getClassLoader().getResource("./").getPath() + "preview.png";
        out = URLDecoder.decode(out, "UTF-8");

        String command = "convert -trim " + QUOTES + file.getAbsolutePath() + QUOTES + " " + QUOTES + out + QUOTES;

        String finalOut = out;
        terminal.run(command, ()-> {
            onSuccess.apply(new File(finalOut));
            return null;
        }, onFailure);
    }
}
