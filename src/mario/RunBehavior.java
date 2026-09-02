package mario;

public class RunBehavior {
    private int walkSpeed = 5;
    private int runSpeed = 10;
    private boolean isRunning = false;

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public int getSpeed() {
    	if (isRunning) {
    	    return runSpeed;
    	} else {
    	    return walkSpeed;
    	}
    }

    public boolean isRunning() {
        return isRunning;
    }
}
