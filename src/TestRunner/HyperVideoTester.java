package TestRunner;

import Model.HyperVideo;
import Model.HyperVideoFileHelper;
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
        HyperVideoFileHelper fileHelper = new HyperVideoFileHelper();

        HyperVideo h_video = new HyperVideo("new video", "USCOne");

        ArrayList<Integer> tmp_set1 = new ArrayList<Integer>();
        tmp_set1.add(1);
        tmp_set1.add(2);
        tmp_set1.add(10);
        tmp_set1.add(20);

        h_video.addHyperLink("link1", 0, tmp_set1, "lalala", 10);

        ArrayList<Integer> tmp_set2 = new ArrayList<Integer>();
        tmp_set2.add(101);
        tmp_set2.add(102);
        tmp_set2.add(110);
        tmp_set2.add(120);

        h_video.addHyperLink("link2", 0, tmp_set2, "lala", 10);

        ArrayList<Integer> tmp_set3 = new ArrayList<Integer>();
        tmp_set3.add(201);
        tmp_set3.add(202);
        tmp_set3.add(210);
        tmp_set3.add(220);

        h_video.addHyperLink("link1", 10, tmp_set3, "lalala", 50);

        fileHelper.saveHyperVideoToFile(h_video, "first_save.JSON");
        HyperVideo h2_video = fileHelper.readHyperVideoFromFile("first_save.JSON");
        h2_video.addHyperLink("link1", 100, tmp_set3, "lalala", 50);
        fileHelper.saveHyperVideoToFile(h2_video, "second_save.JSON");
    }
    public static void main(String[] args) throws IOException, ParseException {
        HyperVideoTester tester = new HyperVideoTester();
    }
}
