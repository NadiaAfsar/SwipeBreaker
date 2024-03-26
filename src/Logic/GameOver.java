package Logic;

import Graphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOver {
    int difficultyLevel;
    int ballColor;
    String username;
    JLabel gameOver;
    int score;
    JLabel scoreJLabel;
    JButton playAgain;
    JButton newGame;
    JButton mainMenu;

    public GameOver(int difficultyLevel, int ballColor, String username, int score) {
        this.difficultyLevel = difficultyLevel;
        this.ballColor = ballColor;
        this.username = username;
        this.score = score;
        setGameOver();
        setScore();
        setPlayAgain();
        setNewGame();
        setMainMenu();
    }
    private void setGameOver() {
        gameOver = new JLabel("Game Over");
        gameOver.setBounds(350, 60,300,80);
        gameOver.setFont(new Font("Serif",Font.PLAIN,40));
        gameOver.setForeground(Color.PINK);
        GameFrame.getGamePanel().add(gameOver);

    }
    private void setScore() {
        scoreJLabel = new JLabel("Score: "+score);
        scoreJLabel.setBounds(350, 140, 300, 50);
        scoreJLabel.setFont(new Font("Serif",Font.PLAIN,32));
        scoreJLabel.setForeground(Color.PINK);
        GameFrame.getGamePanel().add(scoreJLabel);
    }
    private void setPlayAgain() {
        playAgain = new JButton("Play Again");
        playAgain.setBounds(300,250,300,50);
        playAgain.setBackground(Color.GREEN);
        playAgain.setFont(new Font("Serif",Font.PLAIN,32));
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                if (difficultyLevel == 1) {
                    try {
                        new EasyGame(ballColor, username);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (difficultyLevel == 2) {
                    try {
                        new MediumGame(ballColor, username);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    try {
                        new HardGame(ballColor, username);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        GameFrame.getGamePanel().add(playAgain);
    }
    private void setNewGame() {
        newGame = new JButton("New Game");
        newGame.setBounds(300,350,300,50);
        newGame.setBackground(Color.GREEN);
        newGame.setFont(new Font("Serif",Font.PLAIN,32));
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                new Start(GameFrame.getGamePanel());
            }
        });
        GameFrame.getGamePanel().add(newGame);
    }
    private void setMainMenu() {
        mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(300,450,300,50);
        mainMenu.setBackground(Color.GREEN);
        mainMenu.setFont(new Font("Serif",Font.PLAIN,32));
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                GameFrame.initialMenu();
            }
        });
        GameFrame.getGamePanel().add(mainMenu);
    }

    private void emptyPanel() {
        GameFrame.getGamePanel().remove(gameOver);
        GameFrame.getGamePanel().remove(scoreJLabel);
        GameFrame.getGamePanel().remove(playAgain);
        GameFrame.getGamePanel().remove(newGame);
        GameFrame.getGamePanel().remove(mainMenu);
    }
}
