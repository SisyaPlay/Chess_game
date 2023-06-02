package Engine;

import Controllers.BoardController;
import Utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.*;

/**
 * Trida StockFish pusti engine a posila do konzole prikazy
 */
public class Stockfish {

    private BufferedWriter writer; // Posila prikazy
    private BufferedReader reader; // Cte z konzoly
    private String figureLocation; // Pozice figury
    private static final String PATH_TO_STOCKFISH = "././Engine/stockfish-windows-2022-x86-64-avx2.exe"; // Cesta do enginu
    private static final String DIR_TO_STOCKFISH = "././Engine/"; // Cesta do enginu
    private final String REGEX = "(?<=bestmove )[a-h][1-8][a-h][1-8](?= ponder)"; // Regularni vyraz
    private Pattern pattern = Pattern.compile(REGEX); // Patern
    private ArrayList<String> positions = new ArrayList<>(); // List pozici
    private BoardController bc; // Kontroler sachovnice
    private final String SET_SF_POSITIONS = "position startpos move "; // Prikaz na pohyb
    private final String SET_SF_TIME = "go btime "; // Prikaz na cekani na odpoved o StockFish
    private final String SF_IS_READY = "isready"; // Prikaz kontroly je-li hotov
    private final String SF_QUIT = "quit"; // Vychod
    private final String SF_READY_OK = "readyok"; // Je hotov

    /**
     * Konstruktor pusti aplikace enginu
     * @param bc
     */
    public Stockfish(BoardController bc) {
        this.bc = bc;
        try {
            startEngine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pusteni enginu
     * @throws IOException
     */
    public void startEngine() throws IOException {
        Process p = new ProcessBuilder(PATH_TO_STOCKFISH).start();
        InputStream processInputStream = p.getInputStream();
        OutputStream processOutputStream = p.getOutputStream();

        writer = new BufferedWriter(new OutputStreamWriter(processOutputStream));

        reader = new BufferedReader(new InputStreamReader(processInputStream));
    }

    /**
     * Pusti prikaz je-li hotov
     * @throws IOException
     */
    public void sendIsReadyCommand() throws IOException {
        writer.write(SF_IS_READY + "\n");
        writer.flush();
    }

    /**
     * Udela prikaz na pohyb
     * @param position
     * @param timer
     * @throws IOException
     */
    public void sendPositionCommand(String position, int timer) throws IOException {
        figureLocation = position;

        // Nastavit atkualni pozici figur pro Stockfish
        if(figureLocation != null) {
            positions.add(figureLocation);
            StringBuilder setPosition = new StringBuilder(SET_SF_POSITIONS);

            for (String s : positions) {
                setPosition.append(s).append(" ");
            }
            writer.write(setPosition.toString() +  "\n");
            writer.flush();
            // Spustit Stockfish
            writer.write(SET_SF_TIME + Integer.toString(timer) +  "\n");
            writer.flush();

            System.out.println(setPosition);
            System.out.println(SET_SF_TIME + Integer.toString(timer));
        }
    }

    /**
     * Precte output a najde pozice pohybu
     * @throws IOException
     */
    public void output() throws IOException {
        Runnable basic = () ->
        {
            try {
                while (true) {
                    String text = reader.readLine();

                    if (text.contains("bestmove")) {
                        String code = text.split("bestmove ")[1].split(" ")[0];
                        positions.add(code);
                        int old_col = Utils.getInstance().decodeWord(code.charAt(0)); // Pocatecni pozice figury
                        int old_row = Utils.getInstance().decodeWord(code.charAt(1));
                        int new_col = Utils.getInstance().decodeWord(code.charAt(2)); // Konecna pozice figury
                        int new_row = Utils.getInstance().decodeWord(code.charAt(3));
                        Thread.sleep(1000); // Odpocivani na 1 sekundu

                        bc.setSelectedFigure(old_row, old_col);
                        bc.setSelectedFigureX(old_col);
                        bc.setSelectedFigureY(old_row);
                        bc.moveFigure(new_col, new_row);

                        System.out.println(code);

                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(basic).start();
    }

    /**
     * Prepusti engine
     */
    public void restartEngine() {
        try {
            writer.write(SF_QUIT);
            positions = new ArrayList<String>();
            startEngine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
