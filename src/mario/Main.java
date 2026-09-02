package mario;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mario Game");
        GamePanel gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());

    }
}
