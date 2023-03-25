package Figures;

import java.awt.*;


public class Bishop extends Figure {

	public Bishop(int x, int y, Color color, double square_size) {
		super(x, y, color, square_size);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double x = square_size * this.getX();
		double y = square_size * this.getY();


		g.setColor(color);
		int[] xPoints = {(int)(x + square_size / 4), (int)(x + square_size / 2), (int)(x + square_size - square_size / 4)};
		int[] yPoints = {(int)(y + square_size / 2), (int)(y + square_size / 6), (int)(y + square_size / 2)};
		g.fillPolygon(xPoints, yPoints, 3);
		g.setColor(Color.BLACK);
		g.drawLine((int)(x + square_size / 4), (int)(y + square_size / 2), (int)(x + square_size / 2), (int)(y + square_size / 6));
		g.drawLine((int)(x + square_size - square_size / 4), (int)(y + square_size / 2), (int)(x + square_size / 2), (int)(y + square_size / 6));
		g.drawLine((int)(x + square_size / 4), (int)(y + square_size / 2), (int)(x + square_size - square_size / 4), (int)(y + square_size / 2));

		g.setColor(color);
		g.fillRect((int)(x + square_size / 3), (int)(y + (square_size - square_size / 3.5)), (int)(square_size / 3), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 3), (int)(y + (square_size - square_size / 3.5)), (int)(square_size / 3 ),(int)(square_size / 6));

		g.setColor(color);
		g.fillOval((int)(x + square_size / 4), (int)(y + square_size / 3), (int)(square_size / 2), (int)(square_size / 2));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 4), (int)(y + square_size / 3), (int)(square_size / 2), (int)(square_size / 2));
	}


}
