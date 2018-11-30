package TestRunner;

import Model.HyperVideoLink;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class HyperVideoLinkTester {
    public HyperVideoLinkTester() throws IOException {
        Set<Integer> tmp_set = new HashSet<Integer>();
        tmp_set.add(1);
        tmp_set.add(2);
        HyperVideoLink h_link = new HyperVideoLink(0, tmp_set, "lalala", 30);

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
