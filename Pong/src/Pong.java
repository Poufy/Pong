import java.awt.Color;
import acm.graphics.*;
import acm.program.*;
import java.util.Random;

public class Pong extends GraphicsProgram {
	private static final int RADIUS = 50;
	private static final int BACKGROUND_WIDTH = 1000;
	private static final int BACKGROUND_HEIGHT = 600;
	private static final int PAUSE_TIME = 40;

	GOval ball;

	public void run() {
		createBackGround();
		createBall();
		moveBall();
	}

	public void createBackGround() {
		setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		setBackground(Color.decode("#8c8786"));

	}

	public void createBall() {
		ball = new GOval(RADIUS, RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.decode("#31fa00"));
		add(ball, getWidth() / 2 - RADIUS/2, getHeight() / 2 - RADIUS/2);
	}
	public void moveBall() {
		Random rand = new Random();
		int dx = rand.nextInt(5) + 10;
		int dy = rand.nextInt(10) + 15;
		int i = 0;
		while(i < 1000) {
			// <= since we're moving in increments that are higher than 1 so we might skip the point where ball.getX is equal to getWidth
			if(getWidth() <= ball.getX() + RADIUS || ball.getX()<= 0) {
				dx = -dx;
			}
			if(getHeight() <= ball.getY() + RADIUS || ball.getY() <= 0) {
				dy = -dy;
			}
			ball.move(dx, dy);
			pause(20);
			i++;
		}
	}
	
}
