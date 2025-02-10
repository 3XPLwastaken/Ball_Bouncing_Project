import java.awt.*;
import java.util.ArrayList;

public class Ball {
    private double x;
    private double y;
    private double mX;
    private double mY;

    private double xSpeed;
    private double ySpeed;

    private double originalXSpeed;
    private double originalYSpeed;

    private double size;
    private Color color;

    public Ball(int x, int y, double xSpeed, double ySpeed, int size, Color color) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.color = color;

        originalXSpeed = xSpeed;
        originalYSpeed = ySpeed;

        mX = x + (double) size /2;
        mY = y + (double) size /2;
    }

    public Color getColor() {
        return color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMx() {
        return mX;
    }

    public double getMy() {
        return mY;
    }

    public void setX(int x) {
        this.x = x;
        mX = x + (int)(size/2);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setY(int y) {
        this.y = y;
        mY = y + (int)(size/2);
    }

    public double getSize() {
        return size;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void addX(int x) {
        this.x += x;
        mX = this.x + (int)(size/2);
    }

    public void addY(int y) {
        this.y += y;
        mY = this.y + (int)(size/2);
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)size, (int)size);

        // will toggle on and off when needed for debugging
        if (true) {
            g.setColor(new Color(255,0,0));
            //debug to make sure center calculation was correct
            // g.fillRect((int) (mX), (int) (mY), 1, 1);
        }
    }

    public void step(double dt) {
        double scale = dt*60;

        ySpeed += 0.005;

        x += xSpeed;
        y += ySpeed;

        mX = x + (int)(size/2);
        mY = y + (int)(size/2);


        //ySpeed += Math.sin(System.nanoTime()/10.0)/10; // gravity
    }

    public void bounce(Arena arena, ArrayList<Ball> ballsArray) {
        //System.out.println(x-size + " >= " + arena.getWidth());
        if (x+size >= arena.getWidth() || x <= 0) {
            xSpeed *= -1;
        }

        if (y+size >= arena.getHeight() || y <= 0) {
            ySpeed *= -1;
            ySpeed /= 2;
        }
    }

    // the distance will be calulated from the middle of the ball here
    public double magnitude(Ball ball1, Ball ball2) {
        double x = Math.abs(ball1.getMx() - ball2.getMx());
        double y = Math.abs(ball1.getMy() - ball2.getMy());

        //System.out.println(Math.sqrt((x^2) + (y^2)));
        //System.out.println(y);

        //
        return Math.sqrt((Math.pow(x, 2)) + Math.pow(y, 2));
    }

    public void bounceLogic(Ball ball, Ball ball2, boolean overwrite) {
        double dx = ball2.getMx() - ball.getMx();
        double dy = ball2.getMy() - ball.getMy();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= (ball.size + ball2.size) / 2 || overwrite) {
            double nx = dx / distance;  // Normal vector
            double ny = dy / distance;

            // Compute relative velocity
            double vx = ball.xSpeed - ball2.xSpeed;
            double vy = ball.ySpeed - ball2.ySpeed;

            // Compute dot product
            double dotProduct = vx * nx + vy * ny;

            // Only continue if balls are moving toward each other
            if (dotProduct < 0) {
                return;
            }

            // Compute coefficient of restitution (elastic collisions)
            double restitution = 0.8;//0.8; // Between 0 (inelastic) and 1 (perfectly elastic)

            // Compute impulse scalar
            double impulse = (1 + restitution) * dotProduct / 2;

            // Apply impulse to change velocities
            ball.xSpeed -= impulse * nx;
            ball.ySpeed -= impulse * ny;
            ball2.xSpeed += impulse * nx;
            ball2.ySpeed += impulse * ny;

            // Ensure balls don't overlap by pushing them apart
            double overlap = (ball.size + ball2.size) / 2 - distance;
            ball.x -= overlap * nx / 2;
            ball.y -= overlap * ny / 2;
            ball2.x += overlap * nx / 2;
            ball2.y += overlap * ny / 2;

            if (!overwrite) {
                ball2.bounceLogic(ball2, ball, true);
            }
        }
    }

    public void bounceButCool(Arena arena, ArrayList<Ball> ballsArray) {
        //check positions of other balls
        for (Ball ball : ballsArray) {
            if (ball == this) {
                continue;
            }

            // calculate
            bounceLogic(this, ball, false);
        }

        //System.out.println(x-size + " >= " + arena.getWidth());
        if (x+size >= arena.getWidth()) {
            x = arena.getWidth()-size;
            xSpeed *= -0.6;
        } else if (x <= 0) {
            x = 0;
            xSpeed *= -0.6;
        }

        if (y+size >= arena.getHeight()) {
            y = arena.getHeight()-size;
            ySpeed *= -0.6;
        } else if (y <= 0) {
            y = 0;
            ySpeed *= -0.6;
            //ySpeed /= 2;
        }
    }
}
