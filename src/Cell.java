import java.awt.*;

import javax.swing.JPanel;

public class Cell extends JPanel{
	double x1, y1, square_size;
	private int shiftX;
	Color color;
	private final int ROW_COUNT = 8;
	public double[] chessBoardX = new double[ROW_COUNT];
	public double[] chessBoardY = new double[ROW_COUNT];
	
	public Cell(double x1, double y1, double square_size, int shiftX) {
		this.x1 = x1;
		this.y1 = y1;
		this.square_size = square_size;
		this.shiftX = shiftX;
		this.setOpaque(false);
	}

	public void paintComponent(Graphics g){
		double row, col;
		int index = 0;
		int index2 = 0;
		g.setColor(Color.WHITE);
		for (row = 0; row < ROW_COUNT; row++) {
			y1 = row * square_size;
			for (col = 0; col < ROW_COUNT; col++) {
				x1 = col * square_size + shiftX;
				if ((row + col) % 2 != 0) {
					g.fillRect((int) x1, (int) y1, (int) square_size, (int) square_size);
					drawHetch(g);
				}
				if(index < ROW_COUNT) {
					chessBoardX[(int)col] = x1;
					index++;
				}
			}
			if(index2 < ROW_COUNT) {
				chessBoardY[(int)row] = y1;
				index2++;
			}
		}


		for (row = 0; row < ROW_COUNT; row++) {
			for (col = 0; col < ROW_COUNT; col++) {
				x1 = col * square_size + shiftX;
				y1 = row * square_size;
				if ((row + col) % 2 == 0) {
					g.fillRect((int)x1, (int)y1, (int)square_size, (int)square_size);
				}
			}
		}
	}

	private void drawHetch(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g.setColor(Color.lightGray);
		for (int i = 0; i < square_size; i += 8) {
			g.drawLine((int)x1, (int)y1 + i, (int)x1 + i, (int)y1);
			g.drawLine((int)x1 + (int)square_size, (int)y1 + i, (int)x1 + i, (int)y1 + (int)square_size);
		}
		g.setColor(Color.WHITE);
	}

	public void setSize(double square_size) {
		this.square_size = square_size;
	}
}