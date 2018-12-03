package TestRunner;

import Model.HyperVideo;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HyperVideoTester {
    public HyperVideoTester() throws IOException {
        HyperVideo h_video = new HyperVideo("new video", "USCOne");

        ArrayList<Integer> tmp_set1 = new ArrayList<Integer>();
        tmp_set1.add(1);
        tmp_set1.add(2);
        tmp_set1.add(10);
        tmp_set1.add(20);

        h_video.addHyperLink("link1", 0, tmp_set1, "lalala", 10);

        ArrayList<Integer> tmp_set2 = new ArrayList<Integer>();
        tmp_set1.add(101);
        tmp_set1.add(102);
        tmp_set1.add(110);
        tmp_set1.add(120);

        h_video.addHyperLink("link2", 0, tmp_set2, "lala", 10);

        ArrayList<Integer> tmp_set3 = new ArrayList<Integer>();
        tmp_set1.add(201);
        tmp_set1.add(202);
        tmp_set1.add(210);
        tmp_set1.add(220);

        h_video.addHyperLink("link1", 10, tmp_set3, "lalala", 50);

        JSONObject jo = h_video.returnJSON();

        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();

        System.out.println(h_video.getAllLinkNames());
        System.out.println(h_video.getLinksByName("link1"));
        System.out.println(h_video.getLinksByFrame(0));
    }
    public static void main(String[] args) throws IOException {
        HyperVideoTester tester = new HyperVideoTester();
    }
}
