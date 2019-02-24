package Model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class Video {
    private static final int soundPerFrame = 1470;
    private String videoName;
    private HyperVideo hyperVideo;
    private boolean isHyper;


    public void loadVideo(String videoName) {
        this.videoName = videoName;
        isHyper = false;
    }

    public void loadVideo (String videoName, HyperVideo hyperVideo) {
        this.hyperVideo = hyperVideo;
        this.videoName = videoName;
        isHyper = true;
    }

    public BufferedImage getNextFrame(int nextFrame) {
        if(isHyper) {
            return (new Frame(videoName, nextFrame, hyperVideo.getLinksByFrame(nextFrame))).getImg();
        }
        else {
            return (new Frame(videoName, nextFrame)).getImg();
        }
    }
}
