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
		/*
		super.paint(g);
		*/
		double R = square_size;


		Graphics2D g2 = (Graphics2D)g;


		setSide(g);

		g.fillOval((int)(x + square_size / 3), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.fillOval((int)((x + square_size / 3)  + 2 * (R / 10)), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.drawOval((int)((x + square_size / 3)  + 2 * (R / 10)), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));

		setSide(g);
		g.fillOval((int)(x + square_size / 3), (int)(y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3), (int)(y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));

		g2.rotate(Math.toRadians(45), (int)(x + square_size / 2), (int)(y + square_size / 3));

		setSide(g);
		g.fillOval((int)(x + square_size / 3) , (int)(y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3) , (int)(y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g2.rotate(Math.toRadians(-45), (int)(x + square_size / 2), (int)(y + square_size / 3));
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
