import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
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
	
	public Main() {
		snake = new Snake();
		fruit = new Fruit();
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 0, speed);
		direction = "Right";
		addKeyListener(this	);
		allowKeyPress = true;
	}
	
	@Override
	public void paintComponent(Graphics g) {
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
}
