package TestRunner;

import Model.HyperVideo;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class HyperVideoTester {
    public HyperVideoTester() throws IOException {
        HyperVideo h_video = new HyperVideo("new video", "USCOne");

        Set<Integer> tmp_set1 = new HashSet<Integer>();
        tmp_set1.add(1);
        tmp_set1.add(2);

        h_video.addHyperLink(0, tmp_set1, "lalala", 10);

        Set<Integer> tmp_set2 = new HashSet<Integer>();
        tmp_set2.add(8);
        tmp_set2.add(10);

        h_video.addHyperLink(0, tmp_set2, "lala", 10);

        Set<Integer> tmp_set3 = new HashSet<Integer>();
        tmp_set3.add(1);
        tmp_set3.add(2);

        h_video.addHyperLink(10, tmp_set3, "lalala", 50);

        JSONObject jo = h_video.returnJSON();

        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();
    }
    public static void main(String[] args) throws IOException {
        HyperVideoTester tester = new HyperVideoTester();
    }
}
