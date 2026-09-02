package mario;

public class Health {
    private int lives;

    public Health(int initialLives) {
        this.lives = initialLives;
    }

    public int getLives() {
        return lives;
    }

    public void reduceLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public void reset(int livesCount) {
        this.lives = livesCount;
    }

    public boolean isDead() {
        return lives <= 0;
    }
}
