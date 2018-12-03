package Util;
import java.awt.*;
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

    public static void drawRectangle(BufferedImage img, Rectangle rect) {
        int x1 = rect.x;
        int x2 = rect.x + rect.width;
        int y1 = rect.y;
        int y2 = rect.y + rect.height;
        for (int i = x1; i < x2; i ++) {
            img.setRGB(i, y1, 0);
            img.setRGB(i, y2, 0);
        }
        for (int j = y1; j < y2; j ++) {
            img.setRGB(x1, j, 0);
            img.setRGB(x2, j, 0);
        }

    }
}
