package mario;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {
    public int x, y;
    public static final int SIZE = 50;
    private TileType type;
    private Image img;

    // Constructor ساده: پیش‌فرض GROUND
    public Tile(int x, int y, Image img) {
        this(x, y, TileType.GROUND, img);
    }

    // Constructor کامل با مشخص‌کردن نوع
    public Tile(int x, int y, TileType type, Image img) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.img = img;
    }
    

    public void draw(Graphics g, int cameraX) {
        int width = SIZE;
        int height = SIZE;

        if (type == TileType.PIPE) {
            height = (int)(SIZE * 1.8);  // مثلاً 90 پیکسل اگه SIZE = 50
            g.drawImage(img, x - cameraX, y - (height - SIZE), width, height, null);
        } else {
            g.drawImage(img, x - cameraX, y, width, height, null);
        }
    }

    // متد جدید برای گرفتن ارتفاع واقعی
    public int getHeight() {
        return (type == TileType.PIPE) ? (int)(SIZE * 1.8) : SIZE;
    }
    
    public TileType getType() {
        return type;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Rectangle getBounds() {
        int width = SIZE;
        int height = getHeight();  // از متدی که قبلاً اضافه کردیم استفاده می‌کنه
        int renderY = (type == TileType.PIPE) ? y - (height - SIZE) : y;
        return new Rectangle(x, renderY, width, height);
    }
}