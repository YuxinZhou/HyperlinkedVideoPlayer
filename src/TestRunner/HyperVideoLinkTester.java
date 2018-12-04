package TestRunner;

import Model.HyperVideoLink;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HyperVideoLinkTester {
    public HyperVideoLinkTester() throws IOException, ParseException {
        ArrayList<Integer> tmp_array = new ArrayList<Integer>();
        tmp_array.add(1);
        tmp_array.add(100);
        tmp_array.add(1);
        tmp_array.add(100);
        HyperVideoLink h_link = new HyperVideoLink("link1", 0, tmp_array, "lalala", 30,0);

        JSONObject jo = h_link.returnJSON();

        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();

        JSONParser parser = new JSONParser();
        JSONObject importJO = (JSONObject) parser.parse(new FileReader("JSONExample.json"));

        HyperVideoLink h2_link = new HyperVideoLink(importJO);
        JSONObject exportJO = h2_link.returnJSON();

        PrintWriter pw2 = new PrintWriter("JSONExample2.json");
        pw2.write(exportJO.toJSONString());

        pw2.flush();
        pw2.close();

    }
    public static void main(String[] args) throws IOException, ParseException {
        HyperVideoLinkTester tester = new HyperVideoLinkTester();
    }
}
