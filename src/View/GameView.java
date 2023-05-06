package View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

    public JLabel timer1;
    public JLabel timer2;
    private JLabel countOfKillerFig1 = new JLabel("countOfKillerFig1");
    private JLabel countOfKillerFig2 = new JLabel("countOfKillerFig1");


    public GameView() {

    }

    public void createWindow(JFrame frame) {
        ChessBoardView board = new ChessBoardView();
        this.setTitle("UPG Sachy Mukanov - A22B0388P");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);

        timer1 = board.timer1;
        timer2 = board.timer2;

        timer1.setFont(new Font("Verdana", Font.PLAIN, calculateSize()));
        timer2.setFont(new Font("Verdana", Font.PLAIN, calculateSize()));

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.restart();
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save as PNG");
                FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.addChoosableFileFilter(pngFilter);
                fileChooser.setFileFilter(pngFilter);
                int option = fileChooser.showSaveDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String pathName = file.getPath();
                    if (pathName.endsWith(".png")) {
                        board.createPNGImageOfGraf(file.getAbsolutePath());
                    } else {
                        board.createPNGImageOfGraf(file.getAbsolutePath() + ".png");
                    }
                }
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save as PNG");
                FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.addChoosableFileFilter(pngFilter);
                fileChooser.setFileFilter(pngFilter);
                int option = fileChooser.showSaveDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String pathName = file.getPath();
                    if (pathName.endsWith(".png")) {
                        board.createPNGImage(file.getAbsolutePath());
                    } else {
                        board.createPNGImage(file.getAbsolutePath() + ".png");
                    }
                }
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

    private int calculateSize() {
        int size = WIDTH / 50;
        return size;
    }
}
