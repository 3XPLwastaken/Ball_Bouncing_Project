import java.awt.*;
import java.util.ArrayList;

public class BallsManager {
    private ArrayList<Ball> balls;
    private Arena arena;

    public BallsManager(Arena arena) {
        this.arena = arena;
        balls = new ArrayList<Ball>();
    }

    public void addBall() {
        int x = (int)(Math.random()*200);
        int y = (int)(Math.random()*200);

        balls.add(new Ball(x, y, 30, 30, 15,
                new Color(255, 255, 255)
       ));
    }

    public void step(Graphics g) {
        for (Ball ball : balls) {
            ball.bounce(arena);
            ball.step();
            ball.render(g);
        }
    }
}
