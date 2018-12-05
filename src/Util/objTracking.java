package Util;

import Model.Frame;
import Model.HyperVideoLink;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class objTracking {
    private static final int width = 352;
    private static final int height = 288;
    private static final int maxFrames = 200;

    public static byte[][] toGrayMatrix(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        byte[][] result = new byte[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte lmn = imageUtil.getLuminance(img.getRGB(x, y));
                result[x][y] = lmn;
            }
        }
        return result;
    }

    public static byte[][] getRegion(byte[][] img, ArrayList<Integer> region) {
        int x0 = region.get(0);
        int y0 = region.get(1);
        int width = region.get(2) - x0 + 1;
        int height = region.get(3) - y0 + 1;
        byte[][] result = new byte[width][height];
        for (int y = y0; y < y0 + height; y++) {
            for (int x = x0; x < x0 + width; x++) {
                byte lmn = img[x][y];
                result[x - x0][y - y0] = lmn;
            }
        }
        return result;
    }

    public static BufferedImage showGrayImg(byte[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        BufferedImage show = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte lmn = matrix[x][y];
                int rgb = 0xff000000 | ((lmn & 0xff) << 16) | ((lmn & 0xff) << 8) | (lmn & 0xff);
                show.setRGB(x, y, rgb);
            }
        }
        return show;
    }

    /**
     * Find the most similar area by minimal sum of absolute difference.
     *
     * @param target Target image represented by 2-D byte array
     * @param img    Source image represented by 2-D byte array
     * @param bound  Searching bound represented by ArrayList
     * @param res    Result bound represented by ArrayList, having the same size as target
     * @return Minimal difference
     */

    public static int findClosestArea(byte[][] target, byte[][] img, ArrayList<Integer> bound, ArrayList<Integer> res) {

        int t_width = target.length;
        int t_height = target[0].length;

        int minDiff = Integer.MAX_VALUE;
        int minX = bound.get(0);
        int minY = bound.get(1);
        int maxX = bound.get(2);
        int maxY = bound.get(3);



        for (int y = minY; y <= maxY - t_height; y++) {
            for (int x = minX; x <= maxX - t_width; x++) {
                int curDiff = 0;
                boolean toSearch = true;
                for (int j = 0; j < t_height; j++) {
                    if (!toSearch) break;           // early terminate
                    for (int i = 0; i < t_width; i++) {
                        curDiff += Math.abs(target[i][j] - img[i + x][j + y]);
                        if (curDiff > minDiff) {
                            toSearch = false;
                            break;
                        }
                    }
                }

                if (curDiff < minDiff) {
                    minDiff = curDiff;
                    res.clear();
                    res.addAll(Arrays.asList(x, y, x + t_width - 1, y + t_height - 1));
                }
            }
        }
        return minDiff;
    }

    public static ArrayList<HyperVideoLink> tracking(HyperVideoLink start, String videoName) {
        int frameNum = start.get_frameNumber();
        int startFrame = frameNum;
        ArrayList<HyperVideoLink> res = new ArrayList<>();
        BufferedImage cur = (new Frame(videoName, frameNum)).getImg();
        int error;
        ArrayList<Integer> objBound = start.get_selectedPixels();
        int radius = 10;
        while (frameNum + 1 <= 9000 && frameNum - startFrame <= maxFrames) {
            BufferedImage nxt = (new Frame(videoName, ++frameNum)).getImg();


            int minX = Math.max(0, objBound.get(0) - radius);
            int minY = Math.max(0, objBound.get(1) - radius);
            int maxX = Math.min(width - 1, objBound.get(2) + radius);
            int maxY = Math.min(height - 1, objBound.get(3) + radius);
            ArrayList<Integer> searchBox = new ArrayList<>(Arrays.asList(minX, minY, maxX, maxY));

            byte[][] source = toGrayMatrix(nxt);
            byte[][] target = getRegion(toGrayMatrix(cur), objBound);
            ArrayList<Integer> newBound = new ArrayList<>();
            error = findClosestArea(target, source, searchBox, newBound);
            //System.out.println(frameNum + "   " + error / ((maxY - minY) * (maxX - minX)));
            if (error / ((maxY - minY) * (maxX - minX)) > 23) break;
            res.add(new HyperVideoLink(start.get_name(), frameNum, newBound, start.get_subVideoName(),
                    start.get_subVideoFrameNumber(), start.get_startFrameNumber()));
            objBound = newBound;
            cur = nxt;
        }


        return res;

    }
}
