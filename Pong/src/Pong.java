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
	GLabel specialAbility1;
	GLabel specialAbility2;
	GRect paddle1;
	GRect paddle2;
	GPoint point1;
	GPoint point2;
	GPoint point3;
	GPoint point4;
	int p1Score = 0;
	int p2Score = 0;

	public void run() {
		createBackGround();
		addScores();
		specialAbilityFlashing();
		createBall();
		createPaddles();
		moveBall();
	}

	public void createBackGround() {
		setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
//		JFrame frame = new JFrame();
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
//		frame.setUndecorated(true);
//		frame.setVisible(true);
		setBackground(Color.decode("#4E4B4B"));

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
		boolean x = rand.nextBoolean();
		int dx = rand.nextInt(1) + 3;
		if (x)
			dx = -dx;
		int dy = rand.nextInt(3) + 5;
		int i = 0;
		while (true) {
			point1 = new GPoint(ball.getX(), ball.getY());
			point2 = new GPoint(ball.getX() + RADIUS, ball.getY());
			point3 = new GPoint(ball.getX() - RADIUS / 2, ball.getY());
			point4 = new GPoint(ball.getX() + RADIUS / 2, ball.getY());
			if (i < 100) {
				specialAbility1.setVisible(false);
				specialAbility2.setVisible(false);
			}
			if (i > 100) {
				specialAbility1.setVisible(true);
				specialAbility2.setVisible(true);
				if (i == 200) {
					i = 0;
				}
			}
			i++;

			// <= since we're moving in increments that are higher than 1 so we might skip
			// the point where ball.getX is equal to getWidth
//			paddle1.contains(point3) ||
//			|| paddle2.contains(point4)
			if (paddle1.contains(point1) || paddle2.contains(point2)) {
				dx = -dx;
			}
			if (getHeight() <= ball.getY() + RADIUS || ball.getY() <= 0) {
				dy = -dy;
			}

			if (p1Score == 2 || p2Score == 2) {
				remove(ball);
				if (p1Score > p2Score) {
					GLabel p1Win = new GLabel("Left side won!!!");
					p1Win.setColor(Color.GREEN);
					p1Win.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					add(p1Win, getWidth() / 2 - p1Win.getWidth() / 2, getHeight() / 2);
				} else {
					GLabel p2Win = new GLabel("Right side won!!!");
					p2Win.setColor(Color.GREEN);
					p2Win.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					add(p2Win, getWidth() / 2 - p2Win.getWidth() / 2, getHeight() / 2);
				}

				break;

			}
			if (getWidth() <= ball.getX() + RADIUS || ball.getX() <= 0) {
				newRound();
				x = rand.nextBoolean();
				dx = rand.nextInt(1) + 3;
				if (x)
					dx = -dx;

				dy = rand.nextInt(3) + 5; // random direction after every goal
			}

			ball.move(dx, dy);
			increaseScore();
			pause(5);
		}
	}

	public void specialAbilityFlashing() {
		specialAbility1 = new GLabel("Ultimate: Press D");
		specialAbility2 = new GLabel("Ultimate: Press 0");
		specialAbility1.setColor(Color.green);
		specialAbility2.setColor(Color.green);
		specialAbility1.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		specialAbility2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		add(specialAbility1, getWidth() / 4 - specialAbility1.getWidth() / 2, 60);
		add(specialAbility2, getWidth() * 0.73 - specialAbility2.getWidth() / 2, 60);
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

		if (getWidth() <= ball.getX() + RADIUS) {
			p1Score++;
			player1.setLabel(p1Score + "");
		}
		if (ball.getX() <= 0) {
			p2Score++;
			player2.setLabel(p2Score + "");
		}
	}

	public void createPaddles() {
		paddle1 = new GRect(10, 250);
		paddle1.setFilled(true);
		paddle1.setColor(Color.green);
		paddle2 = new GRect(10, 250);
		paddle2.setFilled(true);
		paddle2.setColor(Color.green);
		add(paddle1, 50, getHeight() / 2 - paddle1.getHeight() / 2);
		add(paddle2, getWidth() - 50, getHeight() / 2 - paddle2.getHeight() / 2);
	}

	public void newRound() {
		// also reset the paddles location later
		ball.setLocation(getWidth() / 2 - RADIUS / 2, getHeight() / 2 - RADIUS / 2);
		pause(1000);
	}
}
