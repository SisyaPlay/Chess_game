package Figures;

import java.awt.*;
import java.awt.geom.Arc2D;

public class Pawns extends Figure {
	public static final EFigure figure = EFigure.PAWNS;
	private double square_size;

	public Pawns(int x, int y, ESide side, double square_size) {
		super(x, y, side, figure);
		this.square_size = square_size;

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		this.x = (int)(x + square_size / 4);

		double R = square_size / 2;
		Graphics2D g2 = (Graphics2D)g;

		//TELO
		setSide(g);
		g.fillArc(this.x, this.y, (int)R, (int)R, 0, 180);
		g.setColor(Color.BLACK);
		g.drawArc(this.x, this.y, (int)R, (int)R, 0, 180);
		g.drawLine((int)this.x, (int)(this.y + R / 2), (int)(x + R), (int)(int)(this.y + R / 2));


		double R2 = square_size / 4;

		//HLAVA
		setSide(g);
		g.fillOval((int)(this.x + R / 4), (int)(this.y - R2 + 2), (int)R2, (int)R2);
		g.setColor(Color.BLACK);
		g.drawOval((int)(this.x + R / 4), (int)(this.y - R2 + 2), (int)R2, (int)R2);
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
