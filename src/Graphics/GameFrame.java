package Graphics;

import Logic.MyProject;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameFrame extends JFrame {
    static Background backGround;
    static JPanel panel;
    static JPanel gamePanel;
    static JButton start;
    static JButton settings;
    static JButton history;
    static JLabel recordJLabel;
    static boolean musicOnOff;
    static Music music;

    public GameFrame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        addFrame();
        addPanel();
        addGamePanel();

        backGround = new Background("pics/background.jpg");
        backGround.setLayout(null);
        panel.add(gamePanel);
        panel.add(backGround);
        music = new Music();
        music.play();
        musicOnOff = true;
        initialMenu();
    }

    public static void initialMenu() {
        addRecord();
        addStartButton();
        addSettingsButton();
        addHistoryButton();
        update();
    }
    static void addRecord() {
        recordJLabel = new JLabel("RECORD: "+ MyProject.record);
        recordJLabel.setFont(new Font("Serif",Font.PLAIN,32));
        recordJLabel.setForeground(Color.PINK);
        recordJLabel.setBounds(340,80,300,80);
        gamePanel.add(recordJLabel);
    }
    static private void addStartButton() {
        start = new JButton("Start");
        start.setBounds(300,180,300,80);
        start.setBackground(Color.GREEN);
        start.setFont(new Font("Serif",Font.PLAIN,32));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                new Start(gamePanel);
            }
        });
        gamePanel.add(start);
    }
    static void addSettingsButton() {
        settings = new JButton("Settings");
        settings.setBounds(300,310,300,80);
        settings.setBackground(Color.GREEN);
        settings.setFont(new Font("Serif",Font.PLAIN,32));
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                new Settings(gamePanel);
            }
        });
        gamePanel.add(settings);
    }
    static void addHistoryButton() {
        history = new JButton("History");
        history.setBounds(300,440,300,80);
        history.setBackground(Color.GREEN);
        history.setFont(new Font("Serif",Font.PLAIN,32));
        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                new History();
                update();
            }
        });
        gamePanel.add(history);
    }
    static void update() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }
    private void addFrame() {
        setTitle("Swipe Brick Breaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(910, 650);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(0, 0, 900, 650);
        panel.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.setLayout(null);
        setContentPane(panel);
    }
    private void addGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setBounds(0, 0, 900, 650);
        gamePanel.setVisible(true);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        gamePanel.requestFocusInWindow();
        gamePanel.setLayout(null);
        gamePanel.setOpaque(false);
    }

    public static JPanel getGamePanel() {
        return gamePanel;
    }

    private static void emptyPanel() {
        gamePanel.remove(start);
        gamePanel.remove(settings);
        gamePanel.remove(history);
        gamePanel.remove(recordJLabel);
    }

    public static Background getBackGround() {
        return backGround;
    }

    public static JPanel getPanel() {
        return panel;
    }
}
