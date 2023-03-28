package Figures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;


/**
 * Trida Rook, vykresli jezdce
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Knight extends Figure{

	public Knight(int x, int y, Color color, double square_size) {
		super(x, y, color, square_size);
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double R = square_size;
		double x = square_size * this.getX();
		double y = square_size * this.getY();


		Graphics2D g2 = (Graphics2D)g;

		//USI
		g.setColor(color);
		g.fillOval((int)(x + square_size / 3), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.fillOval((int)((x + square_size / 3)  + 2 * (R / 10)), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.drawOval((int)((x + square_size / 3)  + 2 * (R / 10)), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));

		//TELO
		g.setColor(color);
		g.fillRect((int)(x + square_size / 3), (int)(y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 3), (int)(y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));

		//HLAVA
		g2.rotate(Math.toRadians(45), (int)(x + square_size / 2), (int)(y + square_size / 3));
		g.setColor(color);
		g.fillOval((int)(x + square_size / 3) , (int)(y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3) , (int)(y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g2.rotate(Math.toRadians(-45), (int)(x + square_size / 2), (int)(y + square_size / 3));
	}


}
