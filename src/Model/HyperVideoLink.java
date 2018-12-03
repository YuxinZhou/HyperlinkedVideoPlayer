package Model;

import org.json.simple.JSONArray;
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

    public HyperVideoLink(JSONObject jo) {
        _name = (String) jo.get(nameKey);
        _frameNumber = ((Long) jo.get(frameNumberKey)).intValue();
        _selectedPixels = new ArrayList<>();
        JSONArray selectedPixels = (JSONArray) jo.get(selectedPixelsKey);
        for(int i = 0; i < selectedPixels.size(); i++) {
            _selectedPixels.add(((Long) selectedPixels.get(i)).intValue());
        }
        _subVideoName = (String) jo.get(subVideoNameKey);
        _subVideoFrameNumber = ((Long) jo.get(subVideoFrameNumberKey)).intValue();
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
