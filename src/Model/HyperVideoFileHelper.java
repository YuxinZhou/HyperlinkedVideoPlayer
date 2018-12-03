package Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HyperVideoFileHelper {
    public HyperVideoFileHelper() {

    }

    public void saveToFile(String content, String filePath) {
        try {
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(content);
            pw.flush();
            pw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveHyperVideoToFile(HyperVideo h_video, String filePath) {
        saveToFile(h_video.returnJSON().toJSONString(), filePath);
    }

    public HyperVideo readHyperVideoFromFile(String filePath) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject tmp_jo = (JSONObject) parser.parse(new FileReader(filePath));
            return new HyperVideo(tmp_jo);
        }
        catch (ParseException| IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
