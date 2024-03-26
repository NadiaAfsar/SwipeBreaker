package Logic;

import java.io.IOException;

public class HardGame extends GameManager{
    public HardGame(int ballColor, String username) throws IOException {
        super(ballColor, username);
        difficultyLevel = 3;
        speed = 15;
        bricksToAdd = 6;
        addTimers();
        addNewBricks();
        bricksTimer.restart();
    }

    @Override
    public void increaseScore() {
        if (bricksScore == 0) {
            bricksScore++;
        }
        else {
            bricksScore += 5;
        }
    }
}
