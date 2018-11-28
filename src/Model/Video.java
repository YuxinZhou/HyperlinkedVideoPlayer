package Model;

import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedBlockingQueue;


public class Video {

    private final int fps = 30;
    private boolean started = false;
    private int startFramePos = 1;
    private int curFramePos = -20;
    private BufferedImage curFrame;
    private long startTime = System.currentTimeMillis();

    private LinkedBlockingQueue<Frame> frames = new LinkedBlockingQueue<>(1000);


    public Video(String videoName) {
        new Thread(() -> {
            for (int i = 1; i <= 9000; i++)
            {
                try {
                    frames.put(new Frame(videoName, i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            curFrame = frames.take().getImg();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void resume() {
        startTime = System.currentTimeMillis();
        startFramePos = curFramePos;
        started = true;
    }

    public void pause() {
        started = false;
    }

    public BufferedImage getNextFrame() {
        BufferedImage frame = null;
        long curTime = System.currentTimeMillis();
        int framePos = (int) ((curTime - startTime) * fps / 1000.) + startFramePos;
        if (started && curFramePos < framePos) {
            try {
                curFramePos = framePos;
                Frame frameObj = frames.take();
                while (frameObj.getId() < framePos){
                    frameObj = frames.take();
                }
                frame = frameObj.getImg();
                curFrame = frame;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            frame = curFrame;
        }
        return frame;
    }
}
