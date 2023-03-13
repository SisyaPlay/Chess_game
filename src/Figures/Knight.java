package Figures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;


public class Knight extends Figure{
	public static final EFigure figure = EFigure.KNIGHT;

	Point image_corner;
	Point previousPoint;

	public Knight(int x, int y, ESide side, double square_size) {
		super(x, y, side, figure, square_size);
		setOpaque(false);
		setBackground(null);
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);

		double R = square_size;

		Graphics2D g2 = (Graphics2D)g;

		//USI
		setSide(g);
		g.fillOval((int)(this.x + square_size / 3), (int)(this.y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.fillOval((int)((this.x + square_size / 3)  + 2 * (R / 10)), (int)(this.y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.setColor(Color.BLACK);
		g.drawOval((int)(this.x + square_size / 3), (int)(this.y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.drawOval((int)((this.x + square_size / 3)  + 2 * (R / 10)), (int)(this.y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));

		//TELO
		setSide(g);
		g.fillRect((int)(this.x + square_size / 3), (int)(this.y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));
		g.setColor(Color.BLACK);
		g.drawRect((int)(this.x + square_size / 3), (int)(this.y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));

		//HLAVA
		g2.rotate(Math.toRadians(45), (int)(this.x + square_size / 2), (int)(this.y + square_size / 3));
		setSide(g);
		g.fillOval((int)(this.x + square_size / 3) , (int)(this.y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g.setColor(Color.BLACK);
		g.drawOval((int)(this.x + square_size / 3) , (int)(this.y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g2.rotate(Math.toRadians(-45), (int)(this.x + square_size / 2), (int)(this.y + square_size / 3));
	}

	public void setSide(Graphics g) {
		if(side == ESide.BLACK) {
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.WHITE);
		}
	}
}
