package Figures;

import java.awt.*;

public class Knight extends Figure {
	public static final EFigure figure = EFigure.PAWNS;
	private double square_size;
	public Knight(int x, int y, ESide side, double square_size) {
		super(x, y, side, figure);
		this.square_size = square_size;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		double R = square_size / 2;
		Graphics2D g2 = (Graphics2D)g;
		/*
		g2.setStroke(new BasicStroke(10));
		*/
		if(side == ESide.BLACK) {
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.WHITE);
		}
		g.drawOval((int)(x + square_size / 4), (int)(y + square_size / 4), (int)R, (int)(R + square_size / 4));

		g.setColor(Color.BLACK);
		g.fillOval((int)(x + square_size / 4), (int)(y + square_size / 4), (int)R, (int)(R + square_size / 4));

		/*
		double R2 = square_size / 4;

		if(side == ESide.BLACK) {
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.WHITE);
		}
		g.fillOval((int)(x + R / 4), (int)(y - R2 + 2), (int)R2, (int)R2);

		g.setColor(Color.BLACK);
		g.drawOval((int)(x + R / 4), (int)(y - R2 + 2), (int)R2, (int)R2);
		*/

	}

}
