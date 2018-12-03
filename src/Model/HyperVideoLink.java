package Model;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class HyperVideoLink {
    private String _name;
    private int _frameNumber;
    private ArrayList<Integer> _selectedPixels;
    private String _subVideoName;
    private int _subVideoFrameNumber;

    static private final String nameKey = "Name";
    static private final String frameNumberKey = "FrameNumber";
    static private final String selectedPixelsKey = "SelectedPixels";
    static private final String subVideoNameKey = "SubVideoName";
    static private final String subVideoFrameNumberKey = "SubVideoFrameNumber";

    public HyperVideoLink(String name, int frameNumber, ArrayList<Integer> selectedPixels, String subVideoName, int subVideoFrameNumber) {
        _name = name;
        _frameNumber = frameNumber;
        _selectedPixels = selectedPixels;
        _subVideoName = subVideoName;
        _subVideoFrameNumber = subVideoFrameNumber;
    }

    public String get_name() {
        return _name;
    }

    public int get_frameNumber() {
        return _frameNumber;
    }

    public ArrayList<Integer> get_selectedPixels() {
        return _selectedPixels;
    }

    public String get_subVideoName() {
        return _subVideoName;
    }

    public int get_subVideoFrameNumber() {
        return _subVideoFrameNumber;
    }

    public JSONObject returnJSON() {
        JSONObject jo = new JSONObject();
        jo.put(nameKey, _name);
        jo.put(frameNumberKey, _frameNumber);
        jo.put(selectedPixelsKey, _selectedPixels);
        jo.put(subVideoNameKey, _subVideoName);
        jo.put(subVideoFrameNumberKey, _subVideoFrameNumber);
        return jo;
    }
}
