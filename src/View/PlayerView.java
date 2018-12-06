package View;

import Model.*;
import Util.imageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    private JLabel lbIm1;


    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage frame = video.getNextFrame(sound.getFramePosition());
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

        imageUtil.copyImage(video.getNextFrame(0), img);
        lbIm1 = new JLabel(new ImageIcon(img));
        lbIm1.addMouseListener(new MouseListener());

        sound.loadSound("data/AIFilmTwo/AIFilmTwo.wav");

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

    private void StopVideo() {
        stopButton.setEnabled(false);
        playButton.setEnabled(true);
        sound.stop();
    }

    private void LoadNewVideo(String filePath) {
        hyperVideo = HyperVideoFileHelper.readHyperVideoFromFile(filePath);
        video = new Video(hyperVideo);
        sound.loadSound("data/" + hyperVideo.get_mainVideoName() + "/" + hyperVideo.get_mainVideoName() + ".wav");
    }

    private void LoadSubVideo(HyperVideoLink hyperVideoLink) {
        StopVideo();

        video = new Video(hyperVideoLink.get_subVideoName());
        sound.loadSound("data/" + hyperVideoLink.get_subVideoName() + "/" + hyperVideoLink.get_subVideoName() + ".wav", hyperVideoLink.get_subVideoFrameNumber());


//        stopButton.setEnabled(true);
//        playButton.setEnabled(false);
//        sound.resume();
//        video.resume();
    }

    private class PlayButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            stopButton.setEnabled(true);
            playButton.setEnabled(false);
            try {
                sound.resume();
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

            JFileChooser fileChooser = new JFileChooser("json/");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            fileChooser.setDialogTitle("Please choose the hyper video json file");
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                StopVideo();
                LoadNewVideo(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    private class MouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int in_image_pos_x = e.getPoint().x - (lbIm1.getBounds().width - width)/2;
            int in_image_pos_y = e.getPoint().y - (lbIm1.getBounds().height - height)/2 ;
            ArrayList<HyperVideoLink> linksThisFrame = hyperVideo.getLinksByFrame(sound.getFramePosition()/1470 + 1);
            linksThisFrame.forEach((link) -> {
                ArrayList<Integer> pixelsSelected = link.get_selectedPixels();
                if((in_image_pos_x >= pixelsSelected.get(0)) && (in_image_pos_y >= pixelsSelected.get(1)) && (in_image_pos_x <= pixelsSelected.get(2)) && (in_image_pos_y <= pixelsSelected.get(3))) {
                    System.out.println("click in link");
                    LoadSubVideo(link);
                }
            });
        }
    }

    public static void main(String[] args) {
        PlayerView view = new PlayerView();
        Timer clock = new Timer(1000/fps, view);
        clock.start();
    }

}

