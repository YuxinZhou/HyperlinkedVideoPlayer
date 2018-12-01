package View;

import Model.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class AuthView {
    private static final int width = 352;
    private static final int height = 288;
    private BufferedImage img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private JList jList;
    private String[] links = new String[]{"l1", "l2", "l3", "l4", "l5"};
    private JScrollPane jScrollPane;
    private JFrame jFrame;


    public AuthView() {


        // Import panel
        JLabel label1 = new JLabel("  Actions");
        JButton importButton = new JButton("Import Primary Video");
        JButton importButton2 = new JButton("Import Secondary Video");
        JButton newButton = new JButton("Create Mew Hyperlink");


        // Hyper Link List
        JLabel label2 = new JLabel("Links");
        jList = new JList();
        jList.setListData(links);
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // double clicked
                    JList myList = (JList) e.getSource();
                    int index = myList.getSelectedIndex();
                    Object obj = myList.getModel().getElementAt(index);
                    System.out.println(obj.toString());
                }
            }
        });

        jScrollPane = new JScrollPane();
        jScrollPane.setPreferredSize(new java.awt.Dimension(200, 80));
        jScrollPane.getViewport().setView(jList);

        JButton saveButton = new JButton("Save File");
        JButton connectButton = new JButton("Connect Video");



        // Left Video
        JLabel lbIm1 = new JLabel(new ImageIcon(img1));
        lbIm1.setPreferredSize(new java.awt.Dimension(width + 20, height + 20));
        JSlider jSlider = new JSlider(0, 9000);
        jSlider.setPaintTicks(true);

        // Right Video
        JLabel lbIm2 = new JLabel(new ImageIcon(img2));
        lbIm2.setPreferredSize(new java.awt.Dimension(width + 20, height + 20));
        JSlider jSlider2 = new  JSlider(0, 9000);
        jSlider2.setPaintTicks(true);

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


    public static void main(String[] args) {
        AuthView view = new AuthView();

    }

}
