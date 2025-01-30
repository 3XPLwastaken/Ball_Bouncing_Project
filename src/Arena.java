import javax.swing.*;
import java.awt.*;

//yadayada
public class Arena extends JPanel {
    private int circleSize;
    private int xPos;
    private int yPos;
    private BallsManager manager;

    public Arena() {
        manager = new BallsManager(this);
        xPos = 0;
        yPos = 0;
        circleSize = 60;


        for (int i = 0; i < 20; i++) {
            manager.addBall();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        manager.step(g);

        /*xPos += 1;
        g.setColor(new Color(255,0,0));
        g.fillOval(xPos, yPos, circleSize, circleSize);

        g.drawRect(20, 20, 30, 30);

        //System.in.wait(1000);*/
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }
}
