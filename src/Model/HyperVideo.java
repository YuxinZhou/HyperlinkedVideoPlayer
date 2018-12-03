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
    static private final String videoNameKey = "VideoName";
    static private final String mainVideoNameKey = "MainVideoName";

    private Set<String> _subVideoNames = new HashSet<>();
    private HashMap<Integer, ArrayList<HyperVideoLink>> _linksPerFrame = new HashMap<>();
    private HashMap<String, ArrayList<HyperVideoLink>> _linksPerName = new HashMap<>();
    static private final String subVideoNamesKey = "SubVideoNames";
    static private final String linksPerFrameKey = "LinksPerFrame";
    static private final String linksPerNameKey = "LinksPerName";

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
     * @param name a name that associates links in different frames
     * @param frameNumber the index of a frame
     * @param selectedPixels a set of selected pixel indexes
     * @param subVideoName sub video to play when certain pixels are clicked
     * @param subVideoFrameNumber sub video frame to start play with
     */
    public void addHyperLink(String name, int frameNumber, ArrayList<Integer> selectedPixels, String subVideoName, int subVideoFrameNumber) {
        HyperVideoLink tmp_link = new HyperVideoLink(name, frameNumber, selectedPixels, subVideoName, subVideoFrameNumber);

        // update in links per frame
        if( !_linksPerFrame.containsKey(frameNumber)) {
            _linksPerFrame.put(frameNumber, new ArrayList<>());
        }
        // get all links at this frame
        ArrayList<HyperVideoLink> frameLinks = _linksPerFrame.get(frameNumber);
        // add a new link
        frameLinks.add(tmp_link);

        // update in links per name
        if(!_linksPerName.containsKey(name)) {
            _linksPerName.put(name, new ArrayList<>());
        }
        // get all links of this name
        ArrayList<HyperVideoLink> nameLinks = _linksPerName.get(name);
        // add a new link
        nameLinks.add(tmp_link);

        // update sub videos set
        _subVideoNames.add(subVideoName);
    }

    /**
     * get all link names
     *
     * @return all link names in an array list
     */
    public ArrayList<String> getAllLinkNames() {
        ArrayList<String> linkNames = new ArrayList<>();
        _linksPerName.forEach((name, links) -> {
            linkNames.add(name);
        });
        return linkNames;
    }

    /**
     * get all links by a link name
     *
     * @param name name of the link
     * @return all links of the given name
     */
    public ArrayList<HyperVideoLink> getLinksByName(String name) {
        if(_linksPerName.containsKey(name))
            return _linksPerName.get(name);
        else
            return new ArrayList<>();
    }

    /**
     * get all links by frame number
     *
     * @param frameNum frame number
     * @return all links of at the given frame
     */
    public ArrayList<HyperVideoLink> getLinksByFrame(int frameNum) {
        if(_linksPerFrame.containsKey(frameNum))
            return _linksPerFrame.get(frameNum);
        else
            return new ArrayList<>();
    }

    public JSONObject returnJSON() {
        JSONObject jo = new JSONObject();
        jo.put(videoNameKey, _videoName);
        jo.put(mainVideoNameKey, _mainVideoName);

        JSONArray linksJA1 = new JSONArray();
        _subVideoNames.forEach((name) -> linksJA1.add(name));

        jo.put(subVideoNamesKey, linksJA1);

        JSONObject linksJoFrame = new JSONObject();
        _linksPerFrame.forEach((frame_num, links) -> {
            JSONArray linksJA = new JSONArray();
            links.forEach((link) -> {
                linksJA.add(link.returnJSON());
            });
            linksJoFrame.put(frame_num, linksJA);
        });
        jo.put(linksPerFrameKey, linksJoFrame);

        JSONObject linksJoName = new JSONObject();
        _linksPerName.forEach((name, links) -> {
            JSONArray linksJA = new JSONArray();
            links.forEach((link) -> {
                linksJA.add(link.returnJSON());
            });
            linksJoName.put(name, linksJA);
        });
        jo.put(linksPerNameKey, linksJoName);

        return jo;
    }
}
