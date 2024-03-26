package Logic;

import java.io.IOException;

public class EasyGame extends GameManager{
    public EasyGame(int ballColor, String username, boolean save, boolean aiming) throws IOException {
        super(ballColor, username, save, aiming);
        difficultyLevel = 1;
        speed = 5;
        bricksToAdd = 2;
        addTimers();
        addNewBricks();
        bricksTimer.restart();
    }

}
