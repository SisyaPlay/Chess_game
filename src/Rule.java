import Figures.Figure;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Rule extends JPanel implements MouseListener, MouseMotionListener {
    private Figure[][] board;
    private int square_size;
    private Figure selectedFigure; // Vybrana figura
    private int selectedFigureX; // Pozice vybranou figury podle osy X
    private int selectedFigureY = 0; // Pozice vybranou figury podle osy Y

    private int shiftX; // Posun
    private int shiftY;

    public Rule (Figure[][] board) {
        this.board = board;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
