import javax.swing.*;
import java.awt.*;


/**
 * Tento projekt je hra sachy, zatim bez pravidl, ale s drag and drop funkci.
 * Trida Main spusti program a vytvori okno c sachovnice.
 * @author Alinzhan Mukanov
 * @version 26.03.2023
 */
public class Main {
    private static final int WIDTH = 800; // Sirka onka
    private static final int HEIGHT = 600; // Vyska okna
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        JFrame frame = new JFrame("UPG Sachy Mukanov - A22B0388P");

        //frame.setLocationRelativeTo(null);

        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));


        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();

        frame.setVisible(true);

    }
}
