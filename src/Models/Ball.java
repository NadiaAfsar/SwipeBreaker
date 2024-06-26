package Models;

import Graphics.GameFrame;

import javax.swing.*;
import java.awt.*;

public class Ball {
    double x;
    double y;
    double dx;
    double dy;
    int colorNumber;
    Color color;
    JLabel ballJLabel;
    private boolean removed;
    public Ball(double x, double y, int color) {
        this.x = x;
        this.y = y;
        this.colorNumber = color;
        setColor(colorNumber);
        ballJLabel = new JLabel("●");
        ballJLabel.setBounds((int)x,(int)y,100,100);
        ballJLabel.setFont(new Font("Serif",Font.PLAIN,30));
        ballJLabel.setForeground(this.color);
        GameFrame.getGamePanel().add(ballJLabel);
    }
    private void setColor(int colorNumber) {
        if (colorNumber == 0) {
            color = Color.GREEN;
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
        return (int)x+13;
    }
    public int getOy() {
        return (int)y+50;
    }
    public void setPosition(double x, double y) {
        ballJLabel.setBounds((int)x, (int)y, 100, 50);
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
    public void changeColor() {
        int x = (int)(Math.random()*6);
        if (x <= 4) {
            setColor(x);
            ballJLabel.setForeground(this.color);
            if (removed) {
                GameFrame.getGamePanel().add(ballJLabel);
            }
        }
        else {
            GameFrame.getGamePanel().remove(ballJLabel);
            removed = true;
        }
    }
    public void backToInitial() {
        setColor(colorNumber);
        ballJLabel.setForeground(this.color);
        if (removed) {
            GameFrame.getGamePanel().add(ballJLabel);
        }
    }
}
