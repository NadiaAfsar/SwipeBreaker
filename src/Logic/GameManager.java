package Logic;

import Graphics.GameFrame;
import Graphics.Start;
import Models.Arrow;
import Models.Ball;
import Models.Brick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class GameManager {
    JPanel gamePanel;
    int score;
    int record;
    int speed;
    int ballColor;
    String username;
    boolean[][] isBrick;
    ArrayList<Brick> bricks;
    Map<Integer,Boolean> addedBricks;
    JButton backButton;
    int balls;
    Ball ball;
    static Arrow arrow;
    MouseListener mouseListener;
    MouseMotionListener mouseMotionListener;
    ArrayList<Ball> releasedBalls;
    int releasedBallsNumber;
    double dx;
    double dy;
    public GameManager(int ballColor, String username) {
        score = 0;
        this.gamePanel = GameFrame.getGamePanel();
        this.ballColor = ballColor;
        this.username = username;
        bricks = new ArrayList<>();
        addBackButton();
        ball = new Ball(430,500, ballColor);
        balls = 1;
        addMouseListener();

    }
    public void update() {
        GameFrame.getGamePanel().revalidate();
        GameFrame.getGamePanel().repaint();
    }
    public void setBricks() {
        for (Brick brick: bricks) {
            for (int i = brick.getX(); i < brick.getX() + 100; i++) {
                for (int j = brick.getY(); j < brick.getY() + 50; j++) {
                    isBrick[i][j] = true;
                }
            }
        }
    }
    public void nextTurn(int bricksToAdd) throws IOException {
        score++;
        for (Brick brick: bricks) {
            brick.changePlace(brick.getX(),brick.getY()+50);
        }
        addedBricks = new HashMap<>();
        isBrick = new boolean[950][700];
        for (int i = 1; i <= bricksToAdd; i++) {
            int x = getRandomX(i);
            addedBricks.put(x,true);
            Brick brick = new Brick(x*100, 0, (int)(Math.random()*5), score);
            bricks.add(brick);
        }
        setBricks();
    }

    private void addBackButton() {
        backButton = new JButton("Back");
        backButton.setBounds(640,500,100,50);
        backButton.setBackground(Color.GREEN);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Brick brick: bricks) {
                    gamePanel.remove(brick.getBrickJLabel());
                    gamePanel.remove(brick.getScoreJLabel());
                }
                gamePanel.remove(ball.getBallJLabel());
                gamePanel.removeMouseListener(mouseListener);
                gamePanel.removeMouseMotionListener(mouseMotionListener);
                GameFrame.getGamePanel().remove(backButton);
                new Start(gamePanel);
                update();
            }
        });
        GameFrame.getGamePanel().add(backButton);
    }
    private int getRandomX(int i) {
        int x = (int)(Math.random()*(9-i+1));
        if (addedBricks.get(x) != null) {
            x++;
        }
        return x;
    }

    public static Arrow getArrow() {
        return arrow;
    }
    private void addMouseListener() {
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addArrow();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                arrow = null;
                int x = (int)MouseInfo.getPointerInfo().getLocation().getX() - 190;
                int y = (int)MouseInfo.getPointerInfo().getLocation().getY() - 40;
                calculateD(x,y);
                releaseBalls();
                update();
            }
        };
        mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                addArrow();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
        gamePanel.addMouseListener(mouseListener);
        gamePanel.addMouseMotionListener(mouseMotionListener);
    }
    private void addArrow() {
        int x = (int)MouseInfo.getPointerInfo().getLocation().getX() - 190;
        int y = (int)MouseInfo.getPointerInfo().getLocation().getY() - 40;
        if (y < 545) {
            arrow = new Arrow(ball.getOx(), ball.getOy(), x, y);
            update();
        }
    }
    private void releaseBalls() {
        releasedBalls = new ArrayList<>();
        new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveBalls();
            }
        }).start();
    }
    private void moveBalls() {
        for (int i = 0; i < releasedBallsNumber; i++) {
            Ball b = releasedBalls.get(i);
            b.setPosition(b.getX()+b.getDx(), b.getY()-b.getDy());
            collided(b);
        }
        if (releasedBallsNumber != balls) {
            Ball b = new Ball(ball.getX(), ball.getY(), ball.getColorNumber());
            b.setDx(dx);
            b.setDy(dy);
            b.setPosition(b.getX()+dx, b.getY()-dy);
            releasedBalls.add(b);
            releasedBallsNumber++;
            collided(ball);
        }
        if (releasedBallsNumber == balls) {
            gamePanel.remove(ball.getBallJLabel());
            update();
        }
    }
    private void collided(Ball ball) {
        int xo = ball.getOx();
        int yo = ball.getOy();
        int xd = (int)ball.getDx();
        int yd = (int)ball.getDy();
        if (yo-yd-50 < 0) {
            ball.setPosition(xo-20,0);
            ball.setDy(-ball.getDy());
        }
        else if (xo+xd >= 900) {
            ball.setPosition(880,yo-50);
            ball.setDx(-ball.getDx());
        }
        else if (xo+xd-50 <= 0) {
            ball.setPosition(0,yo-50);
            ball.setDx(-ball.getDx());
        }
        else if (isBrick[xo][yo-yd-50] || isBrick[xo][yo-yd+50]) {
            ball.setDy(-ball.getDy());
        }
        else if (isBrick[xo+xd+20][yo] || isBrick[xo+xd-20][yo]) {
            ball.setDx(-ball.getDx());
        }
    }
    private void calculateD(int x, int y) {
        int x1 = ball.getOx();
        int y1 = ball.getOy();
        if (x != x1) {
            double tan = 1.0 * (y1 - y) / (x - x1);
            if (tan >= 1) {
                dy = 10;
                dx = 10/tan;
            }
            else if (tan <= -1) {
                dy = 10;
                dx = 10/tan;
            }
            else {
                dy = Math.abs(10*tan);
                dx = 10*tan/Math.abs(tan);
            }
        }
        else {
            dx = 0;
            dy = 10;
        }
    }
}
