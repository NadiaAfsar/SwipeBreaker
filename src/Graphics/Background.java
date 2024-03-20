package Graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends JPanel {
    private BufferedImage backGround;

    public Background() {
        try {
            backGround = ImageIO.read(new File("pics/background.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setBounds(0,0,900,650);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0,0,900,650,null);
        }
    }


}
