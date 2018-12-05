package Util;

import Model.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class imageUtil {

    public static final Color White = new Color(255, 255, 255);
    public static final Color Yellow = new Color(255, 255, 0);
    public static final Color Red = new Color(255, 0, 0);

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

    public static void drawRectangle(BufferedImage img, Rectangle rect, Color color) {
        int x1 = rect.x;
        int x2 = rect.x + rect.width;
        int y1 = rect.y;
        int y2 = rect.y + rect.height;
        for (int i = x1; i < x2; i++) {
            img.setRGB(i, y1, color.getRGB());
            img.setRGB(i, y2, color.getRGB());
        }
        for (int j = y1; j < y2; j++) {
            img.setRGB(x1, j, color.getRGB());
            img.setRGB(x2, j, color.getRGB());
        }


    }

    public static byte getLuminance(int color) {
        byte r = (byte) ((color >> 16) & 0xff);
        byte g = (byte) ((color >> 8) & 0xff);
        byte b = (byte) ((color) & 0xff);
        return (byte) (0.2126 * r + 0.7152 * g + 0.0722 * b);
    }



    public static void main(String[] args) {

//        JFrame jFrame = new JFrame();
//        jFrame.getContentPane().add(new JLabel(new ImageIcon(showGrayImg(target))));
//
//        jFrame.pack();
//        jFrame.setVisible(true);
    }


}
