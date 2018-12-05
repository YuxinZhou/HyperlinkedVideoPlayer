package View;

import Model.HyperVideo;
import Model.HyperVideoFileHelper;
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
    private JButton loadVideoButton = new JButton("Load Video");
    private BufferedImage img;
    private Video video;
    private Sound sound;
    private HyperVideo hyperVideo;


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
        video = new Video("USCOne");
        sound = new Sound();

        stopButton.setEnabled(false);

        imageUtil.copyImage(video.getNextFrame(), img);
        JLabel lbIm1 = new JLabel(new ImageIcon(img));

        sound.loadSound("data/USCOne/USCOne.wav");

        // Define buttons' function
        playButton.addActionListener(new PlayButtonListener());
        stopButton.addActionListener(new StopButtonListener());
        loadVideoButton.addActionListener(new LoadVideoButtonListener());

        // Add buttons to control panel
        controlPane.add(playButton);
        controlPane.add(stopButton);
        controlPane.add(loadVideoButton);

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

    private void LoadNewVideo(String filePath) {
        hyperVideo = HyperVideoFileHelper.readHyperVideoFromFile(filePath);
        video = new Video(hyperVideo);
        sound.loadSound("data/" + hyperVideo.get_mainVideoName() + "/" + hyperVideo.get_mainVideoName() + ".wav");
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

    private class LoadVideoButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stopButton.setEnabled(false);
            playButton.setEnabled(true);
            sound.pause();
            video.pause();

            JFileChooser fileChooser = new JFileChooser("json/");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            fileChooser.setDialogTitle("Please choose the hyper video json file");
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                LoadNewVideo(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        PlayerView view = new PlayerView();
        Timer clock = new Timer(1000/fps, view);
        clock.start();
    }

}

