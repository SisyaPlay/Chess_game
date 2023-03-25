import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        JFrame frame = new JFrame("Chess Board");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setLocationRelativeTo(null);
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}
