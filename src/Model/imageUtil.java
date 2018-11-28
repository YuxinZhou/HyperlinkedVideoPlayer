package Model;
import java.awt.image.BufferedImage;

public class imageUtil {

    public static void copyImage(BufferedImage from, BufferedImage to) {
        if (from.getHeight() != to.getHeight() || from.getWidth() != to.getWidth()) {
            return;
        }
        int height = from.getHeight();
        int width = to.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pix = from.getRGB(x, y);
                to.setRGB(x, y, pix);
            }
        }
    }
}
