package mario;

import java.awt.*;

public class Coin {
	private int x, y;
	private Image image;
	private boolean collected = false;

	public Coin(int x, int y, Image image) {
		this.x = x;
		this.y = y;
		this.image = image;
	}

	public void draw(Graphics g, int cameraX) {
		if (!collected) {
			g.drawImage(image, x - cameraX, y, Tile.SIZE, Tile.SIZE, null);
		}
	}

	public boolean isCollected() {
		return collected;
	}

	public void collect() {
		collected = true;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, Tile.SIZE, Tile.SIZE);
	}
}
