package TestRunner;

import Model.HyperVideoLink;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HyperVideoLinkTester {
    public HyperVideoLinkTester() throws IOException {
        ArrayList<Integer> tmp_array = new ArrayList<Integer>();
        tmp_array.add(1);
        tmp_array.add(100);
        tmp_array.add(1);
        tmp_array.add(100);
        HyperVideoLink h_link = new HyperVideoLink("link1", 0, tmp_array, "lalala", 30);

        JSONObject jo = h_link.returnJSON();

        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();
    }
    public static void main(String[] args) throws IOException {
        HyperVideoLinkTester tester = new HyperVideoLinkTester();
    }
}
