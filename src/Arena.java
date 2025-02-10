import javax.swing.*;
import java.awt.*;

public class Arena extends JPanel {
    private int circleSize;
    private int xPos;
    private int yPos;
    private BallsManager manager;

    private int oldX;
    private int oldY;

    private JFrame frame;

    private Ball mouseBall;
    private double lastTime = System.nanoTime();
    private double dt = 0;

    private double lastMouseX;
    private double lastMouseY;

    private int xSpeed = 5; // Speed of JFrame movement
    private double ySpeed = 5;

    public Arena(JFrame frame) {
        manager = new BallsManager(this);
        xPos = 0;
        yPos = 0;
        circleSize = 60;

        this.frame = frame;
        mouseBall = manager.addBall2();
        oldX = frame.getX();
        oldY = frame.getY();

        try {
            lastMouseX = super.getMousePosition().x;
            lastMouseY = super.getMousePosition().y;
        } catch (Exception _) {}

        for (int i = 0; i < 300; i++) {
            manager.addBall();
        }

        // Start moving the JFrame
        //startMovingFrame();
    }

    private void startMovingFrame() {
        Timer timer = new Timer(20, e -> {
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

            int newX = frame.getX() + xSpeed;
            int newY = (int) (frame.getY() + ySpeed);

            // Bounce off screen edges
            if (newX + frame.getWidth() >= screenWidth || newX <= 0) xSpeed *= -1;
            if (newY + frame.getHeight() >= screenHeight || newY <= 0) ySpeed *= -1;

            frame.setLocation(newX, newY);

            //ySpeed += 0.3;
        });

        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        dt = System.nanoTime() - lastTime;
        lastTime = System.nanoTime();

        super.paintComponent(g);
        manager.step(g, dt);

        if (super.getMousePosition() != null) {
            try {
                mouseBall.setX(super.getMousePosition().x - (int) (mouseBall.getSize() / 2));
                mouseBall.setY(super.getMousePosition().y - (int) (mouseBall.getSize() / 2));

                mouseBall.setxSpeed(
                        (super.getMousePosition().x - lastMouseX) / 3
                );

                mouseBall.setySpeed(
                        (super.getMousePosition().y - lastMouseY) / 3
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            lastMouseX = super.getMousePosition().x;
            lastMouseY = super.getMousePosition().y;
        } catch (Exception _) {}

        int shake = 2;
        for (int i = 0; i < manager.getBalls().size(); i++) {
            Ball ball = manager.getBalls().get(i);
            ball.addX(oldX - frame.getX() - (int) (Math.random() * shake - shake / 2));
            ball.addY(oldY - frame.getY() - (int) (Math.random() * shake - shake / 2));
        }

        oldX = frame.getX();
        oldY = frame.getY();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }
}