import java.awt.Color;
import acm.graphics.*;
import acm.program.*;

public class Pong extends GraphicsProgram {
	private static final int BALL_WIDTH = 50;
	private static final int BACKGROUND_WIDTH = 1000;
	private static final int BACKGROUND_HEIGHT = 600;

	public void run() {
		createBackGround();
		createBall();
	}

	public void createBackGround() {
		setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		setBackground(Color.decode("#8c8786"));

	}

	public void createBall() {
		GOval ball = new GOval(BALL_WIDTH, BALL_WIDTH);
		ball.setFilled(true);
		ball.setColor(Color.decode("#31fa00"));
		add(ball, getWidth() / 2 - BALL_WIDTH/2, getHeight() / 2 - BALL_WIDTH/2);
	}
	
}
