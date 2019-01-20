import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import acm.graphics.*;
import acm.program.*;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.lang.Object;

public class Pong extends GraphicsProgram {
	private static final int RADIUS = 35;
	private static final int BACKGROUND_WIDTH = 1200;
	private static final int BACKGROUND_HEIGHT = 600;
	private static final int PAUSE_TIME = 40;
	GOval ball;
	GLabel player1;
	GLabel player2;
	int p1Score = 0;
	int p2Score = 0;

	public void run() {
//		try { 
//		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
		createBackGround();
		createBall();
		createPaddles();
		addScores();
		moveBall();
		// getLabel() might be of use
	}

	public void createBackGround() {
//		setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		JFrame frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(true);
		frame.setVisible(true);
		setBackground(Color.decode("#8c8786"));

	}

	public void createBall() {
		ball = new GOval(RADIUS, RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.decode("#31fa00"));
		add(ball, getWidth() / 2 - RADIUS / 2, getHeight() / 2 - RADIUS / 2);
	}

	public void moveBall() {
		pause(1500); // wait for the text to be displayed
		Random rand = new Random();
		int dx = rand.nextInt(1) + 3;
		int dy = rand.nextInt(3) + 5;
		int i = 0;
		while (i < 1000) {
			// <= since we're moving in increments that are higher than 1 so we might skip
			// the point where ball.getX is equal to getWidth
			if (getWidth() <= ball.getX() + RADIUS || ball.getX() <= 0) {
				dx = -dx;
			}
			if (getHeight() <= ball.getY() + RADIUS || ball.getY() <= 0) {
				dy = -dy;
			}
			ball.move(dx, dy);
			increaseScore();
			pause(5);
			i++;
		}
	}

	public void addScores() {

		player1 = new GLabel("0");
		player2 = new GLabel("0");
		player1.setColor(Color.green);
		player2.setColor(Color.green);
		player1.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		player2.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		add(player1, getWidth() / 4 - player1.getWidth() / 2, 40);
		add(player2, getWidth() * 0.73, 40);
	}

	public void increaseScore() {


		if (getWidth() <= ball.getX() + RADIUS ) {
			p1Score++;
			player1.setLabel(p1Score+"");
		}
		if(ball.getX() <= 0) {
			p2Score++;
			player2.setLabel(p2Score+"");
		}
	}
	public void createPaddles() {
		GRect paddle1 = new GRect(20, 50);
	}
}
