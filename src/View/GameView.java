package View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Trida gameView zpusti okno a sachy v okne
 * Dedi od JFrame
 */
public class GameView extends JFrame{


    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 800; // Sirka onka
    private static final int HEIGHT = 600; // Vyska okna
    private final Panel rightPanel = new Panel(); // Pravy panel
    private final Panel leftPanel = new Panel(); // Levy panel
    private JMenuBar menuBar = new JMenuBar(); // Menu bar
    private JMenu menuGame = new JMenu("Game"); // Menu hra, ma restart, a dva exporty do PNG
    private JMenu menuHelp = new JMenu("Help"); // Menu Help, zatim ne pouziva
    private JMenuItem item = new JMenuItem("Restart"); // Tlacitko restart, restartuje hru
    private JMenuItem item2 = new JMenuItem("Export graph"); // Tlacitko exportuje graf do PNG formatu
    private JMenuItem subItem1 = new JMenuItem("Linear graf");
    private JMenuItem subItem2 = new JMenuItem("Bar graf");
    private JMenuItem item3 = new JMenuItem("Export to PNG"); // Tlacitko exportuje sachovnice a figury do PNG formatu
    public JLabel timer1; // Prvni label na casovac
    public JLabel timer2; // Druhy label na casovac
    //private JLabel countOfKillerFig1 = new JLabel("countOfKillerFig1"); // Pocet zabitych figur, zatim nepouziva
    //private JLabel countOfKillerFig2 = new JLabel("countOfKillerFig1");
    private JButton startButton;
    private JButton settingsButton;

    /**
     * Konstruktor tridy GameView
     */
    public GameView() {}

    public void startMenu(JFrame frame) {
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        settingsButton = new JButton("Settings");
        buttonPanel.add(startButton);
        buttonPanel.add(settingsButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Add action listeners to the buttons
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove the buttons
                buttonPanel.remove(startButton);
                buttonPanel.remove(settingsButton);

                // Add the chess board view
                createWindow(frame);
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add code for the settings button action here
            }
        });

        setVisible(true);
    }

    /**
     * Vytvori okno s hrou
     * @param frame
     */
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
                        board.createPNGImageOfGraf(file.getAbsolutePath(), true);
                    } else {
                        board.createPNGImageOfGraf(file.getAbsolutePath() + ".png", true);
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
        //leftPanel.add(countOfKillerFig1);
        //leftPanel.add(countOfKillerFig2);

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
