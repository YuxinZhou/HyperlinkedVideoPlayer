package View;

import Model.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class AuthView implements ActionListener{

    private static final int width = 352;
    private static final int height = 288;
    public static final int fps = 30;
    private JFrame jFrame;
    private JPanel controlPane;
    private JButton playButton = new JButton(">");
    private JButton stopButton = new JButton("||");
    private BufferedImage img1;
    private BufferedImage img2;
    private Video video1;
    private Sound sound1;


    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage frame = video1.getNextFrame();
        if (frame != null) {
            imageUtil.copyImage(frame, img1);
            jFrame.repaint();
        }
    }

    public AuthView() {
        img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        jFrame = new JFrame();
        controlPane = new JPanel();
        video1 = new Video("AIFilmTwo");
        sound1 = new Sound();

        stopButton.setEnabled(false);

        imageUtil.copyImage(video1.getNextFrame(), img1);
        JLabel lbIm1 = new JLabel(new ImageIcon(img1));

        sound1.loadSound("data/AIFilmTwo/AIFilmTwo.wav");

        // Define buttons' function
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                stopButton.setEnabled(true);
                playButton.setEnabled(false);
                try {
                    sound1.resume();
                    video1.resume();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                stopButton.setEnabled(false);
                playButton.setEnabled(true);
                try {
                    sound1.pause();
                    video1.pause();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

    public static void main(String[] args) {
        AuthView view = new AuthView();
        Timer clock = new Timer(1000/fps, view);
        clock.start();
    }

}
