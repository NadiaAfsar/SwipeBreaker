package Models;

import Graphics.GameFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Item extends ObjectInGame{
    protected BufferedImage image1;
    protected BufferedImage image2;
    private boolean removed;
    public int initialX;
    public int initialY;
    public abstract void ability();
    @Override
    public void changeColor() throws IOException {
        int x = (int)(Math.random()*4);
        if (x == 0) {
            objectJLabel.setIcon(new ImageIcon(image));
            if (removed) {
                GameFrame.getGamePanel().add(objectJLabel);
                removed = false;
            }
        }
        else if (x == 1) {
            objectJLabel.setIcon(new ImageIcon(image1));
            if (removed) {
                GameFrame.getGamePanel().add(objectJLabel);
                removed = false;
            }
        }
        else if (x == 3) {
            objectJLabel.setIcon(new ImageIcon(image2));
            if (removed) {
                GameFrame.getGamePanel().add(objectJLabel);
                removed = false;
            }
        }
        else {
            GameFrame.getGamePanel().remove(objectJLabel);
            removed = true;
        }
    }

    @Override
    public void backToInitial() throws IOException {
        objectJLabel.setIcon(new ImageIcon(image));
        if (removed) {
            GameFrame.getGamePanel().add(objectJLabel);
            removed = false;
        }
    }
    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }
}
