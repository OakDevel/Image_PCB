package salvado.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import salvado.Controllers.Interfaces.IController;
import salvado.Controllers.Utils.FilePickerController;
import salvado.Model.MainModel;
import salvado.Model.Interfaces.IMainModel;

import java.io.*;

import static salvado.Utilities.ImageViewUtils.centerImage;

public class MainController implements IController{
    private IMainModel model;

    @FXML private AnchorPane container;
    @FXML private Button convertButton;
    @FXML private Button convertAndBurn;
    @FXML private ImageView imageView;
    //Isto tem truque... Se quisseres ir buscar a view das o id...Neste caso seria apenas "options". Caso queiras ir
    //buscar o controller(parte do codigo) ao id adicionas "MainController".Neste caso como a view tem o id "options"
    //para ir buscar o seu controller seria "optionsController"
    @FXML private OptionsController optionsController;
    @FXML private Pane loadingView;

    public MainController() {
        model = new MainModel(this);

    }

    @FXML protected void onSelectFileClicked(ActionEvent event) {
        File file = FilePickerController.showFilePicker(container.getScene().getWindow());
        model.processSelectedFile(file);
    }

    @FXML protected void onConvertClicked(ActionEvent event) {
        model.convert(optionsController.getInColor(), optionsController.getOutColor(),
                      optionsController.getFuzzValue(), optionsController.getScaleValue(),
                      optionsController.getDensityValue(), optionsController.getFormat());
    }

    @FXML protected void onConvertAndBurnClicked(ActionEvent event) {
        model.convertAndBurn(optionsController.getInColor(), optionsController.getOutColor(),
                             optionsController.getFuzzValue(), optionsController.getScaleValue(),
                             optionsController.getDensityValue(), optionsController.getFormat());
    }

    @Override
    public Void onSuccessFileSelected(File file) {
        convertButton.setDisable(false);
        convertAndBurn.setDisable(false);
        loadingView.setVisible(false);

        try {
            Image image = new Image(new FileInputStream(file));
            imageView.setImage(image);
            centerImage(imageView);
        } catch (FileNotFoundException e) { }

        return null;
    }

    @Override
    public Void onFailedSelectedFile() {
        convertButton.setDisable(true);
        convertAndBurn.setDisable(false);
        loadingView.setVisible(false);
        imageView.setImage(null);
        showError("Cannot read file!");

        return null;
    }

    @Override
    public Void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Success!");
        alert.show();

        return null;
    }

    @Override
    public Void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(message);
        alert.show();

        return null;
    }

    @Override
    public void setLoading() {
        loadingView.setVisible(true);
    }

}

