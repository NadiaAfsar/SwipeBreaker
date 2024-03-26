package Models;

import Graphics.GameFrame;
import Logic.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PowerItem extends  Item{
    public PowerItem(int x, int y, int level) throws IOException {
        this.initialX = x;
        this.initialY = y;
        this.x = x + 25;
        this.y = y + 4;
        this.level = level;
        this.width = 30;
        this.height = 33;
        setImage();
        objectJLabel = new JLabel(new ImageIcon(image));
        objectJLabel.setBounds(this.x, this.y, width, height);
        GameFrame.getGamePanel().add(objectJLabel);
    }
    private void setImage() throws IOException {
        image = ImageIO.read(new File("pics/power.png"));
        image1 = ImageIO.read(new File("pics/power1.png"));
        image2 = ImageIO.read(new File("pics/power2.png"));
    }
    @Override
    public void ability() {
        GameManager.ballsPower = 2;
        new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.ballsPower = 1;
            }
        }).start();
    }

    @Override
    public void changePlace(int x, int y) {
        this.x = x;
        this.y = y;
        objectJLabel.setBounds(this.x, this.y, width, height);
    }

    @Override
    public void collided() {
        GameFrame.getGamePanel().remove(objectJLabel);
        ability();
    }

}
