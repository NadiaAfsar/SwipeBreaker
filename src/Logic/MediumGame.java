package Logic;

import java.io.IOException;

public class MediumGame extends GameManager{
    public MediumGame(int ballColor, String username, boolean save, boolean aiming) throws IOException {
        super(ballColor, username, save, aiming);
        difficultyLevel = 2;
        speed = 10;
        bricksToAdd = 4;
        addTimers();
        addNewBricks();
        bricksTimer.restart();
    }

}
