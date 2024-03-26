package Logic;

import Graphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class GameOver {
    private int difficultyLevel;
    private int ballColor;
    private String username;
    private JLabel gameOver;
    private int score;
    private JLabel scoreJLabel;
    private JButton playAgain;
    private JButton newGame;
    private JButton mainMenu;
    private boolean save;
    private boolean aiming;
    private String time;

    public GameOver(int difficultyLevel, int ballColor, String username, int score, boolean save, boolean aiming, String time) throws IOException {
        this.difficultyLevel = difficultyLevel;
        this.ballColor = ballColor;
        this.username = username;
        this.score = score;
        this.save = save;
        this.aiming = aiming;
        this.time = time;
        setGameOver();
        setScore();
        setPlayAgain();
        setNewGame();
        setMainMenu();
        System.out.println(1);
        saveGame();
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
                        new EasyGame(ballColor, username, save, aiming);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (difficultyLevel == 2) {
                    try {
                        new MediumGame(ballColor, username, save, aiming);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    try {
                        new HardGame(ballColor, username, save , aiming);
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
    private String date() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return date.format(now);
    }
    private void saveGame() throws IOException {
        if (save) {
            Map<String, String> map = new HashMap<>();
            map.put("date", date());
            map.put("name", username);
            map.put("time", time);
            map.put("score", score+"");
            MyProject.saved.add(map);
            if (score > MyProject.record) {
                MyProject.record = score;
            }
            MyProject.save();
        }
    }
}
