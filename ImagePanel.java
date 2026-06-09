/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

/**
 *
 * @author Sojeong
 */

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private Image backgroundImage;

    public ImagePanel(String fileName) {

        backgroundImage =
                new ImageIcon(fileName).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(
                backgroundImage,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );
    }
}