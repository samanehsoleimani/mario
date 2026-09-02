package mario;

import javax.swing.JFrame;

public class GameWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mario Background Test");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // اندازه پنجره
        frame.setResizable(false);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
