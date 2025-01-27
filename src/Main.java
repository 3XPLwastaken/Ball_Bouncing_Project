import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("anim");
        Arena arena = new Arena();


        frame.setSize(400, 400);
        frame.add(arena);

        arena.setBackground(new Color(5, 19, 200));

        frame.setVisible(true);
    }
}