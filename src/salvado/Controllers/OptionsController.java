package salvado.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class OptionsController {

    private final static ObservableList formats = FXCollections.observableArrayList(".png", ".tiff", ".bmp", ".jpg");

    @FXML private ColorPicker inColor;
    @FXML private ColorPicker outColor;
    @FXML private Slider fuzzSlider;
    @FXML private Label fuzzValue;
    @FXML private Slider densitySlider;
    @FXML private Label densityValue;
    @FXML private Slider scaleSlider;
    @FXML private Label scaleValue;
    @FXML private ChoiceBox<String> formatPicker;

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

    public String getFormat(){
        return formatPicker.getSelectionModel().getSelectedItem();
    }

    public Color getInColor() {
        return inColor.getValue();
    }

    public Color getOutColor() {
        return outColor.getValue();
    }

    public double getFuzzValue(){
        return fuzzSlider.getValue();
    }

    public double getDensityValue(){
        return densitySlider.getValue();
    }

    public double getScaleValue(){
        return scaleSlider.getValue();
    }
}
