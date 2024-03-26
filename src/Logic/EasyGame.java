package Logic;

import java.io.IOException;

public class EasyGame extends GameManager{
    public EasyGame(int ballColor, String username) throws IOException {
        super(ballColor, username);
        difficultyLevel = 1;
        speed = 5;
        bricksToAdd = 2;
        addTimers();
        addNewBricks();
        bricksTimer.restart();
    }

    @Override
    public void increaseScore() {
        bricksScore++;
    }
}
