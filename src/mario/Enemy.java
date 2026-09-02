package mario;

import java.awt.*;

public class Enemy {
    private int x, y;
    private int width = Tile.SIZE;
    private int height = Tile.SIZE;
    private Image image;
    private int speed = 2;
    private boolean movingLeft = true;
    private boolean isAlive = true;

    public Enemy(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void update() {
        if (!isAlive) return;

        if (movingLeft) {
            x -= speed;
        } else {
            x += speed;
        }
    }

    public void reverseDirection() {
        movingLeft = !movingLeft;
    }

    public void draw(Graphics g, int cameraX) {
        if (isAlive) {
            g.drawImage(image, x - cameraX, y, width, height, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isAlive() { return isAlive; }
    public void kill() { isAlive = false; }
}
