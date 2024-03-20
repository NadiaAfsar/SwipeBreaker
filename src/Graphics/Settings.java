package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Settings {
    JPanel gamePanel;
    JLabel aiming;
    JButton aimingOnButton;
    JButton aimingOffButton;
    JLabel music;
    JButton musicOnButton;
    JButton musicOffButton;
    JLabel save;
    JButton saveOnButton;
    JButton saveOffButton;
    JButton backButton;
    public Settings(JPanel gamePanel) {
        this.gamePanel = gamePanel;
        addAiming();
        addMusic();
        addSave();
        addBackButton();
        GameFrame.update();
    }
    private void addAiming() {
        aiming = new JLabel("Aiming:");
        aiming.setBounds(250,90,200,80);
        aiming.setFont(new Font("Serif",Font.PLAIN,36));
        aiming.setForeground(Color.PINK);
        gamePanel.add(aiming);
        aimingOnButton = new JButton("On");
        aimingOffButton = new JButton("Off");
        addOnButton(aimingOnButton,500,100);
        aimingOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aimingOnButton.setBackground(Color.GREEN);
                aimingOffButton.setBackground(Color.RED);
            }
        });
        addOffButton(aimingOffButton,620,100);
        gamePanel.add(aimingOnButton);
        aimingOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aimingOnButton.setBackground(Color.RED);
                aimingOffButton.setBackground(Color.GREEN);
            }
        });
        gamePanel.add(aimingOffButton);

    }
    private void addOnButton(JButton button, int x, int y) {
        button.setBounds(x,y,70,50);
        button.setBackground(Color.GREEN);
    }
    private void addOffButton(JButton button, int x, int y) {
        button.setBounds(x,y,70,50);
        button.setBackground(Color.RED);
    }
    private void addMusic() {
        music = new JLabel("Theme Music:");
        music.setBounds(250, 220, 300, 80);
        music.setFont(new Font("Serif", Font.PLAIN, 36));
        music.setForeground(Color.PINK);
        gamePanel.add(music);
        musicOnButton = new JButton("On");
        musicOffButton = new JButton("Off");
        addOnButton(musicOnButton, 500, 230);
        musicOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameFrame.musicOnOff) {
                    GameFrame.musicOnOff = true;
                    try {
                        GameFrame.music = new Music();
                        GameFrame.music.play();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    musicOnButton.setBackground(Color.GREEN);
                    musicOffButton.setBackground(Color.RED);
                }
            }
        });
        addOffButton(musicOffButton, 620, 230);
        musicOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameFrame.musicOnOff) {
                    GameFrame.musicOnOff = false;
                    try {
                        GameFrame.music.stop();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    musicOnButton.setBackground(Color.RED);
                    musicOffButton.setBackground(Color.GREEN);
                }
            }
        });
        if (!GameFrame.musicOnOff) {
            musicOnButton.setBackground(Color.RED);
            musicOffButton.setBackground(Color.GREEN);
        }
        gamePanel.add(musicOnButton);
        gamePanel.add(musicOffButton);
    }
    private void addSave() {
        save = new JLabel("Save Games:");
        save.setBounds(250, 350, 300, 80);
        save.setFont(new Font("Serif", Font.PLAIN, 36));
        save.setForeground(Color.PINK);
        gamePanel.add(save);
        saveOnButton = new JButton("On");
        saveOffButton = new JButton("Off");
        addOnButton(saveOnButton,500,360);
        saveOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOnButton.setBackground(Color.GREEN);
                saveOffButton.setBackground(Color.RED);
            }
        });
        addOffButton(saveOffButton,620,360);
        gamePanel.add(saveOnButton);
        saveOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOnButton.setBackground(Color.RED);
                saveOffButton.setBackground(Color.GREEN);
            }
        });
        gamePanel.add(saveOffButton);
    }
    private void addBackButton() {
        backButton = new JButton("Back");
        backButton.setBounds(640,500,100,50);
        backButton.setBackground(Color.GREEN);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                GameFrame.initialMenu();
                GameFrame.update();
            }
        });
        gamePanel.add(backButton);
    }
    private void emptyPanel() {
        gamePanel.remove(aiming);
        gamePanel.remove(aimingOnButton);
        gamePanel.remove(aimingOffButton);
        gamePanel.remove(music);
        gamePanel.remove(musicOnButton);
        gamePanel.remove(musicOffButton);
        gamePanel.remove(save);
        gamePanel.remove(saveOnButton);
        gamePanel.remove(saveOffButton);
        gamePanel.remove(backButton);
    }

}
