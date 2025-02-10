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
        int x = (int)(Math.random()*2000);
        int y = (int)(Math.random()*2000);

        balls.add(new Ball(x, y, Math.random(), Math.random(), 10,
                randomColor()
        ));

        /*balls.add(new Ball(x, y, Math.random(), Math.random(), (int)(Math.random()*50),
                randomColor()
        ));*/
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Ball addBall2() {
        int x = (int)(Math.random()*200);
        int y = (int)(Math.random()*200);

        balls.add(new Ball(x, y, 3, 3, 10,
                randomColor()
        ));

        /*balls.add(new Ball(x, y, Math.random(), Math.random(), (int)(Math.random()*50),
                randomColor()
        ));*/

        return balls.getLast();
    }

    public void step(Graphics g, double dt) {
        for (Ball ball : balls) {
            ball.bounceButCool(arena, balls);
            ball.step(dt);
            ball.render(g);
        }
    }
}
