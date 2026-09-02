package mario;

import java.awt.*;

public class Fireball implements Runnable {
    private int x, y;
    private boolean facingRight;
    private Image image;
    private boolean active = true;
    private int speed = 8;

    // 🔹 اندازه ثابت گلوله (می‌تونی به دلخواه تغییر بدی)
    public static final int FIREBALL_SIZE = 28;

    public Fireball(int x, int y, boolean facingRight, Image image) {
        this.x = x;
        this.y = y;
        this.facingRight = facingRight;
        this.image = image;
    }

    public void draw(Graphics g, int cameraX) {
        if (active) {
            g.drawImage(image, x - cameraX, y, FIREBALL_SIZE, FIREBALL_SIZE, null);
        }
    }

    @Override
    public void run() {
        while (active) {
            if (facingRight) {
                x += speed;
            } else {
                x -= speed;
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // اگر از صفحه خارج شد غیرفعال بشه
            if (x < 0 || x > GameConstants.GAME_WIDTH) {
                active = false;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, FIREBALL_SIZE, FIREBALL_SIZE);
    }

    public boolean isActive() {
        return active;
    }

   
}
