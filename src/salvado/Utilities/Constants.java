package salvado.Utilities;

public class Constants {

    private static final String OS = System.getProperty("os.name");
    public static final String QUOTES = "\"";
    public static final String COMMAND = isWindows()
                                         ? "magick convert"
                                         : "convert";

    public static final String SHELL = isWindows()
                                       ? "cmd.exe"
                                       : "/bin/bash";

    public static final String SHELL_PARAM = isWindows()
                                             ? "/c"
                                             : "-c";


    public static boolean isWindows(){
        return OS.startsWith("Windows");
    }
}
