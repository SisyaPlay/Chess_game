package View;

import Controllers.BoardController;
import Figures.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class KilledFigureView extends JPanel {
    private Color color;
    private double square_size;
    private Queen queen;
    private Bishop bishop;
    private Knight knight;
    private Rook rook;
    private Pawns pawns;
    private JLabel queenLabel, bishopLabel, knightLabel, rookLabel, pawnsLabel;
    private JPanel queenPanel = new JPanel();
    private JPanel bishopPanel = new JPanel();
    private JPanel knightPanel = new JPanel();
    private JPanel rookPanel = new JPanel();
    private JPanel pawnsPanel = new JPanel();
    private int countQ = 0;
    private int countB = 0;
    private int countK = 0;
    private int countR = 0;
    private int countP = 0;

    public KilledFigureView(Color color, double square_size) {
        this.color = color;
        this.square_size = square_size;
        queen = new Queen(0, 0, color, square_size);
        bishop = new Bishop(0, 0, color, square_size);
        knight = new Knight(0, 0, color, square_size);
        rook = new Rook(0, 0, color, square_size);
        pawns = new Pawns(0, 0, color, square_size);

        queenLabel = new JLabel("x" + countQ);
        bishopLabel = new JLabel("x" + countB);
        knightLabel = new JLabel("x" + countK);
        rookLabel = new JLabel("x" + countR);
        pawnsLabel = new JLabel("x" + countP);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        queenPanel.add(queen, BorderLayout.EAST);
        queenPanel.add(queenLabel, BorderLayout.CENTER);
        labelsPanel.add(queenPanel);

        bishopPanel.add(bishop, BorderLayout.EAST);
        bishopPanel.add(bishopLabel, BorderLayout.CENTER);
        labelsPanel.add(bishopPanel);

        knightPanel.add(knight, BorderLayout.EAST);
        knightPanel.add(knightLabel, BorderLayout.CENTER);
        labelsPanel.add(knightPanel);

        rookPanel.add(rook, BorderLayout.EAST);
        rookPanel.add(rookLabel, BorderLayout.CENTER);
        labelsPanel.add(rookPanel);

        pawnsPanel.add(pawns, BorderLayout.EAST);
        pawnsPanel.add(pawnsLabel, BorderLayout.CENTER);
        labelsPanel.add(pawnsPanel);

        add(labelsPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //queen.setSize(square_size);
        //bishop.setSize(square_size);
        //knight.setSize(square_size);
        //rook.setSize(square_size);
        //pawns.setSize(square_size);

    }

    public void setSquare_size(double square_size) {
        this.square_size = square_size;
    }

    public void setQueenCount(int count) {
        countQ = count;
        queenLabel.setText("x" + countQ);
    }

    public void setBishopCount(int count) {
        countB = count;
        bishopLabel.setText("x" + countB);
    }

    public void setKnightCount(int count) {
        countK = count;
        knightLabel.setText("x" + countK);
    }

    public void setRookCount(int count) {
        countR = count;
        rookLabel.setText("x" + countR);
    }

    public void setPawnsCount(int count) {
        countP = count;
        pawnsLabel.setText("x" + countP);
    }

    public void setEvNew() {
        int count = 0;
        setPawnsCount(count);
        setRookCount(count);
        setQueenCount(count);
        setKnightCount(count);
        setBishopCount(count);
    }
}
