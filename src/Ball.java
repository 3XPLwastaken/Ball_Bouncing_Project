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

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
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

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
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

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)size, (int)size);

        // will toggle on and off when needed for debugging
        if (true) {
            g.setColor(new Color(255,0,0));
            g.fillRect((int) (mX), (int) (mY), 1, 1);
        }
    }

    public void step() {
        x += xSpeed;
        y += ySpeed;

        mX = x + (int)(size/2);
        mY = y + (int)(size/2);
        //ySpeed += 1; // gravity
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
        int x = (int)Math.abs(ball1.mX - ball2.mX);
        int y = (int)Math.abs(ball1.mY - ball2.mY);

        System.out.println(Math.sqrt((x^2) + (y^2)));
        //System.out.println(y);

        //
        return Math.sqrt((x^2) + (y^2));
    }

    public void bounceButCool(Arena arena, ArrayList<Ball> ballsArray) {
        //check positions of other balls
        for (Ball ball : ballsArray) {
            if (ball == this) {
                continue;
            }

            // divide the size in half for accurate checking
            //System.out.println(magnitude(this, ball));
            if (magnitude(this, ball) <= size/4) {
                double cX = (ball.mX - mX);
                double cY = (ball.mY - mY);


                double divisor = Math.max(cX, cY);

                System.out.println(cX + "/" + divisor);

                x -= cX/magnitude(this, ball);
                y -= cY/magnitude(this, ball);

                xSpeed = cX/divisor * originalXSpeed;
                ySpeed = cY/divisor * originalYSpeed;

                if (cX < 0 && xSpeed < 0) {
                    xSpeed *= -1;
                } else if (cX > 0 && xSpeed > 0) {
                    xSpeed *= -1;
                }

                if (cY < 0 && ySpeed < 0) {
                    ySpeed *= -1;
                } else if (cY > 0 && ySpeed > 0) {
                    ySpeed *= -1;
                }


            }
        }

        //System.out.println(x-size + " >= " + arena.getWidth());
        if (x+size >= arena.getWidth()) {
            x = arena.getWidth()-size;
            xSpeed *= -1;
        } else if (x <= 0) {
            x = 0;
            xSpeed *= -1;
        }

        if (y+size >= arena.getHeight()) {
            y = arena.getHeight()-size;
            ySpeed *= -1;
        } else if (y <= 0) {
            y = 0;
            ySpeed *= -1;
            //ySpeed /= 2;
        }
    }
}
