package Graphics;

import Logic.MyProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class History {
    private JPanel gamePanel;
    private JScrollPane scroller;
    private JPanel panel;
    JButton backButton;
    public History() {
        gamePanel = GameFrame.getGamePanel();
        addScroller();
        addBackButton();
    }
    private void addScroller() {
        panel = new JPanel();
        panel.setLayout(null);
        addSavedGames();
        scroller = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroller.setBounds(100,50,600,500);
        JLabel label = new JLabel("Date         |Name                    |Time      |Score");
        label.setFont(new Font("Serif",Font.PLAIN,32));
        scroller.setColumnHeaderView(label);
        gamePanel.add(scroller);
    }
    private void addSavedGames() {
        panel.setBounds(0,0,600, MyProject.saved.size()*100);
        for (int i = 0; i < MyProject.saved.size(); i++) {
            Map<String,String> map = MyProject.saved.get(i);
            JLabel date = new JLabel(map.get("date"));
            date.setBounds(10, i*100, 100, 100);
            panel.add(date);
            JLabel name = new JLabel(map.get("name"));
            name.setBounds(140, i*100, 100, 100);
            panel.add(name);
            JLabel time = new JLabel(map.get("time"));
            time.setBounds(380, i*100, 100, 100);
            panel.add(time);
            JLabel score = new JLabel(map.get("score"));
            score.setBounds(500, i*100, 100, 100);
            panel.add(score);
        }
    }
    private void addBackButton() {
        backButton = new JButton("Back");
        backButton.setBounds(750,500,100,50);
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
        gamePanel.remove(scroller);
        gamePanel.remove(backButton);
    }
}
