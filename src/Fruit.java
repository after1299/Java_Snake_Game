import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Fruit {

	private int x;
	private int y;
	private ImageIcon img;
	
	public Fruit() {
		img = new ImageIcon("fruit.png");
		this.x = (int) (Math.floor(Math.random() * Main.column) * Main.CELL_SIZE);
		this.y = (int) (Math.floor(Math.random() * Main.row) * Main.CELL_SIZE);
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public void drawFruit(Graphics g) {
		img.paintIcon(null, g, this.x, this.y);
	}
	
	public void setNewLocation(Snake s) {
		int new_x;
		int new_y;
		boolean overlapping; 
		// avoid the new location overlapping the snake's body
		do {
			new_x = (int) (Math.floor(Math.random() * Main.column) * Main.CELL_SIZE);
			new_y = (int) (Math.floor(Math.random() * Main.row) * Main.CELL_SIZE);
			overlapping = check_overlap(new_x, new_y, s);
		} while (overlapping);
		this.x = new_x;
		this.y = new_y;
	}
	
	private boolean check_overlap(int x, int y, Snake s) {
		ArrayList<Node> snake_body = s.getSnakeBody();
		for (int i = 0; i < snake_body.size(); i++) {
			// if fruit's location overlapping snake's any node
			if (x == snake_body.get(i).x && y == snake_body.get(i).y) {
				return true;
			}
		}
		return false;
	}
}
