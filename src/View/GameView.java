package View;

import Controllers.BoardController;
import Engine.Stockfish;
import Figures.Figure;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Trida gameView zpusti okno a sachy v okne
 * Dedi od JFrame
 */
public class GameView extends JFrame{


    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 800; // Sirka onka
    private static final int HEIGHT = 600; // Vyska okna
    private ChessBoardView board;
    private final Panel rightPanel = new Panel(); // Pravy panel
    private final Panel leftPanel = new Panel(); // Levy panel
    private JPanel labelPanel;
    private JPanel buttonPanel;
    private JMenuBar menuBar = new JMenuBar(); // Menu bar
    private JMenu menuGame = new JMenu("Game"); // Menu hra, ma restart, a dva exporty do PNG
    private JMenu menuHelp = new JMenu("Help"); // Menu Help, zatim ne pouziva
    private JMenuItem item = new JMenuItem("Restart"); // Tlacitko restart, restartuje hru
    private JMenuItem item2 = new JMenuItem("Export graph"); // Tlacitko exportuje graf do PNG formatu
    private JMenuItem item3 = new JMenuItem("Export to PNG"); // Tlacitko exportuje sachovnice a figury do PNG formatu
    private JMenuItem item4 = new JMenuItem("Pause");
    public JLabel timer1 = new JLabel("White 00:00"); // Prvni label na casovac
    public JLabel timer2 = new JLabel("Black 00:00"); // Druhy label na casovac
    private JButton playVsPlBttn = new JButton("Play VS. Player");
    private JButton playVsBotBttn = new JButton("Play VS. StockFish");
    private KilledFigureView whiteFigureView;
    private KilledFigureView blackFigureView;
    private boolean isTimerRunning = false;
    private boolean gameStarted = false;
    private boolean playVSBot;

    /**
     * Konstruktor tridy GameView
     */
    public GameView() {}

