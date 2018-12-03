package TestRunner;

import Model.HyperVideo;
import Model.HyperVideoLink;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HyperVideoTester {
    public HyperVideoTester() throws IOException, ParseException {
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

        JSONParser parser = new JSONParser();
        JSONObject importJO = (JSONObject) parser.parse(new FileReader("JSONExample.json"));

        HyperVideo h2_video = new HyperVideo(importJO);
        h2_video.addHyperLink("link1", 100, tmp_set3, "lalala", 50);
        JSONObject exportJO = h2_video.returnJSON();

        PrintWriter pw2 = new PrintWriter("JSONExample2.json");
        pw2.write(exportJO.toJSONString());

        pw2.flush();
        pw2.close();
    }
    public static void main(String[] args) throws IOException, ParseException {
        HyperVideoTester tester = new HyperVideoTester();
    }
}
