package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HyperVideo {
    private String _videoName;
    private String _mainVideoName;

    private Set<String> _subVideoNames = new HashSet<String>();
    private HashMap<Integer, ArrayList<HyperVideoLink>> _linksPerFrame = new HashMap<Integer, ArrayList<HyperVideoLink>>();

    static private final String videoNameKey = "VideoName";
    static private final String mainVideoNameKey = "MainVideoName";
    static private final String subVideoNamesKey = "SubVideoNames";
    static private final String linksPerFrameKey = "LinksPerFrame";

    /**
     * _videoName getter
     *
     * @return name of this hyper video
     */
    public String get_videoName() {
        return _videoName;
    }

    /**
     * _mainVideoName getter
     *
     * @return name of main video
     */
    public String get_mainVideoName() {
        return _mainVideoName;
    }

    /**
     * constructor
     *
     * @param videoName name of this hyper video
     * @param mainVideoName name of main video
     */
    public HyperVideo(String videoName, String mainVideoName) {
        _videoName = videoName;
        _mainVideoName = mainVideoName;
    }

    /**
     * add a hyper link
     *
     * @param frameNumber the index of a frame
     * @param selectedPixels a set of selected pixel indexes
     * @param subVideoName sub video to play when certain pixels are clicked
     * @param subVideoFrameNumber sub video frame to start play with
     */
    public void addHyperLink(int frameNumber, Set<Integer> selectedPixels, String subVideoName, int subVideoFrameNumber) {
        // check links existence in map
        if( !_linksPerFrame.containsKey(frameNumber)) {
            _linksPerFrame.put(frameNumber, new ArrayList<HyperVideoLink>());
        }
        // get all links at this frame
        ArrayList<HyperVideoLink> links = _linksPerFrame.get(frameNumber);
        // add a new link
        links.add(new HyperVideoLink(frameNumber, selectedPixels, subVideoName, subVideoFrameNumber));
        _subVideoNames.add(subVideoName);
    }

    /**
     *
     * @param frameNumber the index of a frame
     * @return a map that maps a pixel to its hyper link index
     */
    public HashMap<Integer, Integer> getPixelLinkIndexMap(int frameNumber) {
        // initiate the map to return
        HashMap<Integer, Integer> pixelLinkIndexMap = new HashMap<Integer, Integer>();
        // check links map existence
        if( !_linksPerFrame.containsKey(frameNumber)) {
            _linksPerFrame.put(frameNumber, new ArrayList<HyperVideoLink>());
        }
        // get all links at this frame
        ArrayList<HyperVideoLink> links = _linksPerFrame.get(frameNumber);
        // link each pixel with its link index at this frame
        for(int i = 0; i < links.size(); i++) {
            HyperVideoLink link = links.get(i);
            Set<Integer> selectedPixels = link.get_selectedPixels();
            for (int pixelNumber: selectedPixels) {
                pixelLinkIndexMap.put(pixelNumber, i);
            }
        }
        return pixelLinkIndexMap;
    }

    public JSONObject returnJSON() {
        JSONObject jo = new JSONObject();
        jo.put(videoNameKey, _videoName);
        jo.put(mainVideoNameKey, _mainVideoName);

        JSONArray linksJA1 = new JSONArray();
        _subVideoNames.forEach((name) -> linksJA1.add(name));

        jo.put(subVideoNamesKey, linksJA1);

        JSONObject linksJo = new JSONObject();
        _linksPerFrame.forEach((frame_num, links) -> {
            JSONArray linksJA = new JSONArray();
            links.forEach((link) -> {
                linksJA.add(link.returnJSON());
            });
            linksJo.put(frame_num, linksJA);
        });
        jo.put(linksPerFrameKey, linksJo);
        return jo;
    }
}
