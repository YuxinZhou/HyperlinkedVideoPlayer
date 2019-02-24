package View;

import Model.*;
import Util.imageUtil;
import Util.viewUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;

public class PlayerView implements ActionListener {
    private static final int soundPerFrame = 1470;
    private static final int width = 352;
    private static final int height = 288;
    public static final int fps = 30;
    private JFrame jFrame;
    private JPanel controlPane;
    private JButton playButton = new JButton(">");
    private JButton stopButton = new JButton("||");
    private JButton loadVideoButton = new JButton("Load Video");
    private BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private Video video = new Video();
    private Sound sound = new Sound();
    private HyperVideo hyperVideo;
    private JLabel lbIm1;
    private String videoName = null;

    private int formatVideoFrame(int frame) {
        return Math.max(Math.min(frame, 9000), 1);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (videoName == null) return;
        int soundFrame = sound.getFramePosition();
        int videoFrame = soundFrame / soundPerFrame + 1;

        BufferedImage frame = video.getNextFrame(formatVideoFrame(videoFrame));
        if (frame != null) {
            imageUtil.copyImage(frame, img);
            jFrame.repaint();
        }
    }

    public PlayerView() {
        jFrame = new JFrame();
        controlPane = new JPanel();

        stopButton.setEnabled(false);
        playButton.setEnabled(false);

        lbIm1 = new JLabel(new ImageIcon(img));
        lbIm1.addMouseListener(new MouseListener());

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
        jFrame.addWindowListener(new WindowListener());
    }


    private void reloadVideo(String name, int start) {
        if (videoName != null) sound.stop();
        videoName = name;

        String filePath = "json/" + videoName + ".json";

        if (new File(filePath).isFile()) {
            hyperVideo = HyperVideoFileHelper.readHyperVideoFromFile(filePath);
        } else {
            hyperVideo = new HyperVideo("", videoName);
        }
        sound.loadSound("data/" + videoName + "/" + videoName + ".wav", start * soundPerFrame);
        video.loadVideo(videoName, hyperVideo);
        stopButton.setEnabled(false);
        playButton.setEnabled(true);
        jFrame.setTitle("Playing - " + videoName);
    }


    private class PlayButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stopButton.setEnabled(true);
            playButton.setEnabled(false);
            try {
                sound.resume();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class StopButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stopButton.setEnabled(false);
            playButton.setEnabled(true);
            try {
                sound.pause();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class LoadVideoButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stopButton.setEnabled(false);
            playButton.setEnabled(false);
            if (videoName != null) sound.pause();

            JFileChooser fileChooser = new JFileChooser("data/");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            fileChooser.setDialogTitle("Please choose the hyper video json file");
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                reloadVideo(fileChooser.getSelectedFile().getName(), 1);
            }
        }
    }

    private class MouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (videoName == null) return;
            int in_image_pos_x = e.getPoint().x - (lbIm1.getBounds().width - width) / 2;
            int in_image_pos_y = e.getPoint().y - (lbIm1.getBounds().height - height) / 2;
            ArrayList<HyperVideoLink> linksThisFrame = hyperVideo.getLinksByFrame(sound.getFramePosition() / 1470 + 1);
            for (HyperVideoLink link : linksThisFrame) {

                Rectangle rect = viewUtil.arrayToRect(link.get_selectedPixels());
                if (rect.contains(new Point(in_image_pos_x, in_image_pos_y))) {
                    reloadVideo(link.get_subVideoName(), link.get_subVideoFrameNumber());
                    break;
                }
            }
        }
    }

    private class WindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
            sound.stop();
        }
    }

    public static void main(String[] args) {
        PlayerView view = new PlayerView();
        Timer clock = new Timer(1000 / fps, view);
        clock.start();
    }

}

