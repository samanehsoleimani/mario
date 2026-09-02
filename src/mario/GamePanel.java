package mario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
	private Image background;
	private Image tileGround, tilePipe;
	private Image tilemario, tilemario_jump;
	private Image tilemario_leftjump;
	private Image tilemarioleft;
	private ImageIcon runningIcon;
	private ImageIcon runningleftIcon;

	private boolean isGameOver = false;

	public int cameraX = 0;
	private int marioX = 100;
	private int marioY;

	private boolean isMoving = false;

	private final int yOffset = 313;
	private final int groundRow = 4;
	private final int groundLevel = yOffset + groundRow * Tile.SIZE - Tile.SIZE;
	private int currentGroundLevel = groundLevel;

	private int velocityY = 0;
	private final int gravity = 1;
	private boolean isJumping = false;

	private ArrayList<Tile> tiles = new ArrayList<>();
	private RunBehavior runBehavior = new RunBehavior();

	private ArrayList<Coin> coins = new ArrayList<>();
	private Image coinImage;

	private Player player = new Player();
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private Image enemyImage;
	private Image heartImage = Toolkit.getDefaultToolkit().getImage("src/image/heart.png");

	private Flag flag;
	private Image flagImage;
	private boolean isLevelComplete = false;
	private Image castleImage;
	private boolean facingRight = true;
	private Image fireflowerImage;

	private ArrayList<FireFlower> fireFlowers = new ArrayList<>();

	private ArrayList<Fireball> fireballs = new ArrayList<>();
	private Image fireballImage = Toolkit.getDefaultToolkit().getImage("src/image/fireball.png");
	private boolean canShoot = false; // بعد از گرفتن گل فعال می‌شه

	private boolean isLevelCompleteMessageShown = false;
	private boolean isGameOverHandled = false;
	private boolean running = true; // کنترل اجرای gameThread

	public GamePanel() {
		tileGround = Toolkit.getDefaultToolkit().getImage("src/image/ground.jpg");
		tilePipe = Toolkit.getDefaultToolkit().getImage("src/image/Pipe4.png");
		tilemario = Toolkit.getDefaultToolkit().getImage("src/image/stand2.png");
		tilemarioleft = Toolkit.getDefaultToolkit().getImage("src/image/stand3.png");
		tilemario_jump = Toolkit.getDefaultToolkit().getImage("src/image/mario_jump.png");
		tilemario_leftjump = Toolkit.getDefaultToolkit().getImage("src/image/mario_jump2.png");
		runningIcon = new ImageIcon("src/image/running.gif");
		runningleftIcon = new ImageIcon("src/image/running2.gif");
		coinImage = Toolkit.getDefaultToolkit().getImage("src/image/coin.png");
		enemyImage = Toolkit.getDefaultToolkit().getImage("src/image/goomba.gif");
		castleImage = Toolkit.getDefaultToolkit().getImage("src/image/castel.png");
		fireflowerImage = Toolkit.getDefaultToolkit().getImage("src/image/firefower.png");
		flagImage = Toolkit.getDefaultToolkit().getImage("src/image/flag.png");
		flag = new Flag(25 * Tile.SIZE, groundLevel - Flag.HEIGHT + Tile.SIZE, flagImage, castleImage);
		
		marioY = groundLevel;

		Thread gameThread = new Thread(() -> {
		    while (running) {  // ← حالا با flag کنترل میشه
		        try {
		            Thread.sleep(30);
		        } catch (InterruptedException ex) {
		            ex.printStackTrace();
		        }
		        update();
		        CollisionHandler.checkCoinCollision(marioX, marioY, coins, player);
		        repaint();
		    }
		});
		gameThread.start();

	
		int[][] map = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 3, 3, 5, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 3, 1, 1, 1, 0, 3, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 4, 0, 4, 0, 2, 0, 0, 0, 0, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } 
				};

		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				int tileType = map[row][col];
				int x = col * Tile.SIZE;
				int y = row * Tile.SIZE + yOffset;
				switch (tileType) {
				case 1:
					tiles.add(new Tile(x, y, TileType.GROUND, tileGround));
					break;
				case 2:
					tiles.add(new Tile(x, y, TileType.PIPE, tilePipe));
					break;
				case 3:
					coins.add(new Coin(x, y, coinImage));
					break;
				case 4:
					enemies.add(new Enemy(x, y, enemyImage));
					break;
				case 5:
					fireFlowers.add(new FireFlower(x, y, fireflowerImage));
					break;
				}

			}
		}

		setFocusable(true);
		requestFocus();
		addKeyListener(this);

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.setColor(new Color(196, 232, 240));
		g.fillRect(0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);

		flag.draw(g, cameraX);

		for (FireFlower flower : fireFlowers) {
			flower.draw(g, cameraX);
		}
		for (Fireball fireball : fireballs) {
			fireball.draw(g, cameraX);
		}

		g.drawImage(background, -cameraX, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT, this);

		for (Tile t : tiles)
			t.draw(g, cameraX);
		for (Coin coin : coins)
			coin.draw(g, cameraX);

		if (isJumping) {
			g.drawImage(facingRight ? tilemario_jump : tilemario_leftjump, marioX - cameraX, marioY, Tile.SIZE,
					Tile.SIZE, this);
		} else if (isMoving) {
			int newSize = (int) (Tile.SIZE * 1.4);
			g.drawImage(facingRight ? runningIcon.getImage() : runningleftIcon.getImage(), marioX - cameraX,
					marioY - (newSize - Tile.SIZE), newSize, newSize, this);
		} else {
			g.drawImage(facingRight ? tilemario : tilemarioleft, marioX - cameraX, marioY, Tile.SIZE, Tile.SIZE, this);
		}

		for (Enemy enemy : enemies)
			enemy.draw(g, cameraX);

		for (int i = 0; i < player.getLives(); i++) {
			g.drawImage(heartImage, 20 + (i * 40), 50, 32, 32, this);
		}
		for (int i = 0; i < player.getFireballCount(); i++) {
			g.drawImage(fireballImage, 20 + (i * 40), 90, 32, 32, this); // زیر قلب‌ها نمایش میده
		}

		if (isGameOver) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 60));
			g.drawString("GAME OVER", GameConstants.GAME_WIDTH / 2 - 180, GameConstants.GAME_HEIGHT / 2);
			return;
		}

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Score: " + player.getScore(), 20, 30);
	}

	private void moveCamera(int dx) {
		cameraX += dx;
		if (cameraX < 0)
			cameraX = 0;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		int speed = 5; // سرعت ثابت

		if (code == KeyEvent.VK_SHIFT) {
			// فرض می‌کنیم می‌خوایم سرعت رو بیشتر کنیم وقتی شیفت زده می‌شه
			speed = 10;
		}

		if (code == KeyEvent.VK_RIGHT) {
			facingRight = true;
			int nextX = marioX + speed;
			if (!CollisionHandler.willCollide(nextX, marioY, tiles)) {
				marioX = nextX;
				moveCamera(speed);
				isMoving = true;
			}
		} else if (code == KeyEvent.VK_LEFT) {
			facingRight = false;
			int nextX = marioX - speed;
			if (!CollisionHandler.willCollide(nextX, marioY, tiles)) {
				marioX = nextX;
				moveCamera(-speed);
				isMoving = true;
			}
		} else if (code == KeyEvent.VK_SPACE && !isJumping) {
			jump();
		} else if (code == KeyEvent.VK_CONTROL) { // یا مثلا VK_Z
			if (canShoot && player.getFireballCount() > 0) {
				Fireball fireball = new Fireball(marioX, marioY, facingRight, fireballImage);
				fireballs.add(fireball);
				new Thread(fireball).start();
				player.decreaseFireball();
				SoundPlayer.play(Sound.FireBall); // ← این خط را اضافه کن

			}
		}
	}

	private void jump() {
		if (!isJumping) {
			velocityY = -18;
			isJumping = true;
			//SoundPlayer.play(Sound.JUMP); // اینجا صدای پرش رو اضافه کن
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_SHIFT) {
			runBehavior.setRunning(false);
		}

		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
			isMoving = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
	}

	public void update() {
		Rectangle marioRect = new Rectangle(marioX, marioY, Tile.SIZE, Tile.SIZE);

		// چک برخورد با پرچم (اتمام مرحله)
		if (marioRect.intersects(flag.getBounds())) {
			isLevelComplete = true;
			if (!isLevelCompleteMessageShown) {
				isLevelCompleteMessageShown = true;
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(this, "تبریک! مرحله تموم شد.", "مرحله کامل شد",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(0); // برنامه رو کامل می‌بنده
				});
			}
		}

		// گرانش
		velocityY += gravity;
		if (velocityY > 20)
			velocityY = 20; // محدود کردن سرعت سقوط

		int nextY = marioY + velocityY;
		Rectangle marioBoundsNextY = new Rectangle(marioX, nextY, Tile.SIZE, Tile.SIZE);
		//برای تشخیص عمودی بودنه 
		boolean collidedVertically = false;

		// برخورد عمودی با زمین یا لوله‌ها
		for (Tile tile : tiles) {
			if (tile.getType() == TileType.GROUND || tile.getType() == TileType.PIPE) {
				Rectangle tileBounds = tile.getBounds(); // حالا bounds واقعی رو می‌گیره

				// برخورد از بالا
				if (velocityY >= 0 && marioY + Tile.SIZE <= tileBounds.y + 5
						&& marioBoundsNextY.intersects(tileBounds)) {
					
					marioY = tileBounds.y - Tile.SIZE; // ماریو دقیقاً روی بالای واقعی لوله قرار می‌گیره
					velocityY = 0;
					isJumping = false;
					collidedVertically = true;
					currentGroundLevel = marioY;
					break;
				}

				// برخورد از پایین
				if (velocityY < 0 && marioY >= tileBounds.y + tileBounds.height
						&& marioBoundsNextY.intersects(tileBounds)) {

					// System.out.println("برخورد موفق از پایین");
					marioY = tileBounds.y + tileBounds.height;
					velocityY = 0;
					collidedVertically = true;
					break;
				}
			}
		}

		if (!collidedVertically) {
			marioY = nextY;
		}

		// جلوگیری از افتادن زیر زمین
		if (marioY >= groundLevel) {
			marioY = groundLevel;
			velocityY = 0;
			isJumping = false;
			currentGroundLevel = groundLevel;
		}

				// آپدیت دشمن‌ها و برخوردها
		for (Enemy enemy : enemies) {
			enemy.update();

			boolean needToReverse = false;
			Rectangle enemyBounds = enemy.getBounds();

			for (Tile tile : tiles) {
				Rectangle tileBounds = new Rectangle(tile.getX(), tile.getY(), Tile.SIZE, Tile.SIZE);
				if (tile.getType() == TileType.GROUND || tile.getType() == TileType.PIPE) {
					if (enemyBounds.intersects(tileBounds)) {
						if (enemy.getX() + Tile.SIZE >= tile.getX() && enemy.getX() <= tile.getX() + Tile.SIZE) {
							needToReverse = true;
							break;
						}
					}
				}
			}

			if (needToReverse) {
				enemy.reverseDirection();
			}

			// برخورد ماریو با دشمن
			Rectangle marioBounds = new Rectangle(marioX, marioY, Tile.SIZE, Tile.SIZE);
			// برخورد ماریو با گل
			for (FireFlower flower : fireFlowers) {
				if (!flower.isCollected() && marioBounds.intersects(flower.getBounds())) {
					flower.collect();
					player.addScore(500);
					canShoot = true;
					player.setFireballCount(3); // ✅ این خط رو اضافه کن
				}
			}

			int marioBottom = marioY + Tile.SIZE;
			int enemyTop = enemy.getY();

			// شرط دقیق برخورد از بالا، بدون محدودیت X
			boolean isStomp = (marioBottom <= enemyTop + 12) && // از بالا و کمی خطا برای طبیعی‌تر بودن
					(velocityY > 0); // در حال سقوط

			if (enemy.isAlive() && marioBounds.intersects(enemyBounds)) {
				if (isStomp) {
					enemy.kill();
					velocityY = -10; // پرش دوباره بعد از له کردن
					isJumping = true;
					player.addScore(100);
				} else {
					// برخورد از کنار یا پایین: ماریو می‌میره
					marioX = 100;
					marioY = groundLevel;
					cameraX = 0;
					player.reduceLife();
					System.out.println("You hit the enemy!");
				}
			}
		}

		// چک برخورد با سکه‌ها
		boolean collectedCoin = CollisionHandler.checkCoinCollision(marioX, marioY, coins, player);
		if (collectedCoin) {
			SoundPlayer.play(Sound.COIN);
		}

		

		// چک Game Over
		if (player.getLives() <= 0 && !isGameOverHandled) {
		    isGameOverHandled = true;  // جلوگیری از اجرای چندباره

		    // توقف gameThread
		    running = false;  // ← به جای gameLoopTimer.stop()

		    // پخش صدا در Thread جدا
		    new Thread(() -> SoundPlayer.play(Sound.GAME_OVER)).start();

		    // باز کردن پنجره Game Over روی EDT
		    SwingUtilities.invokeLater(() -> new GameOverScreen());
		}


		// چک برخورد گلوله‌ها با دشمن‌ها
		ArrayList<Fireball> fireballsToRemove = new ArrayList<>();
		for (Fireball fireball : fireballs) {
			Rectangle fireballBounds = fireball.getBounds();
			for (Enemy enemy : enemies) {
				if (enemy.isAlive() && fireballBounds.intersects(enemy.getBounds())) {
					enemy.kill();
					fireballsToRemove.add(fireball);
					player.addScore(100); // امتیاز بخاطر کشتن دشمن
					break; // این گلوله دیگه با دشمن دیگه برخورد نمی‌کنه
				}
			}
		}
		fireballs.removeAll(fireballsToRemove);

	}

}
