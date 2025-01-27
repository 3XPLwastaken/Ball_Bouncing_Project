import java.awt.*;

public class Ball {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int size;
    private Color color;

    public Ball(int x, int y, int xSpeed, int ySpeed, int size, Color color) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
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
    }

    public int getSize() {
        return size;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public void step() {
        x += xSpeed;
        y += ySpeed;

        ySpeed += 1; // gravity
    }

    public void bounce(Arena arena) {
        //System.out.println(x-size + " >= " + arena.getWidth());
        if (x+size >= arena.getWidth() || x <= 0) {
            xSpeed *= -1;
        }

        if (y+size >= arena.getHeight() || y <= 0) {
            ySpeed *= -1;
            ySpeed /= 2;
        }
    }


    public void bounceButCool(Arena arena) {
        //System.out.println(x-size + " >= " + arena.getWidth());
        if (x+size >= arena.getWidth()) {
            x = arena.getWidth();
            xSpeed *= -1;
        } else if (x <= 0) {
            x = 0;
            xSpeed *= -1;
        }

        if (y+size >= arena.getHeight()) {
            y = arena.getHeight();
            ySpeed *= -1;
            ySpeed /= 2;
        } else if (y <= 0) {
            y = 0;
            ySpeed *= -1;
            ySpeed /= 2;
        }
    }
}
