package salvado.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import salvado.Controllers.Interfaces.IController;
import salvado.Model.FileModel;
import salvado.Model.Interfaces.IFileModel;
import java.io.*;

public class Controller implements IController{
    private IFileModel model;

    private final static ObservableList formats = FXCollections.observableArrayList(".png", ".tiff", ".bmp", ".jpg");

    @FXML private GridPane container;
    @FXML private Button convertButton;
    @FXML private ColorPicker inColor;
    @FXML private ColorPicker outColor;
    @FXML private ImageView imageView;
    @FXML private Slider fuzzSlider;
    @FXML private Label fuzzValue;
    @FXML private Slider densitySlider;
    @FXML private Label densityValue;
    @FXML private Slider scaleSlider;
    @FXML private Label scaleValue;
    @FXML private ChoiceBox<String> formatPicker;
    @FXML private Pane loadingView;

    public Controller() {
        model = new FileModel(this);

    }

    @FXML
    protected void initialize(){
        inColor.setValue(Color.BLACK);
        outColor.setValue(Color.web("#8000a1"));

        //Adds fuzz slider value and how to handle the change of that value
        fuzzValue.setText((int)fuzzSlider.getValue() + "%");
        fuzzSlider.valueProperty().addListener((observable, oldValue, newValue) -> fuzzValue.setText(newValue.intValue() + "%"));

        densityValue.setText((int)densitySlider.getValue() + "");
        densitySlider.valueProperty().addListener((observable, oldValue, newValue) -> densityValue.setText(newValue.intValue() + ""));

        scaleValue.setText((int)scaleSlider.getValue() + "%");
        scaleSlider.valueProperty().addListener((observable, oldValue, newValue) -> scaleValue.setText(newValue.intValue() + "%"));

        //Init format selector
        formatPicker.setItems(formats);
        formatPicker.getSelectionModel().selectFirst();
    }


    @FXML protected void onSelectFileClicked(ActionEvent event) {
        File file = FilePickerController.showFilePicker(container.getScene().getWindow());
        model.processSelectedFile(file);
    }

    @FXML protected void onConvertClicked(ActionEvent event) {
        model.convert(inColor.getValue(), outColor.getValue(), fuzzSlider.getValue(), scaleSlider.getValue(), densitySlider.getValue(), formatPicker.getSelectionModel().getSelectedItem());
    }

    @Override
    public Void onSuccessFileSelected(File file) {
        convertButton.setDisable(false);
        loadingView.setVisible(false);

        try {
            Image image = new Image(new FileInputStream(file));
            imageView.setImage(image);

            centerImage();
        } catch (FileNotFoundException e) { }

        return null;
    }

    private void centerImage() {
        Image image = imageView.getImage();

        double w = 0;
        double h = 0;

        double ratioX = imageView.getFitWidth() / image.getWidth();
        double ratioY = imageView.getFitHeight() / image.getHeight();

        double reducCoeff = 0;
        if(ratioX >= ratioY) {
            reducCoeff = ratioY;
        } else {
            reducCoeff = ratioX;
        }

        w = image.getWidth() * reducCoeff;
        h = image.getHeight() * reducCoeff;

        imageView.setX((imageView.getFitWidth() - w) / 2);
        imageView.setY((imageView.getFitHeight() - h) / 2);
    }

    @Override
    public Void onFailedSelectedFile() {
        convertButton.setDisable(true);
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

