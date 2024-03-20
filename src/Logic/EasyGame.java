package Logic;

import java.io.IOException;

public class EasyGame extends GameManager{
    public EasyGame(int ballColor, String username) throws IOException {
        super(ballColor, username);
        nextTurn(2);
        update();
    }
}
