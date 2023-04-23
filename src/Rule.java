import Figures.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Rule {
    private Figure[][] board;
    private boolean whiteTurn = true;

    public Rule (Figure[][] board) {
        this.board = board;
    }
}
