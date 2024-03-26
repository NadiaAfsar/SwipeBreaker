package Models;

import Graphics.GameFrame;
import Logic.GameManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class NewBallItem extends Item{
    private boolean removed;
    public NewBallItem(int x, int y, int level) {
        this.initialX = x;
        this.initialY = y;
        this.x = x + 30;
        this.y = y + 10;
        this.level = level;
        this.width = 30;
        this.height = 30;
        objectJLabel = new JLabel("●");
        objectJLabel.setBounds(this.x,this.y,width,height);
        objectJLabel.setFont(new Font("Serif",Font.PLAIN,32));
        objectJLabel.setForeground(Color.CYAN);
        GameFrame.getGamePanel().add(objectJLabel);
    }
    @Override
    public void ability() {
        GameManager.newBalls++;
    }

    @Override
    public void changePlace(int x, int y) {
        this.x = x;
        this.y = y;
        objectJLabel.setBounds(this.x,this.y,width,height);
    }

    @Override
    public void collided() {
        GameFrame.getGamePanel().remove(objectJLabel);
        ability();
    }

    @Override
    public void changeColor() throws IOException {
        int x = (int)(Math.random()*4);
        if (x == 0){
            objectJLabel.setForeground(Color.CYAN);
            if (removed) {
                GameFrame.getGamePanel().add(objectJLabel);
                removed = false;
            }
        }
        else if (x == 1){
            objectJLabel.setForeground(Color.YELLOW);
            if (removed) {
                GameFrame.getGamePanel().add(objectJLabel);
                removed = false;
            }
        }
        else if (x == 2){
            objectJLabel.setForeground(Color.BLUE);
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
        objectJLabel.setForeground(Color.CYAN);
        if (removed) {
            GameFrame.getGamePanel().add(objectJLabel);
            removed = false;
        }
    }

}
