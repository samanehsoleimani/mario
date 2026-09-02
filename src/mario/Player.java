package mario;

import java.awt.Rectangle;

public class Player {
    private int score;
    private int x, y;
    private int width = 32, height = 32;
    private int velocityY;
    private boolean onGround;
    private Health health = new Health(3);
 

    int fireballCount  ;

    public int getFireballCount() {
        return fireballCount;
    }

    public void setFireballCount(int count) {
        fireballCount = count;
    }

public void decreaseFireball() {
    if (fireballCount > 0) fireballCount--;
}

    public void reduceLife() {
        health.reduceLife();
        if (health.isDead()) {
            System.out.println("Game Over!");
        }
    }

    public int getLives() {
        return health.getLives();
    }

    public Player() {
        this.score = 0;
        this.x = 0;
        this.y = 0;
        this.velocityY = 0;
        this.onGround = false;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public int getVelocityY() { return velocityY; }
    public void setVelocityY(int vy) { this.velocityY = vy; }

    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
    private boolean reachedGoal = false;

    public boolean hasReachedGoal() {
        return reachedGoal;
    }

    public void setReachedGoal(boolean reachedGoal) {
        this.reachedGoal = reachedGoal;
    }

}
