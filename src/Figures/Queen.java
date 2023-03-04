package Figures;

public class Queen extends Figure {

	public Queen(int x, int y, ESide side, EFigure figure) {
		super(x, y, side, figure);
		this.figure = figure.QUEEN;
	}

}
