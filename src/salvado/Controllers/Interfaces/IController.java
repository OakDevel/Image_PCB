package salvado.Controllers.Interfaces;

import java.io.File;

public interface IController {
    Void onSuccessFileSelected(File file);
    Void onFailedSelectedFile();

    Void showSuccess();

    Void showError(String message);

    void setLoading();

}
