package Figures;

import java.awt.*;
import java.awt.geom.Path2D;

public class Rook extends Figure {
	public static final EFigure figure = EFigure.ROOK;
	private double square_size;
	public Rook(int x, int y, ESide side, double square_size) {
		super(x, y, side, figure);
		this.square_size = square_size;
	}

	public void paint(Graphics g) {
		super.paint(g);

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






		/*
		Path2D rook = new Path2D.Double();
		rook.moveTo(this.x, this.y + square_size / 3);
		rook.lineTo(this.x + square_size - 2 * (square_size / 6), this.y + square_size / 3);
		rook.lineTo(this.x + square_size - 2 * (square_size / 6),this.y + square_size / 5);
		rook.lineTo(this.x + square_size / 2, this.y + square_size / 5);
		rook.lineTo(this.x + square_size / 2, this.y);
		rook.lineTo((this.x + square_size - 2 * (square_size / 6) + this.x + square_size / 2) / 2, this.y);
		rook.lineTo((this.x + square_size - 2 * (square_size / 6) + this.x + square_size / 2) / 2, this.y - square_size / 2 + square_size / 3);
		rook.lineTo(this.x + square_size - 2 * (square_size / 6),this.y - square_size / 2 + square_size / 3);
		rook.lineTo(this.x + square_size - 2 * (square_size / 6), this.y - square_size / 2 + square_size / 6);
		rook.lineTo(this.x + square_size / 2, this.y - square_size / 2 + square_size / 6);
		rook.lineTo(this.x + square_size / 2, this.y - square_size / 2 + square_size / 4);
		rook.lineTo(this.x + square_size / 6, this.y - square_size / 2 + square_size / 4);
		rook.lineTo(this.x + square_size / 6, this.y - square_size / 2 + square_size / 6);
		rook.lineTo(this.x, this.y - square_size / 2 + square_size / 6);
		rook.lineTo(this.x, this.y - square_size / 2 + square_size / 3);
		rook.lineTo(this.x + (square_size / 10), this.y - square_size / 2 + square_size / 3);
		rook.lineTo(this.x + (square_size / 10), this.y);
		rook.lineTo(this.x + square_size / 6, this.y);
		rook.lineTo(this.x + square_size / 6, this.y + square_size / 5);
		rook.lineTo(this.x, this.y + square_size / 5);
		rook.closePath();

		setSide(g);
		g2.fill(rook);
		g2.setColor(Color.BLACK);
		g2.draw(rook);
		*/
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
