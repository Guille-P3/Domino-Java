package Domino;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RotatorImage {
    private JLabel imageLabel;
    private BufferedImage originalImage;
    private BufferedImage currentImage;
    private int rotationDegrees = 0;
    private File file;

    RotatorImage(File file, JLabel imageLabel) {
        this.file = file;
        this.imageLabel = imageLabel;
    }

    public void loadImage() {
        try {
            originalImage = ImageIO.read(this.file);
            currentImage = originalImage;
            displayImage(currentImage);
            rotationDegrees = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
    }

    public void rotateImage(int degrees) {
        if (originalImage != null) {
            rotationDegrees = (rotationDegrees + degrees) % 360;

            double radians = Math.toRadians(rotationDegrees);
            double sin = Math.abs(Math.sin(radians));
            double cos = Math.abs(Math.cos(radians));
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int newWidth = (int) Math.floor(width * cos + height * sin);
            int newHeight = (int) Math.floor(height * cos + width * sin);

            BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
            Graphics2D g2d = rotatedImage.createGraphics();
            AffineTransform at = new AffineTransform();
            at.translate((newWidth - width) / 2, (newHeight - height) / 2);
            at.rotate(radians, width / 2, height / 2);
            g2d.setTransform(at);
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            currentImage = rotatedImage;
            displayImage(currentImage);
        }
    }

    public BufferedImage getCurrentImage() {
        return this.currentImage;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
        displayImage(currentImage);
    }
}
