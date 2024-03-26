package Models;

import Graphics.GameFrame;
import Logic.GameManager;
import Graphics.Background;

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
    private int score;
    private int initialScore;
    private Timer timer;
    private BufferedImage image1;
    private BufferedImage image2;
    private JLabel scoreJLabel;
    private boolean removed;
    private long startTime;
    public Brick(int x, int y, int color, int score) throws IOException {
        this.color = color;
        this.score = score;
        this.level = score;
        this.initialScore = score;
        this.x = x;
        this.y = y;
        width = 80;
        height = 40;
        setImage(color);
        setScore();
        objectJLabel = new JLabel(new ImageIcon(image));
        objectJLabel.setBounds(x,y,width,height);
        GameFrame.getGamePanel().add(objectJLabel);
    }
    private void setImage(int color) throws IOException {
        if (color == 0 || color == 1) {
            image = ImageIO.read(new File("pics/blue.png"));
            image1 = ImageIO.read(new File("pics/blue1.png"));
            image2 = ImageIO.read(new File("pics/blue2.png"));
        }
        else if (color == 2 || color == 3) {
            image = ImageIO.read(new File("pics/brown.png"));
            image1 = ImageIO.read(new File("pics/brown1.png"));
            image2 = ImageIO.read(new File("pics/brown2.png"));
        }
        else if (color == 4 || color == 5) {
            image = ImageIO.read(new File("pics/pink.jpg"));
            image1 = ImageIO.read(new File("pics/pink1.jpg"));
            image2 = ImageIO.read(new File("pics/pink2.jpg"));
        }
        else if (color == 6 || color == 7) {
            image = ImageIO.read(new File("pics/red.png"));
            image1 = ImageIO.read(new File("pics/red1.png"));
            image2 = ImageIO.read(new File("pics/red2.png"));
        }
        else if (color == 8 || color == 9){
            image = ImageIO.read(new File("pics/yellow.png"));
            image1 = ImageIO.read(new File("pics/yellow1.png"));
            image2 = ImageIO.read(new File("pics/yellow2.png"));
        }
        else if (color == 10) {
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
        this.y = y;
        if (isSimple()) {
            scoreJLabel.setBounds(x + 45, y, 80, 40);
        }
        objectJLabel.setBounds(x,y,100,50);
    }

    @Override
    public void collided() throws IOException {
        GameFrame.getGamePanel().remove(objectJLabel);
        if (color == 10) {
            dancingLight();
        }
        else if (color == 11) {
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
        startTime = System.currentTimeMillis();
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
                if (System.currentTimeMillis() - startTime >= 10000) {
                    timer.stop();
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
    public void earthquake() {

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
        return (color != 10 && color != 11);
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
}
