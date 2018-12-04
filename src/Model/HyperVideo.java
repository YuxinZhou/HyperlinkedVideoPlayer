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
    static private final String linksKey = "Links";

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
     * @param videoName     name of this hyper video
     * @param mainVideoName name of main video
     */
    public HyperVideo(String videoName, String mainVideoName) {
        _videoName = videoName;
        _mainVideoName = mainVideoName;
    }

    public HyperVideo(JSONObject jo) {
        _videoName = (String) jo.get(videoNameKey);
        _mainVideoName = (String) jo.get(mainVideoNameKey);

        JSONArray links = (JSONArray) jo.get(linksKey);
        for (int i = 0; i < links.size(); i++) {
            HyperVideoLink tmp_link = new HyperVideoLink((JSONObject) links.get(i));
            this.addHyperLink(tmp_link.get_name(), tmp_link.get_frameNumber(), tmp_link.get_selectedPixels(),
                    tmp_link.get_subVideoName(), tmp_link.get_subVideoFrameNumber(), tmp_link.get_startFrameNumber());
        }
    }

    /**
     * add a hyper link
     *
     * @param name                a name that associates links in different frames
     * @param frameNumber         the index of a frame
     * @param selectedPixels      a set of selected pixel indexes
     * @param subVideoName        sub video to play when certain pixels are clicked
     * @param subVideoFrameNumber sub video frame to start play with
     */
    public void addHyperLink(String name, int frameNumber, ArrayList<Integer> selectedPixels, String subVideoName,
                             int subVideoFrameNumber, int startFrame) {
        HyperVideoLink tmp_link = new HyperVideoLink(name, frameNumber, selectedPixels, subVideoName,
                subVideoFrameNumber, startFrame);

        // update in links per frame
        if (!_linksPerFrame.containsKey(frameNumber)) {
            _linksPerFrame.put(frameNumber, new ArrayList<>());
        }
        // get all links at this frame
        ArrayList<HyperVideoLink> frameLinks = _linksPerFrame.get(frameNumber);
        // add a new link
        frameLinks.add(tmp_link);

        // update in links per name
        if (!_linksPerName.containsKey(name)) {
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
     * User add a new hyperlink (given startFrame)
     */
    public void newHyperLink(String name, int startFrame, ArrayList<Integer> selectedPixels, String subVideoName, int subVideoFrameNumber) {
        if (_linksPerName.containsKey(name)) {  // Delete old hyperlink

            _linksPerName.remove(name);

            _linksPerFrame.forEach((frame_num, links) -> {
                links.removeIf((link) -> (link.get_name().equals(name)));
            });
        }
        this.addHyperLink(name, startFrame, selectedPixels, subVideoName, subVideoFrameNumber, startFrame);
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
        if (_linksPerName.containsKey(name))
            return _linksPerName.get(name);
        else
            return new ArrayList<>();
    }

    /**
     * get first frame in primary video by a link name
     */
    public int getFirstFrameByName(String name) {
        if (_linksPerName.containsKey(name) && _linksPerName.get(name).size() > 0) {
            return _linksPerName.get(name).get(0).get_startFrameNumber();
        }
        return -1;
    }

    /**
     * get linked frame in secondary video by a link name
     */
    public Frame getLinkedFrameByName(String name) {
        if (_linksPerName.containsKey(name) && _linksPerName.get(name).size() > 0) {
            HyperVideoLink tmp_link = _linksPerName.get(name).get(0);
            Frame frame = new Frame(tmp_link.get_subVideoName(), tmp_link.get_subVideoFrameNumber());
            return frame;
        }
        return null;
    }

    public void renameHyperLinks(String old, String _new) {
        if (old.equals(_new)) return;
        if (!_linksPerName.containsKey(old))
            return;
        // update map
        _linksPerName.put(_new, _linksPerName.get(old));
        _linksPerName.remove(old);

        // update hyperlink's name
        _linksPerName.get(_new).forEach((link) -> link.set_name(_new));

        _linksPerFrame.forEach((frame_num, links) -> {
            links.forEach((link) -> {
                if (link.get_name().equals(old))
                    link.set_name(_new);
            });
        });
    }

    /**
     * get all links by frame number
     *
     * @param frameNum frame number
     * @return all links of at the given frame
     */
    public ArrayList<HyperVideoLink> getLinksByFrame(int frameNum) {
        if (_linksPerFrame.containsKey(frameNum))
            return _linksPerFrame.get(frameNum);
        else
            return new ArrayList<>();
    }

    public JSONObject returnJSON() {
        JSONObject jo = new JSONObject();
        jo.put(videoNameKey, _videoName);
        jo.put(mainVideoNameKey, _mainVideoName);

        JSONArray linksJA = new JSONArray();
        _linksPerFrame.forEach((frame_num, links) -> {
            links.forEach((link) -> {
                linksJA.add(link.returnJSON());
            });
        });
        jo.put(linksKey, linksJA);
        return jo;
    }
}
