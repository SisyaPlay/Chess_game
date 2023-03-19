package Figures;

import java.awt.*;
import java.awt.geom.Arc2D;

public class Pawns extends Figure {
	public static final EFigure figure = EFigure.PAWNS;


	public Pawns(ESide side, double square_size) {
		super(side, square_size);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double R = square_size / 2;
		Graphics2D g2 = (Graphics2D)g;

		//TELO
		setSide(g);
		g.fillArc((int)(x + square_size / 4), (int)(y + square_size / 2), (int)R, (int)R, 0, 180);
		g.setColor(Color.BLACK);
		g.drawArc((int)(x + square_size / 4), (int)(y + square_size / 2), (int)R, (int)R, 0, 180);
		g.drawLine((int)(x + square_size / 4), (int)((y + square_size / 2) + R / 2), (int)((x + square_size / 4) + R), (int)((y + square_size / 2) + R / 2));


		double R2 = square_size / 4;

		//HLAVA
		setSide(g);
		g.fillOval((int)((x + square_size / 4) + R / 4), (int)((y + square_size / 2) - R2 + 2), (int)R2, (int)R2);
		g.setColor(Color.BLACK);
		g.drawOval((int)((x + square_size / 4) + R / 4), (int)((y + square_size / 2) - R2 + 2), (int)R2, (int)R2);
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
