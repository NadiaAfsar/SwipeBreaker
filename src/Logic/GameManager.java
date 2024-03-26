package Logic;

import Graphics.GameFrame;
import Graphics.Start;
import Models.*;
import Graphics.Background;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public abstract class GameManager {
    public int difficultyLevel;
    private JPanel gamePanel;
    public int bricksScore;
    private int score;
    private double totalScore;
    private int ballColor;
    private String username;
    public static Map<String, ObjectInGame> isFull;
    private static ArrayList<ObjectInGame> objects;
    private Map<Integer,Boolean> addedObjects;
    private JButton backButton;
    private int ballsInGame;
    private int balls;
    private Ball ball;
    private static Arrow arrow;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;
    private static ArrayList<Ball> releasedBalls;
    private int releasedBallsNumber;
    private double dx;
    private double dy;
    private boolean firstBallFell;
    private int fallenBalls;
    private double ballX;
    private ArrayList<Ball> fallenBallsArray;
    private Timer timer;
    protected Timer bricksTimer;
    private int x;
    protected int speed;
    protected int bricksToAdd;
    private boolean released;
    public static int ballsPower;
    public static int hearts;
    public static int ballSpeed;
    public static boolean vertigo;
    private boolean heartUsed;
    public static int newBalls;
    private static Background gameBackGround;
    private long hour;
    private long minute;
    private long second;
    private final long startTime;
    private JLabel timeJLabel;
    private JLabel scoreJLabel1;
    private JLabel scoreJLabel2;
    private int lastY;
    public GameManager(int ballColor, String username) {
        gameBackGround = new Background("pics/background - Copy.jpg");
        GameFrame.getPanel().remove(GameFrame.getBackGround());
        GameFrame.getPanel().add(gameBackGround);
        bricksScore = 0;
        this.gamePanel = GameFrame.getGamePanel();
        this.ballColor = ballColor;
        this.username = username;
        objects = new ArrayList<>();
        addBackButton();
        ball = new Ball(430,500, ballColor);
        balls = 1;
        newBalls = 0;
        ballsInGame = balls;
        firstBallFell = false;
        ballsPower = 1;
        hearts = 0;
        ballSpeed = 1;
        vertigo = false;
        addMouseListener();
        startTime = System.currentTimeMillis()/1000;
        timeJLabel = new JLabel("00:00:00");
        timeJLabel.setBounds(750, 420,150,80);
        timeJLabel.setFont(new Font("Serif",Font.PLAIN,28));
        timeJLabel.setForeground(Color.BLACK);
        gamePanel.add(timeJLabel);
        scoreJLabel1 = new JLabel("Score:");
        scoreJLabel1.setBounds(750, 100,150,80);
        scoreJLabel1.setFont(new Font("Serif",Font.PLAIN,50));
        scoreJLabel1.setForeground(Color.BLACK);
        scoreJLabel2 = new JLabel("0");
        scoreJLabel2.setBounds(800, 180,150,80);
        scoreJLabel2.setFont(new Font("Serif",Font.PLAIN,40));
        scoreJLabel2.setForeground(Color.BLACK);
        gamePanel.add(scoreJLabel1);
        gamePanel.add(scoreJLabel2);
    }
    protected void addTimers() {
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    moveBalls();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        bricksTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTime();
                    showScore();
                    if (!released) {
                        moveBricks(speed);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void update() {
        GameFrame.getGamePanel().revalidate();
        GameFrame.getGamePanel().repaint();
    }
    private void setBricks() {
        isFull = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            addRemoveObject(objects.get(i), true);
        }
    }
    protected void nextTurn() throws IOException {
        moveBricks(40);
        bricksTimer.restart();
    }
    public static void addRemoveObject(ObjectInGame object, boolean b) {
        for (int i = object.getX(); i <= object.getX() + object.getWidth(); i++) {
            for (int j = object.getY(); j <= object.getY() + 50; j++) {
                if (b) {
                    isFull.put(i + "&" + j, object);
                }
                else {
                    isFull.put(i + "&" + j, null);
                }
            }
        }
    }
    protected void addNewBricks() throws IOException {
        increaseScore();
        addedObjects = new HashMap<>();
        for (int i = 1; i <= bricksToAdd; i++) {
            int x = getRandomX();
            if (x != 10) {
                addedObjects.put(x, true);
                Brick brick = new Brick(x * 80 - 10, -50+lastY, (int) (Math.random() * 12), bricksScore);
                objects.add(brick);
            }
        }
        addItems();
        update();
    }
    private void moveBricks(int distance) throws IOException {
        for (int i = 0; i < objects.size(); i++) {
            ObjectInGame object = objects.get(i);
            object.changePlace(object.getX(),object.getY()+distance);
            if (object.getY() >= 450) {
                gameOver();
            }
        }
        if (addNewObjects()) {
            addNewBricks();
        }
        update();
    }


    private void addBackButton() {
        backButton = new JButton("Back");
        backButton.setBounds(750,500,100,50);
        backButton.setBackground(Color.GREEN);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPanel();
                new Start(gamePanel);
                update();
            }
        });
        GameFrame.getGamePanel().add(backButton);
    }
    private int getRandomX() {
        int x = (int) (Math.random() * (9));
        if (addedObjects.get(x) != null) {
            getRandomX();
        } else if (addedObjects.get(x) == null) {
            return x;
        }
        return 10;
    }

    public static Arrow getArrow() {
        return arrow;
    }
    private void addMouseListener() {
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!released) {
                    addArrow();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!released) {
                    setBricks();
                    arrow = null;
                    int x = (int) MouseInfo.getPointerInfo().getLocation().getX() - 190;
                    int y = (int) MouseInfo.getPointerInfo().getLocation().getY() - 40;
                    calculateD(x, y);
                    releaseBalls();
                    update();
                }
            }
        };
        mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!released) {
                    addArrow();
                }
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
            arrow = new Arrow(this.ball.getOx(), this.ball.getOy(), x, y);
            update();
        }
    }
    private void releaseBalls() {
        released = true;
        releasedBalls = new ArrayList<>();
        timer.restart();
    }
    private void moveBalls() throws IOException {
        for (int i = 0; i < releasedBallsNumber; i++) {
            Ball b = releasedBalls.get(i);
            b.setPosition(b.getX()+b.getDx()*ballSpeed, b.getY()-b.getDy()*ballSpeed);
            x++;
            collided(b);
        }
        if (releasedBallsNumber != ballsInGame && x == 7*releasedBallsNumber*(releasedBallsNumber+1)/2) {
            if (fallenBalls != balls) {
                Ball b = new Ball(ball.getX(), ball.getY(), ball.getColorNumber());
                b.setDx(dx);
                b.setDy(dy);
                b.setPosition(b.getX() + dx*ballSpeed, b.getY() - dy*ballSpeed);
                releasedBalls.add(b);
                releasedBallsNumber++;
                collided(ball);
            }
            else {
                timer.stop();
                fallenBalls = 0;
            }
        }
        if (releasedBallsNumber == ballsInGame) {
            gamePanel.remove(ball.getBallJLabel());
            update();
        }
    }
    private void collided(Ball ball) throws IOException {
        int xo = ball.getOx();
        int yo = ball.getOy();
        int xd = (int) ball.getDx();
        int yd = (int) ball.getDy();
        if (!ballOnFloor(ball)) {
            if (isFull.get((xo+20)+"&"+yo) != null) {
                ObjectInGame object = isFull.get((xo+20)+"&"+yo);
                objectCollided(object, object.getX() - 45, ball.getY());
                if (object instanceof Brick) {
                ball.setDx(-ball.getDx());
                }
            }
            else if (isFull.get((xo-20)+"&"+yo) != null) {
                ObjectInGame object = isFull.get((xo-20)+"&"+yo);
                objectCollided(object, object.getX() + 110, ball.getY());
                if (object instanceof Brick) {
                    ball.setDx(-ball.getDx());
                }
            }
            if (isFull.get(xo+"&"+(yo+20)) != null) {
                ObjectInGame object = isFull.get(xo+"&"+(yo+20));
                objectCollided(object, ball.getX(), object.getY() - 75);
                if (object instanceof Brick) {
                    ball.setDy(-ball.getDy());
                }
            }
            else if (isFull.get(xo+"&"+(yo-20)) != null) {
                ObjectInGame object = isFull.get(xo+"&"+(yo-20));
                objectCollided(object, ball.getX(), object.getY()+75);
                if (object instanceof Brick) {
                    ball.setDy(-ball.getDy());
                }
            }
            if (xo+xd+20 >= 720) {
                ball.setPosition(680,ball.getY());
                ball.setDx(-ball.getDx());
            }
            if (xo+xd-10 <= 0) {
                ball.setPosition(0,ball.getY());
                ball.setDx(-ball.getDx());
            }
            if (yo-yd-20 <= 0) {
                ball.setPosition(ball.getX(), 10);
                ball.setDy(-ball.getDy());
            }
            update();
        }
    }
    private void calculateD(int x, int y) {
        int x1 = ball.getOx();
        int y1 = ball.getOy();
        if (x != x1) {
            double tan = 1.0 * (y1 - y) / (x - x1);
            if (tan >= 1) {
                dy = 5;
                dx = 5/tan;
            }
            else if (tan <= -1) {
                dy = 5;
                dx = 5/tan;
            }
            else {
                dy = Math.abs(5*tan);
                dx = 5*tan/Math.abs(tan);
            }
        }
        else {
            dx = 0;
            dy = 1;
        }
    }
    private boolean ballOnFloor(Ball ball) throws IOException {
        int xo = ball.getOx();
        int yo = ball.getOy();
        if (yo - 50 >= 520) {
            fallenBalls++;
            releasedBalls.remove(ball);
            releasedBallsNumber--;
            ballsInGame--;
            if (!firstBallFell) {
                fallenBallsArray = new ArrayList<>();
                fallenBallsArray.add(ball);
                ballX = ball.getX();
                firstBallFell = true;
            } else {
                ball.setPosition(xo - 10, 500);
                fallenBallsArray.add(ball);
            }
            if (fallenBalls == balls) {
                for (int i = 0; i < fallenBallsArray.size(); i++) {
                    gamePanel.remove(fallenBallsArray.get(i).getBallJLabel());
                }
                this.ball = new Ball(ballX, 500, ballColor);
                firstBallFell = false;
                balls += newBalls + 1;
                ballsInGame = balls;
                fallenBalls = balls;
                newBalls = 0;
                x = 0;
                nextTurn();
                released = false;
                update();
            }
            return true;
        }
        return false;
    }
    private void objectCollided (ObjectInGame object, double x, double y) throws IOException {
        if (object instanceof Brick) {
            ball.setPosition(x, y);
            if (((Brick)object).getScore()-ballsPower <= 0 || ((Brick)object).getScoreJLabel() == null) {
                if (((Brick)object).getScore() <= 1) {
                    totalScore += ((Brick) object).getInitialScore();
                    scoreJLabel2.setText((int) totalScore + "");
                }
                objects.remove(object);
                addRemoveObject(object, false);
            }
        }
        else {
            objects.remove(object);
            addRemoveObject(object, false);
        }
        object.collided();
    }
    public abstract void increaseScore();
    private void addItems() throws IOException {
        for (int i = 1; i <= 3; i++) {
            int x = getRandomX();
            if (x != 10) {
                addedObjects.put(x, true);
                Item item = getItem(x, -50+lastY);
                objects.add(item);
                addedObjects.put(x, true);
            }
        }
    }
    private Item getItem(int x, int y) throws IOException {
        int i = 0;
        if (!heartUsed) {
            i = (int) (Math.random() * 15);
        }
        else {
            i = (int) (Math.random() * 14);
        }
        if (i >= 0 && i <= 9) {
            return new NewBallItem(x * 80 - 10, y, score);

        }
        else if (i == 10) {
            try {
                return new BombItem(x * 80 - 10, y, score);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (i == 11) {
            return new PowerItem(x*80-10,y, score);
        }
        else if (i == 12) {
            return new SpeedItem(x*80-10,y, score);
        }
        else if (i == 13) {
            return new VertigoItem(x*80-10,y, score);
        }
        else if (i == 14) {
            heartUsed = true;
            return new HeartItem(x*80-10,y, score);
        }
        return null;
    }

    public static ArrayList<ObjectInGame> getObjects() {
        return objects;
    }

    public static ArrayList<Ball> getReleasedBalls() {
        return releasedBalls;
    }

    public static void changeBackGround(String path) {
        GameFrame.getPanel().remove(gameBackGround);
        gameBackGround = new Background(path);
        GameFrame.getPanel().add(gameBackGround);
    }
    private void setTime() {
        long time = System.currentTimeMillis()/1000 - startTime;
        hour = time / 3600;
        time %= 3600;
        minute = time / 60;
        second = time % 60;
    }
    private void showTime() {
        setTime();
        String time = "";
        if (hour < 10) {
            time += 0;
        }
        time += hour+":";
        if (minute < 10) {
            time += 0;
        }
        time += minute+":";
        if (second < 10) {
            time += 0;
        }
        time += second;
        timeJLabel.setText(time);
    }
    private boolean addNewObjects() {
        if (objects.size() == 0) {
            lastY = 0;
            return true;
        }
        else if (objects.get(objects.size()-1).getY() >= 0){
            lastY = objects.get(objects.size()-1).getY();
            return true;
        }
        return false;
    }
    private void showScore() {
        totalScore -= 0.01;
        scoreJLabel2.setText((int)totalScore+"");
    }
    private void emptyPanel() {
        bricksTimer.stop();
        setBricks();
        for (ObjectInGame object: objects) {
            gamePanel.remove(object.getObjectJLabel());
            if (object instanceof Brick ) {
                if (((Brick)object).getScoreJLabel() != null) {
                    gamePanel.remove(((Brick) object).getScoreJLabel());
                }
            }
        }
        gamePanel.remove(ball.getBallJLabel());
        gamePanel.removeMouseListener(mouseListener);
        gamePanel.removeMouseMotionListener(mouseMotionListener);
        gamePanel.remove(timeJLabel);
        gamePanel.remove(scoreJLabel1);
        gamePanel.remove(scoreJLabel2);
        GameFrame.getGamePanel().remove(backButton);
        GameFrame.getPanel().remove(gameBackGround);
        GameFrame.getPanel().add(GameFrame.getBackGround());
    }
    private void gameOver() {
        emptyPanel();
        new GameOver(difficultyLevel, ballColor, username, (int)totalScore);
    }

}
