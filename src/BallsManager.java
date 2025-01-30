import java.awt.*;
import java.util.ArrayList;



public class BallsManager {
    private ArrayList<Ball> balls;
    private Arena arena;

    public BallsManager(Arena arena) {
        this.arena = arena;
        balls = new ArrayList<Ball>();
    }

    private static Color randomColor() {
        return new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
    }

    public void addBall() {
        int x = (int)(Math.random()*200);
        int y = (int)(Math.random()*200);

        balls.add(new Ball(x, y, Math.random(), Math.random(), (int)(Math.random()*50),
                randomColor()
        ));
    }

    public void step(Graphics g) {
        for (Ball ball : balls) {
            ball.bounceButCool(arena, balls);
            ball.step();
            ball.render(g);
        }
    }
}
