package View;

import Model.*;
import Model.Frame;
import Util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.text.MaskFormatter;
import java.util.Vector;

public class AuthView {
    private static final int width = 352;
    private static final int height = 288;
    private BufferedImage img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private Frame frame1;
    private Frame frame2;
    private String primaryVideo;
    private String secondaryVideo;
    private HyperVideo hyperVideo;
    private JList jList;
    private Vector<String> links = new Vector<>();
    private JScrollPane jScrollPane;
    private JFrame jFrame;
    private JSlider jSlider;
    private JSlider jSlider2;
    private JLabel lbIm1;
    private JLabel lbIm2;
    private String linkSelected;
    private Rectangle curObj;
    private JFormattedTextField inputField1;
    private JFormattedTextField inputField2;


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
        newButton.addActionListener(new NewLinkListener());

        // Hyper Link List
        JLabel label2 = new JLabel("Links");
        jList = new JList();
        jList.setListData(links);
        jList.addMouseListener(new JListListener());

        jScrollPane = new JScrollPane();
        jScrollPane.setPreferredSize(new Dimension(200, 80));
        jScrollPane.getViewport().setView(jList);

        JButton saveButton = new JButton("Save File");
        JButton connectButton = new JButton("Connect Video");
        connectButton.addActionListener(new ConnectFileListener());
        saveButton.addActionListener(new SaveFileListener());


        // Left Video
        lbIm1 = new JLabel(new ImageIcon(img1));
        lbIm1.setPreferredSize(new Dimension(width + 20, height + 20));
        jSlider = new JSlider(1, 9000, 1);
        jSlider.setPaintTicks(true);
        jSlider.addChangeListener(new SlideListener(VideoType.PRIMARY));
        try {
            MaskFormatter formatter = new MaskFormatter("####");
            inputField1 = new JFormattedTextField(formatter);
            inputField1.addActionListener(new InputFieldListener(VideoType.PRIMARY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Right Video
        lbIm2 = new JLabel(new ImageIcon(img2));
        lbIm2.setPreferredSize(new Dimension(width + 20, height + 20));
        jSlider2 = new JSlider(1, 9000, 1);
        jSlider2.setPaintTicks(true);
        jSlider2.addChangeListener(new SlideListener(VideoType.SECONDARY));
        try {
            MaskFormatter formatter = new MaskFormatter("####");
            inputField2 = new JFormattedTextField(formatter);
            inputField2.addActionListener(new InputFieldListener(VideoType.SECONDARY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // JFrame
        jFrame = new JFrame();
        GridBagLayout gLayout = new GridBagLayout();
        jFrame.getContentPane().setLayout(gLayout);
        jFrame.addMouseMotionListener(new JFrameListner());
        jFrame.addMouseListener(new JFrameListner());


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
        c.gridwidth = 1;
        jFrame.getContentPane().add(jSlider, c);
        jSlider.setPreferredSize(new Dimension(250, 20));

        c.gridx = 1;
        c.gridy = 9;
        jFrame.getContentPane().add(inputField1, c);
        //inputField1.setPreferredSize(new Dimension(50, 20));

        c.gridx = 2;
        c.gridy = 9;
        jFrame.getContentPane().add(jSlider2, c);
        jSlider2.setPreferredSize(new Dimension(280, 20));

        c.gridx = 3;
        c.gridy = 9;
        c.gridwidth = GridBagConstraints.REMAINDER;
        jFrame.getContentPane().add(inputField2, c);
        // inputField2.setPreferredSize(new Dimension(20, 20));

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public void movePrimaryTo(int goTo) {
        if (primaryVideo == null) return;
        if (goTo < 1) goTo = 1;
        if (goTo > 9000) goTo = 9000;

        frame1 = new Frame(primaryVideo, goTo, hyperVideo.getLinksByFrame(goTo));
        imageUtil.copyImage(frame1.getImg(), img1);
        jSlider.setValue(goTo);
        inputField1.setValue(goTo);
    }

    public void moveSecondaryTo(int goTo) {
        if (secondaryVideo == null) return;
        if (goTo < 1) goTo = 1;
        if (goTo > 9000) goTo = 9000;

        frame2 = new Frame(secondaryVideo, goTo);
        imageUtil.copyImage(frame2.getImg(), img2);
        jSlider2.setValue(goTo);
        inputField2.setValue(goTo);
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
                switch (this.videoType) {
                    case PRIMARY:
                        primaryVideo = path;
                        String filePath = "json/" + primaryVideo + ".json";

                        if (new File(filePath).isFile()) {
                            hyperVideo = HyperVideoFileHelper.readHyperVideoFromFile(filePath);
                        } else {
                            hyperVideo = new HyperVideo("", primaryVideo);
                        }

                        // refresh link list
                        links.clear();
                        for (String l : hyperVideo.getAllLinkNames()) {
                            links.add(l);
                        }
                        jList.setListData(links);

                        // refresh frame
                        movePrimaryTo(1);
                        break;
                    case SECONDARY:
                        secondaryVideo = path;
                        moveSecondaryTo(1);
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
            switch (videoType) {
                case PRIMARY:
                    movePrimaryTo(jSlider.getValue());
                    break;
                case SECONDARY:
                    moveSecondaryTo(jSlider2.getValue());
                    break;
            }
            jFrame.repaint();
        }
    }


    private class InputFieldListener implements ActionListener {
        private VideoType videoType;

        public InputFieldListener(VideoType videoType) {
            this.videoType = videoType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                switch (videoType) {
                    case PRIMARY:
                        movePrimaryTo(Integer.valueOf(inputField1.getValue().toString()));
                        break;
                    case SECONDARY:
                        moveSecondaryTo(Integer.valueOf(inputField2.getValue().toString()));
                        break;
                }
                jFrame.repaint();
            } catch (Exception ex) {
            }
        }
    }

    private class NewLinkListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newLink = JOptionPane.showInputDialog("Please enter the link name");
            if (newLink == null || newLink.equals(""))
                return;
            // Can not have same name!
            while (links.contains(newLink)) {
                JOptionPane.showMessageDialog(null, "Name already exists.", "Error!", JOptionPane.ERROR_MESSAGE);
                newLink = JOptionPane.showInputDialog("Please enter the link name", newLink);
            }
            links.add(newLink);
            jList.setListData(links);
        }
    }

    private class JListListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JList myList = (JList) e.getSource();
            int index = myList.getSelectedIndex();
            if (index == -1) { // empty list
                return;
            }
            Object obj = myList.getModel().getElementAt(index);

            if (e.getClickCount() == 2) { // double clicked -> edit link
                String oldLink = obj.toString();
                String newLink = JOptionPane.showInputDialog("Please enter the link name", obj.toString());
                while (links.contains(newLink)) {
                    JOptionPane.showMessageDialog(null, "Name already exists.", "Error!", JOptionPane.ERROR_MESSAGE);
                    newLink = JOptionPane.showInputDialog("Please enter the link name", newLink);
                }
                if (newLink == null) { // canceled
                    return;
                } else if (newLink.equals("")) { // deleted
                    links.remove(index);
                } else { // updated
                    links.remove(index);
                    links.add(index, newLink);
                    hyperVideo.renameHyperLinks(oldLink, newLink);
                }
                jList.setListData(links);
            }

            if (e.getClickCount() == 1) {
                System.out.println(linkSelected + " is selected");
                linkSelected = obj.toString();
                int firstFrame = hyperVideo.getFirstFrameByName(linkSelected);
                Frame linkedFrame = hyperVideo.getLinkedFrameByName(linkSelected);
                System.out.println(firstFrame);
                System.out.println(linkedFrame);
                if (firstFrame == -1 || linkedFrame == null)
                    return;
                movePrimaryTo(firstFrame);
                secondaryVideo = linkedFrame.getVideoName();
                moveSecondaryTo(linkedFrame.getId());
            }
        }
    }

    private class ConnectFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (primaryVideo == null || jList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a link from list", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (curObj == null) {
                JOptionPane.showMessageDialog(null, "Please select an object", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (secondaryVideo == null) {
                JOptionPane.showMessageDialog(null, "Please choose secondary video", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                linkSelected = jList.getSelectedValue().toString();
                hyperVideo.newHyperLink(linkSelected, jSlider.getValue(),
                        viewUtil.rectToArray(curObj), secondaryVideo, jSlider2.getValue());
                JOptionPane.showMessageDialog(null, "New hyperlink (" + linkSelected + ") created!");
                movePrimaryTo(jSlider.getValue());  // Refresh
                jFrame.repaint();
            }
        }
    }

    private class SaveFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = "json/" + primaryVideo + ".json";
            if (hyperVideo == null)
                return;
            HyperVideoFileHelper.saveHyperVideoToFile(hyperVideo, filePath);
            JOptionPane.showMessageDialog(null, "File saved!");
        }
    }

