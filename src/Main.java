import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel{
	
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
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// System.out.println("We are calling paint component...");
		// draw a black background
		g.fillRect(0, 0, width, height);
		snake.drawSnake(g);
		fruit.drawFruit(g);
		
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
		// remover the tail (which node in getSnackBody's index is : snake.getSnakeBody().size() - 1)
		snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);
		// add new node(head) for snake
		snake.getSnakeBody().add(0, newHead);
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
}
