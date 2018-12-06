package Model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class Video {

    private final int fps = 30;
    private boolean started = false;
    private int startFramePos = 1;
    private int curFramePos = 0;
    private BufferedImage curFrame;

    private String video_name;
    private HyperVideo hyperVideo;
    private boolean isHyper;


    public Video(String videoName) {
        video_name = videoName;
        isHyper = false;
    }

    public Video (HyperVideo h_video) {
        hyperVideo = h_video;
        video_name = hyperVideo.get_mainVideoName();
        isHyper = true;
    }

    public BufferedImage getNextFrame(int soundFrame) {
        if(isHyper) {
            return (new Frame(video_name, soundFrame/1470 + 1, hyperVideo.getLinksByFrame(soundFrame/1470 + 1))).getImg();
        }
        else {
            return (new Frame(video_name, soundFrame/1470 + 1)).getImg();
        }
    }
}
