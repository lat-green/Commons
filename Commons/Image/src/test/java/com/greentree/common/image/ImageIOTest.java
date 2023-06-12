package com.greentree.common.image;

import com.greentree.commons.image.ImageIODecoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ImageIOTest {

    public static void main(String[] args) throws Exception {
        var window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try (final var in = ImageIOTest.class.getClassLoader().getResourceAsStream("box.jpg")) {
            var img = ImageIODecoder.toBufferedImage(ImageIODecoder.toImageData(ImageIO.read(in)));
            window.add(new ImageViewer(img));
        }
        window.pack();
        window.setSize(800, 600);
        window.setVisible(true);
    }

    private static class ImageViewer extends JPanel {

        private final Image img;

        public ImageViewer(Image img) {
            this.img = img;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(img, 0, 0, 200, 200, null);
        }

    }

}
