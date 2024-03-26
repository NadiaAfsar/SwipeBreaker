package Models;

import Graphics.GameFrame;
import Logic.GameManager;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BombItem extends Item{
    BufferedImage explosionImage;
    JLabel explosionJLabel;
    Clip clip;
    AudioInputStream sound;
    public BombItem(int x, int y, int level) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.initialX = x;
        this.initialY = y;
        this.x = x + 25;
        this.y = y + 3;
        this.width = 30;
        this.level = level;
        this.height = 34;
        setImage();
        objectJLabel = new JLabel(new ImageIcon(image));
        objectJLabel.setBounds(this.x, this.y,width,height);
        GameFrame.getGamePanel().add(objectJLabel);
        explosionJLabel = new JLabel(new ImageIcon(explosionImage));
        File music = new File("audios/explosion.wav");
        sound = AudioSystem.getAudioInputStream(music);
        clip = AudioSystem.getClip();
        clip.open(sound);
    }
    private void setImage() throws IOException {
        image = ImageIO.read(new File("pics/bomb.png"));
        image1 = ImageIO.read(new File("pics/bomb1.png"));
        image2 = ImageIO.read(new File("pics/bomb2.png"));
        explosionImage = ImageIO.read(new File("pics/explosion.png"));
    }
    @Override
    public void ability() {
        explode();
        explosionJLabel.setBounds(this.x-90,this.y-100,220,229);
        GameFrame.getGamePanel().add(explosionJLabel);
        clip.start();
        new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.getGamePanel().remove(explosionJLabel);
            }
        }).start();
    }

    @Override
    public void collided() {
        GameFrame.getGamePanel().remove(objectJLabel);
        ability();
    }



    @Override
    public void changePlace(int x, int y) {
        this.x = x;
        this.y = y;
        objectJLabel.setBounds(this.x,this.y,width,height);
    }
    private void explode() {
        int[] xs = new int[]{this.x-80,this.x,this.x+80,this.x-80,this.x+80,this.x-80,this.x,this.x+80};
        int[] ys = new int[]{this.y-40,this.y-40,this.y-40,this.y,this.y,this.y+40,this.y+40,this.y+40};
        for (int i = 0; i < 8; i++) {
            ObjectInGame object = GameManager.isFull.get(xs[i]+"&"+ys[i]);
            if (object != null) {
                if (object instanceof Brick) {
                    Brick brick = (Brick)object;
                    if (brick.getScoreJLabel() != null && brick.getScore() > 50) {
                        brick.setScore(brick.getScore()-50);
                    }
                    else {
                        if (brick.getColor() == 15) {
                            brick.dancingLight();
                        }
                        else if (brick.getColor() == 16) {
                            brick.earthquake();
                        }
                        else {
                            GameFrame.getGamePanel().remove(brick.getScoreJLabel());
                        }
                        GameManager.getObjects().remove(brick);
                        GameManager.addRemoveObject(brick,false);
                        GameFrame.getGamePanel().remove(brick.getObjectJLabel());
                    }
                }
            }
        }
        GameManager.setBricks();
    }
}
