package Util;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class viewUtil {
    // format a rectangle to [topLeft.x, topLeft.y, bottomRight.x, bottomRight.y]
    public static ArrayList<Integer> rectToArray(Rectangle rect) {
        return new ArrayList<>(Arrays.asList(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height));
    }

    public static Rectangle arrayToRect(ArrayList<Integer> array){
        return new Rectangle(array.get(0), array.get(1), array.get(2)-array.get(0), array.get(3)-array.get(1));
    }

    // create a rectangle using start drawing & end drawing point
    public static Rectangle createRect(Point start, Point end, Point origin) {
        int x0 = origin.x;
        int y0 = origin.y;
        Point topLeft = new Point(Math.min(start.x, end.x) - x0, Math.min(start.y, end.y) - y0);
        Point bottomRight = new Point(Math.max(start.x, end.x) - x0, Math.max(start.y, end.y) - y0);
        return new Rectangle(topLeft.x, topLeft.y,
                bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    }
}
