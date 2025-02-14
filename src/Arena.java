import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

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
    private long dt = 0;

    public static double gravityOffsetY = 0.005;
    public static double gravityOffsetX = 0;


    private double lastMouseX;
    private double lastMouseY;

    private double xSpeed = 5; // Speed of JFrame movement
    private double ySpeed = 5;

    public Arena(JFrame frame) {
        manager = new BallsManager(this);
        xPos = 0;
        yPos = 0;
        circleSize = 60;

        date = new Date();

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

        setFocusable(true);
        requestFocus();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                double factor = 0.0025;

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    gravityOffsetY -= factor;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    gravityOffsetY += factor;
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    gravityOffsetX -= factor;
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    gravityOffsetX += factor;
                }

                //System.out.println(e.getKeyCode());

                //super.keyPressed(e);
            }
        });

        // boing
        //startMovingFrame();
    }

    private Date date;

    private void startMovingFrame() {
        Timer timer = new Timer(20, e -> {
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

            int newX = (int) (frame.getX() + xSpeed);
            int newY = (int) (frame.getY() + ySpeed);

            // bounce
            if (newX + frame.getWidth() >= screenWidth || newX <= 0) xSpeed *= -0.75;
            if (newY + frame.getHeight() >= screenHeight || newY <= 0) ySpeed *= -0.75;

            frame.setLocation(newX, newY);

            ySpeed += 0.3;
        });

        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        date = new Date();
        dt = (long) (date.getTime() - lastTime);
        lastTime = date.getTime();

        int x = this.getHeight()/2 - 5 + (int)(gravityOffsetX/0.0025);
        int y = this.getWidth()/2 - 5 + (int)(gravityOffsetY/0.0025);

        if (x < 0) {
            gravityOffsetX = this.getHeight() * 0.0025;
        } else if (x > this.getWidth()) {
            gravityOffsetX = 0;
        }

        if (y < 0) {
            gravityOffsetY = this.getHeight() * 0.0025;
        } else if (y > this.getHeight()) {
            gravityOffsetY = 0;
        }



        //System.out.println(date.getTime());

        super.paintComponent(g);

        g.setColor(new Color(255,255,255));
        g.fillOval(x, y, 10, 10);

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