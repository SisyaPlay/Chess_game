package Figures;

import org.w3c.dom.Node;

import javax.swing.*;

abstract public class Figure extends JFrame {
	protected int x;
	protected int y;
	protected ESide side;
	protected EFigure figure;
	
	public Figure(int x, int y, ESide side, EFigure figure) {
		this.x = x;
		this.y = y;
		this.side = side;
		this.figure = figure;
	}

	public int getStartPositionX() {
		return x;
	}

	public int getStartPositionY() {
		return y;
	}

	
}
