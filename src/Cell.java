import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Cell extends JPanel{
	double x1, y1, x2, y2, square_size;
	Color color;
	
	public Cell(double x1, double y1, double x2, double y2, double square_size, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.square_size = square_size;
		this.color = color;
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.setColor(color);
		g.fillRect((int)x1 , (int)y1, (int)square_size, (int)square_size);

		if(color == Color.BLACK) {
			drawHetch(g);
		}
	
	}

	private void drawHetch(Graphics g) {
		
	}
}
