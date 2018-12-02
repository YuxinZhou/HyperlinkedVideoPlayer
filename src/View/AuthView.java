package View;

import Model.*;
import Model.Frame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class AuthView {
    private static final int width = 352;
    private static final int height = 288;
    private BufferedImage img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private String primaryVideo = null;
    private String secondaryVideo = null;
    private JList jList;
    private Vector<String> links = new Vector<>();
    private JScrollPane jScrollPane;
    private JFrame jFrame;
    private JSlider jSlider;
    private JSlider jSlider2;


    private enum VideoType {
        PRIMARY, SECONDARY
    }

    public AuthView() {

        // Import panel
        JLabel label1 = new JLabel("  Actions");
        JButton importButton = new JButton("Import Primary Video");
        JButton importButton2 = new JButton("Import Secondary Video");
        JButton newButton = new JButton("Create Mew Hyperlink");
        importButton.addActionListener(new ImportButtonListener(VideoType.PRIMARY));
        importButton2.addActionListener(new ImportButtonListener(VideoType.SECONDARY));

        // Hyper Link List
        JLabel label2 = new JLabel("Links");
        jList = new JList();
        jList.setListData(links);
        jList.addMouseListener(new JListListener());

        jScrollPane = new JScrollPane();
        jScrollPane.setPreferredSize(new java.awt.Dimension(200, 80));
        jScrollPane.getViewport().setView(jList);

        JButton saveButton = new JButton("Save File");
        JButton connectButton = new JButton("Connect Video");


        // Left Video
        JLabel lbIm1 = new JLabel(new ImageIcon(img1));
        lbIm1.setPreferredSize(new java.awt.Dimension(width + 20, height + 20));
        jSlider = new JSlider(1, 9000, 1);
        jSlider.setPaintTicks(true);
        jSlider.addChangeListener(new SlideListener(VideoType.PRIMARY));

        // Right Video
        JLabel lbIm2 = new JLabel(new ImageIcon(img2));
        lbIm2.setPreferredSize(new java.awt.Dimension(width + 20, height + 20));
        jSlider2 = new JSlider(1, 9000, 1);
        jSlider2.setPaintTicks(true);
        jSlider2.addChangeListener(new SlideListener(VideoType.SECONDARY));

        jFrame = new JFrame();
        GridBagLayout gLayout = new GridBagLayout();
        jFrame.getContentPane().setLayout(gLayout);

        // Layout
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        jFrame.getContentPane().add(label1, c);
        c.gridx = 0;
        c.gridy = 1;
        jFrame.getContentPane().add(importButton, c);
        c.gridx = 0;
        c.gridy = 2;
        jFrame.getContentPane().add(importButton2, c);
        c.gridx = 0;
        c.gridy = 3;
        jFrame.getContentPane().add(newButton, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        jFrame.getContentPane().add(label2, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 3;
        jFrame.getContentPane().add(jScrollPane, c);

        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        jFrame.getContentPane().add(connectButton, c);

        c.gridx = 3;
        c.gridy = 2;
        jFrame.getContentPane().add(saveButton, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.ipady = 0;
        jFrame.getContentPane().add(lbIm1, c);

        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 2;
        jFrame.getContentPane().add(lbIm2, c);

        c.gridx = 0;
        c.gridy = 9;
        jFrame.getContentPane().add(jSlider, c);


        c.gridx = 2;
        c.gridy = 9;
        jFrame.getContentPane().add(jSlider2, c);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    private class ImportButtonListener implements ActionListener {
        private VideoType videoType; // Primary video or secondary video

        public ImportButtonListener(VideoType videoType) {
            this.videoType = videoType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String path = null;
            JFileChooser fileChooser = new JFileChooser("data/");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            fileChooser.setDialogTitle("Please choose the video folder");
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                path = fileChooser.getSelectedFile().getName();
            }
            // todo: check path
            if (path != null) {
                Frame frame = new Frame(path, 1);
                switch (this.videoType) {
                    case PRIMARY:
                        imageUtil.copyImage(frame.getImg(), img1);
                        jSlider.setValue(0);
                        primaryVideo = path;
                        // Update links
                        links.add("a");
                        links.add("b");
                        jList.setListData(links);
                        break;
                    case SECONDARY:
                        imageUtil.copyImage(frame.getImg(), img2);
                        jSlider2.setValue(0);
                        secondaryVideo = path;
                        break;
                }
                jFrame.repaint();
            }
        }
    }

    private class SlideListener implements ChangeListener {
        private VideoType videoType;

        public SlideListener(VideoType videoType) {
            this.videoType = videoType;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            Frame frame;
            switch (this.videoType) {
                case PRIMARY:
                    if (primaryVideo == null) return;
                    frame = new Frame(primaryVideo, jSlider.getValue());
                    imageUtil.copyImage(frame.getImg(), img1);
                    break;
                case SECONDARY:
                    if (secondaryVideo == null) return;
                    frame = new Frame(secondaryVideo, jSlider2.getValue());
                    imageUtil.copyImage(frame.getImg(), img2);
                    break;
            }
            jFrame.repaint();
        }
    }

    private class NewLinkListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newLink = JOptionPane.showInputDialog("Please enter link name");
            links.add(newLink);
            jList.setListData(links);
        }
    }

    private class JListListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getClickCount() == 2) { // double clicked
                JList myList = (JList) e.getSource();
                int index = myList.getSelectedIndex();
                Object obj = myList.getModel().getElementAt(index);
                System.out.println(obj.toString());
            }

        }
    }

    public static void main(String[] args) {
        AuthView view = new AuthView();

    }

}
