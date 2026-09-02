package mario;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JFrame {

    public GameOverScreen() {
        setTitle("Game Over");
        setSize(400, 300);
        setLocationRelativeTo(null); // وسط صفحه
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // متن Game Over
        JLabel label = new JLabel("GAME OVER", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(Color.BLACK);
        add(label, BorderLayout.CENTER);

        // پنل دکمه‌ها
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // دکمه شروع مجدد
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.addActionListener(e -> {
            dispose(); // بستن پنجره Game Over

            // ایجاد پنجره جدید بازی
            JFrame frame = new JFrame("Mario Game");
            GamePanel gamePanel = new GamePanel(); // کلاس بازی شما
            frame.setContentPane(gamePanel);
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        // دکمه خروج
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
