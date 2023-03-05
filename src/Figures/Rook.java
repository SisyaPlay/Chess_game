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

		this.x = (int)(x + square_size / 6);
		this.y = (int)(y + square_size / 2);

		Graphics2D g2 = (Graphics2D)g;

		setSide(g);
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

		g2.draw(rook);
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
