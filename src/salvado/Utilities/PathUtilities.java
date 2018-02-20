package salvado.Utilities;

import salvado.Model.ImageModel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static salvado.Utilities.Constants.QUOTES;

public class PathUtilities {

    public static String createTerminalOutputPath(File file, String format){
        String finalFile = file.getAbsolutePath();

        if(".".equals(finalFile.charAt(finalFile.length() - 4) + ""))
            finalFile = finalFile.substring(0, finalFile.length() - 4);

        return QUOTES + finalFile + format + QUOTES;
    }

    public static String createTerminalInputPath(File file){
        return QUOTES + file.getAbsolutePath() + QUOTES;
    }

    public static String createPreviewPath(File file, Class<? extends ImageModel> classe) throws UnsupportedEncodingException {
        String out = classe.getClassLoader().getResource("./").getPath() + "preview.png";
        out = URLDecoder.decode(out, "UTF-8");

        if(out.startsWith("/") && Constants.isWindows())
            out = out.substring(1, out.length());

        return out;
    }

    public static String revertTerminalPath2FilePath(String path){
        String out = path.substring(1, path.length() -1);
        out = out.replace("\\", "/");

        return out;
    }

}
