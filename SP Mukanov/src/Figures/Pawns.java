package Figures;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Trida Rook, vykresli pesce
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Pawns extends Figure {

	public Pawns(int x, int y, Color color, double square_size) {
		super(x, y, color, square_size);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double R = square_size / 2;
		double R2 = square_size / 4;
		Graphics2D g2 = (Graphics2D)g;
		double x = square_size * this.getX();
		double y = square_size * this.getY();

		// Telo
		g.setColor(color);
		g.fillArc((int)(x + square_size / 4), (int)(y + square_size / 2), (int)R, (int)R, 0, 180);
		g.setColor(Color.BLACK);
		g.drawArc((int)(x + square_size / 4), (int)(y + square_size / 2), (int)R, (int)R, 0, 180);
		g.drawLine((int)(x + square_size / 4), (int)((y + square_size / 2) + R / 2), (int)((x + square_size / 4) + R), (int)((y + square_size / 2) + R / 2));

		// Hlava
		g.setColor(color);
		g.fillOval((int)((x + square_size / 4) + R / 4), (int)((y + square_size / 2) - R2 + 2), (int)R2, (int)R2);
		g.setColor(Color.BLACK);
		g.drawOval((int)((x + square_size / 4) + R / 4), (int)((y + square_size / 2) - R2 + 2), (int)R2, (int)R2);
	}

}
