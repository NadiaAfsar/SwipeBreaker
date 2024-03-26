package Models;

import Graphics.GameFrame;
import Logic.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VertigoItem extends Item{
    public VertigoItem(int x, int y, int level) throws IOException {
        this.initialX = x;
        this.initialY = y;
        this.x = x + 25;
        this.y = y + 5;
        this.level = level;
        this.width = 30;
        this.height = 30;
        setImage();
        objectJLabel = new JLabel(new ImageIcon(image));
        objectJLabel.setBounds(this.x, this.y,width,height);
        GameFrame.getGamePanel().add(objectJLabel);
    }
    private void setImage() throws IOException {
        image = ImageIO.read(new File("pics/vertigo.png"));
        image1 = ImageIO.read(new File("pics/vertigo1.png"));
        image2 = ImageIO.read(new File("pics/vertigo2.png"));
    }
    @Override
    public void ability() {
        GameManager.vertigo = true;
    }

    @Override
    public void changePlace(int x, int y) {
        this.x = x;
        this.y = y;
        objectJLabel.setBounds(this.x, this.y,width,height);
    }

    @Override
    public void collided() {
        GameFrame.getGamePanel().remove(objectJLabel);
        ability();
    }


}
