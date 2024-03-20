package Models;

import Graphics.GameFrame;

import javax.swing.*;
import java.awt.*;

public class Ball {
    int x;
    int y;
    int colorNumber;
    Color color;
    JLabel ballJLabel;
    public Ball(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.colorNumber = color;
        setColor();
        ballJLabel = new JLabel("●");
        ballJLabel.setBounds(x,y,100,100);
        ballJLabel.setFont(new Font("Serif",Font.PLAIN,50));
        ballJLabel.setForeground(this.color);
        GameFrame.getGamePanel().add(ballJLabel);
    }
    private void setColor() {
        if (colorNumber == 0) {
            color = Color.BLACK;
        }
        else if (colorNumber == 1) {
            color = Color.WHITE;
        }
        else if (colorNumber == 2) {
            color = Color.BLUE;
        }
        else if (colorNumber == 3) {
            color = Color.RED;
        }
        else {
            color = Color.YELLOW;
        }
    }

    public JLabel getBallJLabel() {
        return ballJLabel;
    }
    public int getOx() {
        return x+20;
    }
    public int getOy() {
        return y+50;
    }
    public void setPosition(int x, int y) {
        GameFrame.getGamePanel().remove(ballJLabel);
        ballJLabel.setBounds(x, y, 100, 50);
        GameFrame.getGamePanel().add(ballJLabel);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColorNumber() {
        return colorNumber;
    }
}
