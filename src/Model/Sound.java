package Model;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private Clip clip;
    private int frame = 0;

    public void loadSound(String soundName) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        FileInputStream waveStream;
        try {
            waveStream = new FileInputStream(soundName);
            InputStream bufferedIn = new BufferedInputStream(waveStream);
            AudioInputStream sound =  AudioSystem.getAudioInputStream(bufferedIn);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(sound);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }

        frame = 0;

        // play, stop, loop the sound clip
    }

    public void loadSound(String soundName, int startFrame) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        FileInputStream waveStream;
        try {
            waveStream = new FileInputStream(soundName);
            InputStream bufferedIn = new BufferedInputStream(waveStream);
            AudioInputStream sound =  AudioSystem.getAudioInputStream(bufferedIn);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(sound);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }

        frame = startFrame * 1470;
        clip.setFramePosition(frame);
        System.out.println("clip frame set: " + frame);
        // play, stop, loop the sound clip
    }

    public void play(){
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    public void pause(){
        frame = clip.getFramePosition();
        clip.stop();
    }

    public void resume(){
        clip.setFramePosition(frame);
        System.out.println("clip frame set: " + clip.getFramePosition());
        clip.start();
    }

    public int getFramePosition() {
        return clip.getFramePosition();
    }
}