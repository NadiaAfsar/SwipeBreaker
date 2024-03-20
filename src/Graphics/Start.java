package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Start {
    JPanel gamePanel;
    JLabel difficultyLevel;
    JButton easyButton;
    JButton mediumButton;
    JButton hardButton;
    JLabel ballColor;
    JButton black;
    JButton white;
    JButton blue;
    JButton red;
    JButton yellow;
    JLabel getUsername;
    JTextField usernameInput;
    String username;
    JButton playButton;
    JButton backButton;
    int difficulty;
    int color;
    public Start(JPanel gamePanel) {
        this.gamePanel = gamePanel;
        addDifficultyLevel();
        addBallColor();
        getUsername();
        addPlayButton();
        addBackButton();
        GameFrame.update();
        difficulty = 0;
        color = 0;
    }
    private void addDifficultyLevel() {
        difficultyLevel = new JLabel("Difficulty Level:");
        difficultyLevel.setFont(new Font("Serif",Font.PLAIN,28));
        difficultyLevel.setForeground(Color.PINK);
        difficultyLevel.setBounds(200,10,300,80);
        gamePanel.add(difficultyLevel);
        ArrayList<JButton> buttons = new ArrayList<>();
        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");
        buttons.add(easyButton);
        buttons.add(mediumButton);
        buttons.add(hardButton);
        addButton(easyButton,200,100,100,50,0,buttons);
        addButton(mediumButton,310,100,100,50,1,buttons);
        addButton(hardButton,420,100,100,50,2,buttons);
    }
    private void addBallColor() {
        ballColor = new JLabel("Ball Color:");
        ballColor.setFont(new Font("Serif",Font.PLAIN,28));
        ballColor.setForeground(Color.PINK);
        ballColor.setBounds(200,160,300,80);
        gamePanel.add(ballColor);
        black = new JButton("Black");
        white = new JButton("White");
        blue = new JButton("Blue");
        red = new JButton("Red");
        yellow = new JButton("Yellow");
        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(black); buttons.add(white); buttons.add(blue); buttons.add(red); buttons.add(yellow);
        addButton(black,200,250,100,50,0,buttons);
        addButton(white,310,250,100,50,1,buttons);
        addButton(blue,420,250,100,50,2,buttons);
        addButton(red,530,250,100,50,3,buttons);
        addButton(yellow,640,250,100,50,4,buttons);
    }
    private void addButton(JButton button, int x, int y, int width, int height, int i, ArrayList<JButton> buttons) {
        button.setBounds(x,y,width,height);
        if (i == 0) {
            button.setBackground(Color.GREEN);
        }
        else {
            button.setBackground(Color.RED);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttons.size() == 5) {
                    color = i;
                }
                else {
                    difficulty = i;
                }
                for (int j = 0; j <buttons.size(); j++) {
                    if (j == i) {
                        buttons.get(j).setBackground(Color.GREEN);
                    }
                    else {
                        buttons.get(j).setBackground(Color.RED);
                    }
                }
                GameFrame.update();
            }
        });
        gamePanel.add(button);
    }
    private void getUsername() {
        getUsername = new JLabel("Enter Your Name:");
        getUsername.setFont(new Font("Serif",Font.PLAIN,28));
        getUsername.setForeground(Color.PINK);
        getUsername.setBounds(200,310,300,80);
        gamePanel.add(getUsername);
        usernameInput = new JTextField();
        usernameInput.setFont(new Font("Serif",Font.PLAIN,24));
        usernameInput.setBounds(200,400,250,50);
        gamePanel.add(usernameInput);
    }
    private void addPlayButton() {
        playButton = new JButton("Play");
        playButton.setBounds(400,500,100,50);
        playButton.setBackground(Color.GREEN);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameInput.getText();
                if (username.equals("")) {
                    getUsername.setForeground(Color.RED);
                    GameFrame.update();
                    new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            getUsername.setForeground(Color.PINK);
                            GameFrame.update();
                        }
                    }).start();
                }
                else {
                    emptyPanel();
                }
            }
        });
        gamePanel.add(playButton);
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
        gamePanel.remove(difficultyLevel);
        gamePanel.remove(easyButton);
        gamePanel.remove(mediumButton);
        gamePanel.remove(hardButton);
        gamePanel.remove(ballColor);
        gamePanel.remove(black);
        gamePanel.remove(white);
        gamePanel.remove(blue);
        gamePanel.remove(red);
        gamePanel.remove(yellow);
        gamePanel.remove(getUsername);
        gamePanel.remove(usernameInput);
        gamePanel.remove(playButton);
        gamePanel.remove(backButton);
    }
}
