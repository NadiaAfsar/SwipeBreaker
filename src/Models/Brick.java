package Models;

import Graphics.GameFrame;
import Logic.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Brick extends ObjectInGame{
    private int color;
    private int originalX;
    private int originalY;
    private int score;
    private int initialScore;
    private Timer timer;
    private BufferedImage image1;
    private BufferedImage image2;
    private JLabel scoreJLabel;
    private boolean removed;
    private static long danceStartTime;
    private static long earthQuakeStartTime;
    private static boolean dance;
    private static boolean earthquake;
    private Timer earthQuake;
    public Brick(int x, int y, int color, int score) throws IOException {
        this.color = color;
        this.score = score;
        this.level = score;
        this.initialScore = score;
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        width = 80;
        height = 40;
        setImage(color);
        setScore();
        objectJLabel = new JLabel(new ImageIcon(image));
        objectJLabel.setBounds(x,y,width,height);
        GameFrame.getGamePanel().add(objectJLabel);
    }
    private void setImage(int color) throws IOException {
        if (color >= 0 && color <= 2) {
            image = ImageIO.read(new File("pics/blue.png"));
            image1 = ImageIO.read(new File("pics/blue1.png"));
            image2 = ImageIO.read(new File("pics/blue2.png"));
        }
        else if (color >= 3 && color <= 5) {
            image = ImageIO.read(new File("pics/brown.png"));
            image1 = ImageIO.read(new File("pics/brown1.png"));
            image2 = ImageIO.read(new File("pics/brown2.png"));
        }
        else if (color >= 6 && color <= 8) {
            image = ImageIO.read(new File("pics/pink.jpg"));
            image1 = ImageIO.read(new File("pics/pink1.jpg"));
            image2 = ImageIO.read(new File("pics/pink2.jpg"));
        }
        else if (color >= 9 && color <= 11) {
            image = ImageIO.read(new File("pics/red.png"));
            image1 = ImageIO.read(new File("pics/red1.png"));
            image2 = ImageIO.read(new File("pics/red2.png"));
        }
        else if (color >= 12 && color <= 14){
            image = ImageIO.read(new File("pics/yellow.png"));
            image1 = ImageIO.read(new File("pics/yellow1.png"));
            image2 = ImageIO.read(new File("pics/yellow2.png"));
        }
        else if (color == 15) {
            image = ImageIO.read(new File("pics/dancingLight.jpg"));
            image1 = ImageIO.read(new File("pics/dancingLight1.jpg"));
            image2 = ImageIO.read(new File("pics/dancingLight2.jpg"));
        }
        else {
            image = ImageIO.read(new File("pics/earthquake.png"));
            image1 = ImageIO.read(new File("pics/earthquake1.png"));
            image2 = ImageIO.read(new File("pics/earthquake2.png"));
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    private void setScore() {
        if (isSimple()) {
            scoreJLabel = new JLabel(score+"");
            scoreJLabel.setBounds(x + 45, y, 100, 50);
            scoreJLabel.setFont(new Font("Serif", Font.PLAIN, 28));
            scoreJLabel.setForeground(Color.BLACK);
            GameFrame.getGamePanel().add(scoreJLabel);
        }
    }


    public JLabel getScoreJLabel() {
        return scoreJLabel;
    }

    @Override
    public void changePlace(int x, int y) {
        this.x = x;
        int dy = y - this.y;
        this.y = y;
        this.originalY += dy;
        if (isSimple()) {
            scoreJLabel.setBounds(x + 45, y, 80, 40);
        }
        objectJLabel.setBounds(x,y,100,50);
    }

    @Override
    public void collided() throws IOException {
        GameFrame.getGamePanel().remove(objectJLabel);
        if (color == 15) {
            dancingLight();
        }
        else if (color == 16) {
            earthquake();
        }
        else {
        GameFrame.getGamePanel().remove(scoreJLabel);
            score -= GameManager.ballsPower;
            if (isSimple()) {
                if (score > 0) {
                    setScore();
                    GameFrame.getGamePanel().add(objectJLabel);
                }

            }
        }
    }

    public int getScore() {
        return score;
    };

    public int getLevel() {
        return level;
    }
    public void dancingLight() {
        if (dance) {
            danceStartTime += 10000;
        } else {
            danceStartTime = System.currentTimeMillis();
            dance = true;
            timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < GameManager.getObjects().size(); i++) {
                        try {
                            GameManager.getObjects().get(i).changeColor();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    for (int i = 0; i < GameManager.getReleasedBalls().size(); i++) {
                        GameManager.getReleasedBalls().get(i).changeColor();
                    }
                    changeBackGround();
                    if (System.currentTimeMillis() - danceStartTime >= 10000) {
                        timer.stop();
                        dance = false;
                        for (int i = 0; i < GameManager.getObjects().size(); i++) {
                            try {
                                GameManager.getObjects().get(i).backToInitial();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        for (int i = 0; i < GameManager.getReleasedBalls().size(); i++) {
                            GameManager.getReleasedBalls().get(i).backToInitial();
                        }
                        GameManager.changeBackGround("pics/background - Copy.jpg");
                    }
                }
            });
            timer.start();
        }
    }
    public void earthquake() {
        if (earthquake) {
            earthQuakeStartTime += 10000;
        }
        else {
            earthQuakeStartTime = System.currentTimeMillis();
            earthquake = true;
            earthQuake = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < GameManager.getObjects().size(); i++) {
                        ObjectInGame object = GameManager.getObjects().get(i);
                        if (object instanceof Brick) {
                            ((Brick) object).changeSize();
                        }
                    }
                    if (System.currentTimeMillis() - earthQuakeStartTime >= 10000) {
                        earthQuake.stop();
                        for (int i = 0; i < GameManager.getObjects().size(); i++) {
                            ObjectInGame object = GameManager.getObjects().get(i);
                            if (object instanceof Brick) {
                                ((Brick) object).backToOriginal();
                            }
                        }
                    }
                }
            });
            earthQuake.start();
        }
    }
    public void changeColor() throws IOException {
        if (isSimple()) {
            int x = (int) (Math.random() * 11);
            if (x >= 0 && x <= 9) {
                setImage(x);
                objectJLabel.setIcon(new ImageIcon(image));
                if (removed) {
                    GameFrame.getGamePanel().add(scoreJLabel);
                    GameFrame.getGamePanel().add(objectJLabel);

                    removed = false;
                }
            } else {
                GameFrame.getGamePanel().remove(objectJLabel);
                GameFrame.getGamePanel().remove(scoreJLabel);

                removed = true;
            }
        }
    }
    private void changeBackGround() {
        int x = (int)(Math.random()*3);
        if (x == 0){
            GameManager.changeBackGround("pics/background - Copy.jpg");
        }
        else if (x == 1) {
            GameManager.changeBackGround("pics/background1.jpg");
        }
        else {
            GameManager.changeBackGround("pics/background2.jpg");
        }
    }
    private boolean isSimple() {
        return (color != 15 && color != 16);
    }
    public void backToInitial() throws IOException {
        if (isSimple()) {
            setImage(color);
            if (removed) {
                GameFrame.getGamePanel().add(scoreJLabel);
                GameFrame.getGamePanel().add(objectJLabel);

                removed = false;
            }
        }
    }

    public int getInitialScore() {
        return initialScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getColor() {
        return color;
    }
    private void changeSize() {
        int x = (int)(Math.random()*3);
        if (x == 0) {
            this.x = originalX;
            this.y = originalY;
            width = 80;
            height = 40;
            objectJLabel.setBounds(this.x,this.y,width,height);
            objectJLabel.setIcon(new ImageIcon(image));
        }
        else if (x == 1) {
            this.x = originalX+10;
            this.y = originalY+5;
            width = 60;
            height = 30;
            objectJLabel.setBounds(this.x,this.y,width,height);
            objectJLabel.setIcon(new ImageIcon(image1));
        }
        else if (x == 2) {
            this.x = originalX-10;
            this.y = originalY-5;
            width = 100;
            height = 50;
            objectJLabel.setBounds(this.x,this.y,width,height);
            objectJLabel.setIcon(new ImageIcon(image2));
        }
    }
    private void backToOriginal() {
        this.x = originalX;
        this.y = originalY;
        width = 80;
        height = 40;
        objectJLabel.setBounds(this.x,this.y,width,height);
        objectJLabel.setIcon(new ImageIcon(image));
    }
}