    public void createStartMenu(JFrame frame) {
        frame.setTitle("UPG Sachy Mukanov - A22B0388P");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Chess");
        label.setFont(new Font("Times new roman", Font.PLAIN, 150));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        //labelPanel.add(label);


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        Font font = new Font("Times new roman", Font.PLAIN, 18);
        playVsPlBttn.setFont(font);
        playVsPlBttn.setPreferredSize(new Dimension(200, 50));

        playVsBotBttn.setFont(font);
        playVsBotBttn.setPreferredSize(new Dimension(200, 50));

        buttonPanel.add(Box.createVerticalGlue()); // добавляем "пружину" сверху
        buttonPanel.add(label);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(playVsPlBttn);
        buttonPanel.add(Box.createVerticalStrut(10)); // добавляем промежуток между кнопками
        buttonPanel.add(playVsBotBttn);
        buttonPanel.add(Box.createVerticalGlue()); // добавляем "пружину" снизу

        playVsPlBttn.setAlignmentX(Component.CENTER_ALIGNMENT); // выравниваем по центру горизонтально
        playVsBotBttn.setAlignmentX(Component.CENTER_ALIGNMENT);

        playVsPlBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().remove(buttonPanel);
                frame.getContentPane().remove(labelPanel);
                playVSBot = false;
                startNewGame(frame);
            }
        });

        playVsBotBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().remove(buttonPanel);
                frame.getContentPane().remove(labelPanel);
                playVSBot = true;
                startNewGame(frame);
            }
        });

        frame.getContentPane().add(labelPanel, BorderLayout.NORTH);
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startNewGame(JFrame frame) {
        JPanel dialogPanel = new JPanel();

        JDialog dialog = new JDialog(frame, "Game Settings", true);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (!gameStarted) {
                    frame.getContentPane().add(labelPanel, BorderLayout.NORTH);
                    frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();
                }
            }
        });

        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Choose time in minutes:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<Integer> comboBox = new JComboBox<>(new Integer[] {2, 5, 10});
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(100, comboBox.getPreferredSize().height));

        JButton startButton = new JButton("Start");
        GameView gv = this;
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedTime = (int) comboBox.getSelectedItem();
                board = new ChessBoardView(gv, playVSBot);
                board.setGameMinuts(selectedTime);
                frame.getContentPane().remove(dialogPanel);
                createGame(frame);
                dialog.dispose();
                gameStarted = true;
            }
        });

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (!gameStarted) {
                    frame.getContentPane().add(labelPanel, BorderLayout.NORTH);
                    frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();
                }
            }
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        dialogPanel.add(Box.createVerticalGlue());
        dialogPanel.add(label);
        dialogPanel.add(Box.createVerticalStrut(10));
        dialogPanel.add(comboBox);
        dialogPanel.add(Box.createVerticalStrut(10));
        dialogPanel.add(startButton);
        dialogPanel.add(Box.createVerticalGlue());

        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(250, 150));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().add(dialogPanel);
        dialog.pack();
        dialog.setVisible(true);
    }




    /**
     * Vytvori okno s hrou
     * @param frame
     */
    private void createGame(JFrame frame) {
        board.startGame();

        whiteFigureView = board.whiteFigureView;
        blackFigureView = board.blackFigureView;

        timer1.setFont(new Font("Verdana", Font.PLAIN, resize()));
        timer2.setFont(new Font("Verdana", Font.PLAIN, resize()));

        item.addActionListener(new ActionListener() {
            Object[] options = {"Yes", "Cancel"};
            @Override
            public void actionPerformed(ActionEvent e) {
                    board.getBoardController().whiteStop();
                    board.getBoardController().blackStop();
                    if (JOptionPane.showOptionDialog(null, "Are you sure?", "Confirmation",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]) == JOptionPane.YES_OPTION) {
                        board.restart();
                        item4.setText("Pause");
                        isTimerRunning = false;
                    } else {
                        if (BoardController.getCurrentPlayer().equals(Color.WHITE)) {
                            board.getBoardController().whiteStart();
                        } else {
                            board.getBoardController().blackStart();
                        }
                    }
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

        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isTimerRunning) {
                    board.getBoardController().whiteStop();
                    board.getBoardController().blackStop();
                    //board.getBoardController().setAnimationIsOn(true);
                    item4.setText("Play");
                    isTimerRunning = true;
                } else {
                    if(BoardController.getCurrentPlayer().equals(Color.WHITE)) {
                        board.getBoardController().whiteStart();
                    } else {
                        board.getBoardController().blackStart();
                    }
                    //board.getBoardController().setAnimationIsOn(false);
                    item4.setText("Pause");
                    isTimerRunning = false;
                }
            }
        });

        // Menu
        menuBar.add(menuGame);
        menuBar.add(menuHelp);
        menuGame.add(item);
        menuGame.add(item4);
        menuGame.add(item2);
        menuGame.add(item3);
        this.setJMenuBar(menuBar);

        // Set layout
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        // Right panel
        this.getContentPane().add(rightPanel, BorderLayout.EAST);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.add(timer1);
        rightPanel.add(whiteFigureView);
        resizableComponent(rightPanel, whiteFigureView, timer1);

        // Left panel
        this.getContentPane().add(leftPanel, BorderLayout.WEST);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(timer2);
        leftPanel.add(blackFigureView);
        resizableComponent(leftPanel, blackFigureView, timer2);

        this.getContentPane().add(board, BorderLayout.CENTER);

        this.pack();
    }

    private void resizableComponent(Panel panel, KilledFigureView figureView, JLabel timer) {
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                timer.setFont(new Font("Times new roman", Font.BOLD, resize()));
            }
        });
    }

    private int resize() {
        int windowWidth = getContentPane().getWidth();
        int windowHeight = getContentPane().getHeight();

        double scaleFactor = Math.min((double) windowWidth / WIDTH, (double) windowHeight / HEIGHT);

        int scaledSquareSize = (int) (15 * scaleFactor);
        return scaledSquareSize;
    }

    public void setTextToTimer1(String time) {
        timer1.setText(time);
    }
    public void setTextToTimer2(String time) {
        timer2.setText(time);
    }
}
