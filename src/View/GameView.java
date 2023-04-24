package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame{


    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 800; // Sirka onka
    private static final int HEIGHT = 600; // Vyska okna

    private final Panel rightPanel = new Panel();
    private final Panel leftPanel = new Panel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuGame = new JMenu("Game");
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem item = new JMenuItem("Restart");
    private JMenuItem item2 = new JMenuItem("Export graph");
    private JMenuItem item3 = new JMenuItem("Export to PNG");

    private JLabel timer1 = new JLabel("Timer 1");
    private JLabel timer2 = new JLabel("Timer 2");
    private JLabel countOfKillerFig1 = new JLabel("countOfKillerFig1");
    private JLabel countOfKillerFig2 = new JLabel("countOfKillerFig1");


    public GameView() {

    }

    public void createWindow() {
        ChessBoardView board = new ChessBoardView();
        this.setTitle("UPG Sachy Mukanov - A22B0388P");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.restart();
            }
        });
        // Menu
        menuBar.add(menuGame);
        menuBar.add(menuHelp);
        menuGame.add(item);
        menuGame.add(item2);
        menuGame.add(item3);
        this.setJMenuBar(menuBar);

        // Set layout
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        // Right panel
        this.getContentPane().add(rightPanel, BorderLayout.EAST);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.add(timer1);
        rightPanel.add(timer2);

        // Left panel
        this.getContentPane().add(leftPanel, BorderLayout.WEST);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(countOfKillerFig1);
        leftPanel.add(countOfKillerFig2);

        this.getContentPane().add(board, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();

        this.setVisible(true);
    }
}
