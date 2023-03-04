package Figures;

enum ESide {
	BLACK, WHITE;
}

enum EFigure {
	BISHOP, KING, KNIGHT, PAWNS, QUEEN, ROOK;
}

abstract public class Figure {
	protected int x;
	protected int y;
	protected ESide side;
	protected EFigure figure;
	/*
	final String bishop_b = "bishop-**.svg";
	final String king_b = "king-**.svg";
	final String knight_b = "knight-**.svg";
	final String pawns_b = "pawns-**.svg";
	final String queen_b = "queen-**.svg";
	final String rook_b = "rook-**.svg";
	*/
	
	public Figure(int x, int y, ESide side, EFigure figure) {
		this.x = x;
		this.y = y;
		this.side = side;
		this.figure = figure;
	}
	
}
