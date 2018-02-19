package salvado.Controllers.Utils;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import java.io.File;

public class FilePickerController {
    private final static ExtensionFilter accepted_extensions =
            new ExtensionFilter("Resource Files", "*.svg");

    public static File showFilePicker(Window window){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(accepted_extensions);

        return fileChooser.showOpenDialog(window);
    }
}
