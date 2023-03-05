package Figures;

import java.awt.*;

public class Rook extends Figure {
	public static final EFigure figure = EFigure.ROOK;
	private double square_size;
	public Rook(int x, int y, ESide side, double square_size) {
		super(x, y, side, figure);
		this.square_size = square_size;
	}

	public void paint(Graphics g) {
		super.paint(g);

		x = (int)(x + square_size / 4);

		double R = square_size / 2;
		Graphics2D g2 = (Graphics2D)g;

		setSide(g);
		g.fillArc(x, y, (int)R, (int)R, 0, 180);

		g.setColor(Color.BLACK);
		g.drawArc(x, y, (int)R, (int)R, 0, 180);
		g.drawLine((int)x, (int)(y + R / 2), (int)(x + R), (int)(int)(y + R / 2));


		double R2 = square_size / 4;

		setSide(g);
		g.fillOval((int)(x + R / 4), (int)(y - R2 + 2), (int)R2, (int)R2);

		g.setColor(Color.BLACK);
		g.drawOval((int)(x + R / 4), (int)(y - R2 + 2), (int)R2, (int)R2);
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
