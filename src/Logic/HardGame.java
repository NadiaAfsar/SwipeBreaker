package Logic;

import java.io.IOException;

public class HardGame extends GameManager{
    public HardGame(int ballColor, String username, boolean save, boolean aiming) throws IOException {
        super(ballColor, username, save, aiming);
        difficultyLevel = 3;
        speed = 15;
        bricksToAdd = 6;
        addTimers();
        addNewBricks();
        bricksTimer.restart();
    }

}
