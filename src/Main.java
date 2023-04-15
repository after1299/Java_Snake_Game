import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel implements KeyListener{
	
	public static final int CELL_SIZE = 20;
	public static int width = 400;
	public static int height = 400;
	// row size and column size
	public static int row = height / CELL_SIZE;
	public static int column = width / CELL_SIZE;
	private Snake snake;
	private Fruit fruit;
	private Timer t;
	private int speed = 100;
	private static String direction;
	private boolean allowKeyPress;
	private int score;
	private int highest_score;
	String desktop = System.getProperty("user.home") + "/Desktop/";
	String myFile = desktop + "filename.txt";
	
	public Main() {
		read_highest_score();
		// Done by reset()
//		snake = new Snake();
//		fruit = new Fruit();
//		t = new Timer();
//		t.scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				repaint();
//			}
//		}, 0, speed);
//		direction = "Right";
		reset();
		addKeyListener(this);
	}
	
	private void setTimer() {
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 0, speed);
	}
	
	private void reset() {
		score = 0;
		if (snake != null) {
			snake.getSnakeBody().clear();
		}
		allowKeyPress = true;
		direction = "Right";
		snake = new Snake();
		fruit = new Fruit();
		setTimer();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// check if the snake bits itself
		ArrayList<Node> snake_body = snake.getSnakeBody();
		Node head = snake_body.get(0);
		for (int i = 1; i < snake_body.size(); i++) {
			if (snake_body.get(i).x == head.x && snake_body.get(i).y == head.y) {
				allowKeyPress = false;
				t.cancel();
				t.purge();
				int response = JOptionPane.showOptionDialog(this, "Game Over!! Your score is " + score + ". Would you like to start over?",
															"Game Over", JOptionPane.YES_NO_OPTION, 
															JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_OPTION);
				write_a_file(score);
				switch (response) {
				case JOptionPane.CLOSED_OPTION : 
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION : 
					System.exit(0);
					break;
				case JOptionPane.YES_OPTION :
					reset();
					return;
				}
			}
		}
		
		// System.out.println("We are calling paint component...");
		// draw a black background
		g.fillRect(0, 0, width, height);
		fruit.drawFruit(g);
		snake.drawSnake(g);
		
		// remove snake tail and put it in head
		int snakeX = snake.getSnakeBody().get(0).x;
		int snakeY = snake.getSnakeBody().get(0).y;
		if (direction.equals("Left")) {
			snakeX -= CELL_SIZE;
		} else if (direction.equals("Right")) {
			snakeX += CELL_SIZE;
		} else if (direction.equals("Up")) {
			snakeY -= CELL_SIZE;
		} else if (direction.equals("Down")) {
			snakeY += CELL_SIZE;
		}
		Node newHead = new Node(snakeX, snakeY);
		// check if the snake eats the fruit
		if (snake.getSnakeBody().get(0).x == fruit.getX() &&
				snake.getSnakeBody().get(0).y == fruit.getY()) {
			// 1. set fruit to a new location
			fruit.setNewLocation(snake);
			// 2. draw fruit
			fruit.drawFruit(g);
			// 3. score++
			score++;
		} else {
			// if the snake does not eat food before the picture been repaint
			// remove the tail (which node in getSnackBody's index is : snake.getSnakeBody().size() - 1)
			// once the snake eats the fruit, program will not remove the tail at this repaint round.
			snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);
		}
		// add new node(head) for snake
		snake.getSnakeBody().add(0, newHead);
		
		// allow key press after the picture has been repaint
		allowKeyPress = true;
		requestFocusInWindow();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Snake Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new Main());
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setResizable(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (allowKeyPress) {
			if (e.getKeyCode() == 37 && !direction.equals("Right")) {
				direction = "Left";
			} else if (e.getKeyCode() == 38 && !direction.equals("Down")) {
				direction = "Up";
			} else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
				direction = "Right";
			} else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
				direction = "Down";
			}
			allowKeyPress = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void read_highest_score() {
		try {
			File myObj = new File(myFile);
			Scanner myReader = new Scanner(myObj);
			highest_score = myReader.nextInt();
			myReader.close();
		} catch (FileNotFoundException e) {
			highest_score = 0;
			try {
				File myObj = new File(myFile);
				if (myObj.createNewFile()) {
					System.out.println("File created: " + myObj.getName());
				}
				FileWriter myWriter = new FileWriter(myObj.getName());
				myWriter.write("" + 0);
				myWriter.close();
			} catch (IOException err) {
				System.out.println("An error occurred");
				err.printStackTrace();
			}
		}
	}
	
	public void write_a_file(int score) {
		try {
			FileWriter myWriter = new FileWriter(myFile);
			if (score > highest_score) {
				myWriter.write("" + score);
				highest_score = score;
			} else {
				myWriter.write("" + highest_score);
			}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
