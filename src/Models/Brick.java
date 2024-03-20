package Models;

import Graphics.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Brick {
    int x;
    int y;
    int color;
    BufferedImage image;
    JLabel brickJLabel;
    int score;
    JLabel scoreJLabel;
    public Brick(int x, int y, int color, int score) throws IOException {
        this.color = color;
        this.score = score;
        this.x = x;
        this.y = y;
        setImage();
        setScore();
        brickJLabel = new JLabel(new ImageIcon(image));
        brickJLabel.setBounds(x,y,100,50);
        GameFrame.getGamePanel().add(brickJLabel);
    }
    private void setImage() throws IOException {
        if (color == 0) {
            image = ImageIO.read(new File("pics/blue.png"));
        }
        else if (color == 1) {
            image = ImageIO.read(new File("pics/brown.png"));
        }
        else if (color == 2) {
            image = ImageIO.read(new File("pics/pink.jpg"));
        }
        else if (color == 3) {
            image = ImageIO.read(new File("pics/red.png"));
        }
        else {
            image = ImageIO.read(new File("pics/yellow.png"));
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    private void setScore() {
        String s = this.score+"";
        scoreJLabel = new JLabel(s);
        scoreJLabel.setBounds(x+45,y,100,50);
        scoreJLabel.setFont(new Font("Serif", Font.PLAIN, 28));
        scoreJLabel.setForeground(Color.BLACK);
        GameFrame.getGamePanel().add(scoreJLabel);
    }
    public void changePlace(int x, int y) {
        this.x = x;
        this.y = y;
        GameFrame.getGamePanel().remove(scoreJLabel);
        GameFrame.getGamePanel().remove(brickJLabel);
        scoreJLabel.setBounds(x+45,y,100,50);
        brickJLabel.setBounds(x,y,100,50);
        GameFrame.getGamePanel().add(scoreJLabel);
        GameFrame.getGamePanel().add(brickJLabel);
    }

    public JLabel getBrickJLabel() {
        return brickJLabel;
    }

    public JLabel getScoreJLabel() {
        return scoreJLabel;
    }
}
