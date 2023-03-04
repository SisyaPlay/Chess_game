import java.io.File;

import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.SVGUserAgent;

public class Bishop extends Figure {

	public Bishop(int x, int y, ESide side, EFigure figure) {
		super(x, y, side, figure);
		this.figure = figure.BISHOP;
	}

}
