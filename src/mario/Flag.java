package mario;

import java.awt.*;

public class Flag {
    private int x, y;
    private Image image;
    private Image castleImage; // 🎯 اضافه کردن تصویر قلعه

    public static final int WIDTH = Tile.SIZE;
    public static final int HEIGHT = Tile.SIZE * 4;

    public Flag(int x, int y, Image image, Image castleImage) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.castleImage = castleImage; // 🎯 مقداردهی قلعه
    }

    public void draw(Graphics g, int cameraX) {
        //  کشیدن پرچم
        int width = Tile.SIZE * 4;
        int height = Tile.SIZE * 4;

        g.drawImage(image, x - cameraX, y, width, height, null);

        //  کشیدن قلعه
        int castleWidth = Tile.SIZE * 3;
        int castleHeight = Tile.SIZE * 3;

        g.drawImage(
            castleImage,
            (x + width + 10) - cameraX,   // کنار پرچم
            y + (height - castleHeight), // پایین قلعه روی زمین باشه
            castleWidth,
            castleHeight,
            null
        );
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