    private class JFrameListner extends MouseAdapter {
        private Point start;
        private Point end;


        @Override
        public void mouseMoved(MouseEvent e) {
            Rectangle bound = new Rectangle(lbIm1.getBounds().x + 10, lbIm1.getBounds().y + 10, width, height);
            int x = MouseInfo.getPointerInfo().getLocation().x - jFrame.getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y - jFrame.getLocation().x;
            Point p = new Point(x, y);

            if (primaryVideo != null && bound.contains(p)) {
                jFrame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            } else {
                jFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Rectangle bound = new Rectangle(lbIm1.getBounds().x + 10, lbIm1.getBounds().y + 10, width, height);
            if (primaryVideo != null && bound.contains(e.getPoint())) {  // Draw start
                start = e.getPoint();
                imageUtil.copyImage(frame1.getImg(), img1);
                jFrame.repaint();
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int x0 = lbIm1.getBounds().x + 10;
            int y0 = lbIm1.getBounds().y + 10;
            Rectangle bound = new Rectangle(x0, y0, width, height);
            if (primaryVideo != null && start != null && bound.contains(e.getPoint())) {
                end = e.getPoint();
                curObj = viewUtil.createRect(start, end, new Point(x0, y0));
                start = null;
                end = null;
                System.out.println(curObj);
                imageUtil.drawRectangle(img1, curObj, imageUtil.Red);
                jFrame.repaint();
            }
        }
    }

    public static void main(String[] args) {
        AuthView view = new AuthView();

    }


}
