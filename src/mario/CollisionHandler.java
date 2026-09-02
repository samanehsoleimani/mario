package mario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionHandler {

	// بررسی برخورد ماریو با زمین یا لوله
	public static boolean willCollide(int nextX, int marioY, List<Tile> tiles) {
		Rectangle marioBounds = new Rectangle(nextX, marioY, Tile.SIZE, Tile.SIZE);

		for (Tile tile : tiles) {
			if (tile.getType() == TileType.GROUND || tile.getType() == TileType.PIPE) {
				Rectangle tileBounds = new Rectangle(tile.getX(), tile.getY(), Tile.SIZE, Tile.SIZE);
				if (marioBounds.intersects(tileBounds)) {
					return true;
				}
			}
		}
		return false;
	}

	// بررسی برخورد با سکه‌ها و افزایش امتیاز بازیکن
	public static boolean checkCoinCollision(int marioX, int marioY, ArrayList<Coin> coins, Player player) {
		Rectangle marioRect = new Rectangle(marioX, marioY, Tile.SIZE, Tile.SIZE);

		for (int i = 0; i < coins.size(); i++) {
			Coin coin = coins.get(i);
			if (marioRect.intersects(coin.getBounds())) {
				coins.remove(i);
				player.addScore(10);
				return true; // سکه گرفته شد
			}
		}
		return false; // سکه گرفته نشد
	}

	// بررسی برخورد خاص با لوله (در صورت نیاز برای انیمیشن یا موانع خاص)
	public static boolean collidesWithWall(int marioX, int marioY, List<Tile> tiles) {
		Rectangle marioBounds = new Rectangle(marioX, marioY, Tile.SIZE, Tile.SIZE);

		for (Tile tile : tiles) {
			if (tile.getType() == TileType.PIPE) {
				Rectangle tileBounds = new Rectangle(tile.getX(), tile.getY(), Tile.SIZE, Tile.SIZE);
				if (marioBounds.intersects(tileBounds)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void handleCollisions(Player player, List<Tile> tiles) {
		Rectangle playerBounds = player.getBounds();

		for (Tile tile : tiles) {
			if (tile.getType() == TileType.GROUND || tile.getType() == TileType.PIPE) {
				Rectangle tileBounds = new Rectangle(tile.getX(), tile.getY(), Tile.SIZE, Tile.SIZE);

				if (playerBounds.intersects(tileBounds)) {
					// موقعیت‌های نسبی
					int playerBottom = player.getY() + player.getHeight();
					int playerTop = player.getY();
					int playerRight = player.getX() + player.getWidth();
					int playerLeft = player.getX();

					int tileBottom = tile.getY() + Tile.SIZE;
					int tileTop = tile.getY();
					int tileRight = tile.getX() + Tile.SIZE;
					int tileLeft = tile.getX();

					int overlapTop = playerBottom - tileTop;
					int overlapBottom = tileBottom - playerTop;
					int overlapLeft = playerRight - tileLeft;
					int overlapRight = tileRight - playerLeft;

					// کمترین مقدار همپوشانی پیدا کن
					int minOverlap = Math.min(Math.min(overlapTop, overlapBottom), Math.min(overlapLeft, overlapRight));

					if (minOverlap == overlapTop) {
						// برخورد از بالا - ایستادن روی tile
						player.setY(tileTop - player.getHeight());
						player.setVelocityY(0);
						player.setOnGround(true);
					} else if (minOverlap == overlapBottom) {
						// برخورد از پایین - سرش خورد به tile
						player.setY(tileBottom);
						player.setVelocityY(0);
					} else if (minOverlap == overlapLeft) {
						// برخورد از چپ
						player.setX(tileLeft - player.getWidth());
					} else if (minOverlap == overlapRight) {
						// برخورد از راست
						player.setX(tileRight);
					}

					// برو بیرون از حلقه چون یک tile کفایت می‌کنه
					break;
				}
			}
		}
	}

}