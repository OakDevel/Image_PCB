package salvado.Utilities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewUtils {

    public static void centerImage(ImageView imageView) {
        Image image = imageView.getImage();

        double w;
        double h;

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
}
