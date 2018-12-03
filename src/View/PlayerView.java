package View;

import Model.Sound;
import Model.Video;
import Util.imageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class PlayerView implements ActionListener {

    private static final int width = 352;
    private static final int height = 288;
    public static final int fps = 30;
    private JFrame jFrame;
    private JPanel controlPane;
    private JButton playButton = new JButton(">");
    private JButton stopButton = new JButton("||");
    private BufferedImage img;
    private Video video;
    private Sound sound;


    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage frame = video.getNextFrame();
        if (frame != null) {
            imageUtil.copyImage(frame, img);
            jFrame.repaint();
        }
    }

    public PlayerView() {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        jFrame = new JFrame();
        controlPane = new JPanel();
        video = new Video("AIFilmTwo");
        sound = new Sound();

        stopButton.setEnabled(false);

        imageUtil.copyImage(video.getNextFrame(), img);
        JLabel lbIm1 = new JLabel(new ImageIcon(img));

        sound.loadSound("data/AIFilmTwo/AIFilmTwo.wav");

        // Define buttons' function
        playButton.addActionListener(new PlayButtonListener());

        stopButton.addActionListener(new StopButtonListener());

        // Add buttons to control panel
        controlPane.add(playButton);
        controlPane.add(stopButton);

        GridBagLayout gLayout = new GridBagLayout();
        jFrame.getContentPane().setLayout(gLayout);

        // Layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        jFrame.getContentPane().add(lbIm1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;

        jFrame.getContentPane().add(controlPane, c);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    private class PlayButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            stopButton.setEnabled(true);
            playButton.setEnabled(false);
            try {
                sound.resume();
                video.resume();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class StopButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            stopButton.setEnabled(false);
            playButton.setEnabled(true);
            try {
                sound.pause();
                video.pause();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PlayerView view = new PlayerView();
        Timer clock = new Timer(1000/fps, view);
        clock.start();
    }

}

