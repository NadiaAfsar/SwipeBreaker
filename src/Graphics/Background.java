package Graphics;

import Logic.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends JPanel {
    private BufferedImage backGround;

    public Background(String path) {
        try {
            backGround = ImageIO.read(new File(path));
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
        if (GameManager.getArrow() != null) {
            GameManager.getArrow().draw(g);
        }
    }


}
