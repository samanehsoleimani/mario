package mario;

import java.awt.*;

public class FireFlower {
    private int x, y;
    private Image image;
    private boolean collected = false;
    public static final int SIZE = Tile.SIZE;

    public FireFlower(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void draw(Graphics g, int cameraX) {
        if (!collected) {
        	int width = (int)(Tile.SIZE * 1.2);
        	int height = (int)(Tile.SIZE * 1.2);
        	g.drawImage(image, x - cameraX, y, width, height, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
