package Figures;

import java.awt.*;


public class Bishop extends Figure {
	public static final EFigure figure = EFigure.BISHOP;
	private double square_size;

	public Bishop(int x, int y, ESide side, double square_size) {
		super(x, y, side, figure);
		this.square_size = square_size;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		setSide(g);
		g.fillOval((int)(x + square_size / 4), (int)(y + square_size / 3), (int)(square_size / 2), (int)(square_size / 2));
		g.fillRect((int)(x + square_size / 3), (int)(y + (square_size - square_size / 3.5)), (int)(square_size / 3), (int)(square_size / 6));

		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 4), (int)(y + square_size / 3), (int)(square_size / 2), (int)(square_size / 2));
		g.drawRect((int)(x + square_size / 3), (int)(y + (square_size - square_size / 3.5)), (int)(square_size / 3), (int)(square_size / 6));
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
