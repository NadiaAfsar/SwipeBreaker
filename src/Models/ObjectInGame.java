package Models;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class ObjectInGame {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected BufferedImage image;
    protected JLabel objectJLabel;
    public int level;

    public abstract void changePlace(int x, int y);

    public abstract void collided() throws IOException;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JLabel getObjectJLabel() {
        return objectJLabel;
    }
    public abstract void changeColor()throws IOException;
    public abstract void backToInitial() throws IOException;

    public int getLevel() {
        return level;
    }
}
