package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Frame {
    private final int width = 352;
    private final int height = 288;
    private boolean isColor = true;

    private int id;
    private BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public Frame(String videoName, int i) {
        this.id = i;

        String filename = String.valueOf(i);
        if (i >= 0 && i < 10) {
            filename = "000" + String.valueOf(i);
        } else if (i < 100) {
            filename = "00" + String.valueOf(i);
        } else if (i < 1000) {
            filename = "0" + String.valueOf(i);
        }

        filename = "data/" + videoName + "/" + videoName + filename + ".rgb";

        // Read image from file
        try {
            File file = new File(filename);
            InputStream inputStream = new FileInputStream(file);

            long len = file.length();
            byte[] bytes = new byte[(int) len];

            int offset = 0;
            int numRead;
            while (offset < bytes.length && (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            int ind = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    byte r = bytes[ind];
                    byte g = isColor ? bytes[ind + height * width] : r;
                    byte b = isColor ? bytes[ind + height * width * 2] : r;

                    int rgb = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                    img.setRGB(x, y, rgb);
                    ind++;
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int getId(){
        return id;
    }

    public BufferedImage getImg() {
        return img;
    }
}
