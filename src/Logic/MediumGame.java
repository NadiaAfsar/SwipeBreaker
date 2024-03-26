package Logic;

import java.io.IOException;

public class MediumGame extends GameManager{
    public MediumGame(int ballColor, String username) throws IOException {
        super(ballColor, username);
        difficultyLevel = 2;
        speed = 10;
        bricksToAdd = 4;
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
            bricksScore += 2;
        }
    }
}
