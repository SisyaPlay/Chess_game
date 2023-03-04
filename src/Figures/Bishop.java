package Figures;

import java.io.File;

import javax.swing.JPanel;

import Figures.EFigure;
import Figures.ESide;
import Figures.Figure;


public class Bishop extends Figure {

	public Bishop(int x, int y, ESide side, EFigure figure) {
		super(x, y, side, figure);
		this.figure = figure.BISHOP;
	}

}
