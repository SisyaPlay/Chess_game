package Figures;

import java.awt.*;
import java.awt.geom.Path2D;

public class Rook extends Figure {
	public static final EFigure figure = EFigure.ROOK;


	public Rook(ESide side, double square_size) {
		super(side, square_size);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		setSide(g);

		g.fillRect((int)(x + square_size / 6), (int)(y + square_size - square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 4));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 6), (int)(y + square_size - square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 4));

		setSide(g);
		g.fillRect((int)(x + (square_size / 4)), (int)(y + square_size / 2) , (int)(square_size / 2), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + (square_size / 4)), (int)(y + square_size / 2) , (int)(square_size / 2), (int)(square_size / 6));

		setSide(g);
		g.fillRect((int)(x + square_size / 6), (int)(y + square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 6), (int)(y + square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 6));

		setSide(g);
		g.fillRect((int)(x + square_size / 6), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 6), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		setSide(g);
		g.fillRect((int)(x + ((square_size - square_size / 2) + (square_size / 3)) / 2), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + ((square_size - square_size / 2) + (square_size / 3)) / 2), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		setSide(g);
		g.fillRect((int)(x + square_size  - square_size /3), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size  - square_size /3), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
	}

	public void setSide(Graphics g) {
		if(side == ESide.BLACK) {
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.WHITE);
		}
	}

	public void setPosition(int x, int y) {
		this.x += x;
		this.y += y;
	}

}
