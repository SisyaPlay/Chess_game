import java.awt.*;

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
		if(color == Color.lightGray) {
			g.setColor(Color.WHITE);
			g.fillRect((int)x1 , (int)y1, (int)square_size, (int)square_size);
			drawHetch(g);
		}
		else{
			g.setColor(color);
			g.fillRect((int)x1 , (int)y1, (int)square_size, (int)square_size);
		}
	
	}

	private void drawHetch(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		// Заштриховываем квадрат
		g.setColor(Color.lightGray);
		for (int i = 0; i < square_size; i += 10) {
			g.drawLine((int)x1, (int)y1 + i, (int)x1 + i, (int)y1);
			g.drawLine((int)x1 + (int)square_size, (int)y1 + i, (int)x1 + i, (int)y1 + (int)square_size);
		}
	}
}
