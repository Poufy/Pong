import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import acm.graphics.*;
import acm.program.*;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.event.KeyAdapter;

public class Pong extends GraphicsProgram implements KeyListener {
	private static final int RADIUS = 20;
	private static final int BACKGROUND_WIDTH = 1500;
	private static final int BACKGROUND_HEIGHT = 700;
	private static final int PAUSE_TIME = 40;
	GOval ball;
	GLabel player1;
	GLabel player2;
	GLabel specialAbility1;
	GLabel specialAbility2;
	GLabel playAgain;
	GRect paddle1;
	GRect paddle2;
	GPoint point1;
	GPoint point2;
	GPoint point3;
	GPoint point4;
	GLabel p1Win;
	GLabel p2Win;
	boolean gameIsGoing = true;
	boolean x;
	Random rand;
	float movement = 0;
	int p1Score = 0;
	int p2Score = 0;
	int paddleHeight = 100;
	float i = 0;
	float dx;
	float dy;


	/*
	 * Things that need to be added: accelaration and decelration for more fluid
	 * movement Ultimate ability limiting the paddles from going off screen slow the
	 * ball a bit
	 */
	public void run() {
//		JFrame frame = new JFrame();
//		//more initialization code here
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		frame.setSize(dim.width, dim.height);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
		addKeyListeners();
		createBackGround();
		addScores();
		specialAbilityFlashing();
		createBall();
		createPaddles();
		moveBall();

	}

	public void createBackGround() {
		setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		setBackground(Color.decode("#4E4B4B"));

	}

	public void playSound() {
		File goal = new File("goal.wav");
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(goal));
			clip.start();
			Thread.sleep(clip.getMicrosecondLength() / 1000);

		} catch (Exception e) {

		}
	}

	public void specialAbilityFlickering() {
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
	}

	public void createBall() {
		ball = new GOval(RADIUS, RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.decode("#31fa00"));
		add(ball, getWidth() / 2 - RADIUS / 2, getHeight() / 2 - RADIUS / 2);
	}

	public void createGPoints() {
		point1 = new GPoint(ball.getX(), ball.getY());
		point2 = new GPoint(ball.getX() + RADIUS, ball.getY());
//		point3 = new GPoint(ball.getX() - RADIUS / 2, ball.getY());
//		point4 = new GPoint(ball.getX() + RADIUS / 2, ball.getY());
	}

	public void randomInitialBallDirection() {
		Random rand = new Random();
		boolean x = rand.nextBoolean();
		dx = rand.nextInt(2) + 3;
		if (x)
			dx = -dx;
		dy = rand.nextInt(3) + 4;
	}

	public void ballDeflect() {
		if (paddle1.contains(point1) || paddle2.contains(point2)) {
			dx = -dx;
		}
		if (getHeight() <= ball.getY() + RADIUS || ball.getY() <= 0) {
			dy = -dy;
		}
	}

	public void winningLabel(int whoWon) {
		switch (whoWon) {
		case 1:
			p1Win = new GLabel("Left side won!!!");
			playAgain = new GLabel("Press enter to play again");
			p1Win.setColor(Color.GREEN);
			p1Win.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			playAgain.setColor(Color.GREEN);
			playAgain.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			add(p1Win, getWidth() / 2 - p1Win.getWidth() / 2, getHeight() / 2);
			add(playAgain, getWidth() / 2 - playAgain.getWidth() / 2, getHeight() / 2 - p1Win.getHeight() * 3 / 2);
			break;
		case 2:
			p2Win = new GLabel("Right side won!!!");
			playAgain = new GLabel("Press enter to play again");
			p2Win.setColor(Color.GREEN);
			p2Win.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			playAgain.setColor(Color.GREEN);
			playAgain.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			add(p2Win, getWidth() / 2 - p2Win.getWidth() / 2, getHeight() / 2);
			add(playAgain, getWidth() / 2 - playAgain.getWidth() / 2, getHeight() / 2 - p2Win.getHeight() * 3 / 2);
			break;
		}

	}

	public void goalScored() {
		resetPaddlesLocation();
		newRound();
		rand = new Random();
		x = rand.nextBoolean();
		dx = rand.nextInt(1) + 3;
		if (x)
			dx = -dx;

		dy = rand.nextInt(3) + 5; // random direction after every goal
	}

	public void moveBall() {
		pause(1000); // wait for the text to be displayed
		randomInitialBallDirection();
		while (true) {
			createGPoints();
			restrictPaddles();
			specialAbilityFlickering();
			ballDeflect();

			if (p1Score == 5 || p2Score == 5) {
				remove(ball);
				if (p1Score > p2Score) {
					winningLabel(1);
					break;
				} else {
					winningLabel(2);
					break;
				}

			}
			if (getWidth() <= ball.getX() + RADIUS || ball.getX() <= 0) {
				// playSound();
				goalScored();
			}

			ball.move(dx, dy);
			increaseScore();
			double z = 9;
			if(z > 6) z-= 0.001;
			pause(z);
		}
		gameIsGoing = false;
		resetPaddlesLocation();
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
		paddle1 = new GRect(10, paddleHeight);
		paddle1.setFilled(true);
		paddle1.setColor(Color.green);
		paddle2 = new GRect(10, paddleHeight);
		paddle2.setFilled(true);
		paddle2.setColor(Color.green);
		add(paddle1, 50, getHeight() / 2 - paddleHeight / 2);
		add(paddle2, getWidth() - 50, getHeight() / 2 - paddleHeight / 2);
	}

	public void newRound() {

		// also reset the paddles location later
		ball.setLocation(getWidth() / 2 - RADIUS / 2, getHeight() / 2 - RADIUS / 2);
		pause(1000);
	}

	public void resetPaddlesLocation() {
		paddle1.setLocation(50, getHeight() / 2 - paddle1.getHeight() / 2);
		paddle2.setLocation(getWidth() - 50, getHeight() / 2 - paddle2.getHeight() / 2);
	}

	public void restrictPaddles() {
		if (paddle1.getY() <= 0) {
			paddle1.setLocation(paddle1.getX(), 0);
		}
		if (paddle1.getY() + paddleHeight >= BACKGROUND_HEIGHT) {
			paddle1.setLocation(paddle1.getX(), BACKGROUND_HEIGHT - paddleHeight);
		}
		if (paddle2.getY() <= 0) {
			paddle2.setLocation(paddle2.getX(), 0);
		}
		if (paddle2.getY() + paddleHeight >= BACKGROUND_HEIGHT) {
			paddle2.setLocation(paddle2.getX(), BACKGROUND_HEIGHT - paddleHeight);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		if(movement >= 6) movement = 6;
		if (gameIsGoing) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				movement = 8;
				paddle1.move(0, -movement);
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				movement = 8;
				paddle1.move(0, movement);
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				movement = 8;
				paddle2.move(0, -movement);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				movement = 8;
				paddle2.move(0, movement);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (gameIsGoing) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				movement *= 0.95;
				paddle1.move(0, -movement);
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				movement *= 0.95;				paddle1.move(0, movement);
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				movement *= 0.95;				paddle2.move(0, -movement);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				movement *= 0.95;				paddle2.move(0, movement);
			}
		}
	}

}
