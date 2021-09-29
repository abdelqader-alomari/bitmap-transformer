package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bitmap {
    String inputPath;
    String outputPath;
    String newImageName;

    BufferedImage image = null;

    public Bitmap(String inputPath, String outputPath, String newImageName) throws IOException {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.newImageName = newImageName;
        try {
            this.image = ImageIO.read(new File(inputPath));

        } catch (IOException ioException) {
            System.out.println(ioException);
        }

    }

    public void waterMark(String text) {
        File sourceImageFile = new File(inputPath);
        File destImageFile = new File(outputPath + newImageName);

        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 60));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            // paints the textual watermark
            g2d.drawString(text, centerX, centerY);

            ImageIO.write(sourceImage, "png", destImageFile);
            g2d.dispose();

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void NegativeColor() throws IOException {
        File sourceImageFile = new File(inputPath);
        File destImageFile = new File(outputPath + newImageName);
        BufferedImage sourceImage = ImageIO.read(sourceImageFile);
        for (int x = 0; x < sourceImage.getWidth(); x++) {
            for (int y = 0; y < sourceImage.getHeight(); y++) {
                int rgba = sourceImage.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue());
                sourceImage.setRGB(x, y, col.getRGB());

            }
        }
        ImageIO.write(sourceImage, "png", destImageFile);
    }

    public void grey() throws IOException {

        File sourceImageFile = new File(inputPath);
        File destImageFile = new File(outputPath + newImageName);
        BufferedImage sourceImage = ImageIO.read(sourceImageFile);

        BufferedImage grey = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(),
                BufferedImage.TYPE_USHORT_GRAY);
        Graphics modifications = grey.getGraphics();
        modifications.drawImage(this.image, 0, 0, null);
        modifications.dispose();

        ImageIO.write(grey, "png", destImageFile);

    }

    public void reverseImageVertically() throws IOException {

        File sourceImageFile = new File(inputPath);
        File destImageFile = new File(outputPath + newImageName);
        BufferedImage sourceImage = ImageIO.read(sourceImageFile);

        for (int i = 0; i < sourceImage.getWidth(); i++) {
            for (int j = 0; j < sourceImage.getHeight() / 2; j++) {
                int temp = sourceImage.getRGB(i, j);
                sourceImage.setRGB(i, j, sourceImage.getRGB(i, sourceImage.getHeight() - j - 1));
                sourceImage.setRGB(i, sourceImage.getHeight() - j - 1, temp);
            }
        }
        ImageIO.write(sourceImage, "png", destImageFile);
    }

    public void reverseImageHorizontally() throws IOException {
        File sourceImageFile = new File(inputPath);
        File destImageFile = new File(outputPath + newImageName);
        BufferedImage sourceImage = ImageIO.read(sourceImageFile);

        for (int i = 0; i < sourceImage.getHeight() / 2; i++) {
            for (int j = 0; j < sourceImage.getWidth(); j++) {
                int temp = sourceImage.getRGB(i, j);
                sourceImage.setRGB(i, j, sourceImage.getRGB(sourceImage.getWidth() - i - 1, j));
                sourceImage.setRGB(sourceImage.getWidth() - i - 1, j, temp);
            }
        }
        ImageIO.write(sourceImage, "png", destImageFile);
    }

}
