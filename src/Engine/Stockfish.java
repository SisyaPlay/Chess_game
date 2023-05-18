package Engine;

import Controllers.BoardController;
import Utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.*;

public class Stockfish {

    private BufferedWriter writer;
    private BufferedReader reader;
    private String figureLocation;
    private static final String PATH_TO_STOCKFISH = "././Engine/stockfish-windows-2022-x86-64-avx2.exe";
    private static final String DIR_TO_STOCKFISH = "././Engine/";
    private final String REGEX = "(?<=bestmove )[a-h][1-8][a-h][1-8](?= ponder)";

    private Pattern pattern = Pattern.compile(REGEX);

    private ArrayList<String> positions = new ArrayList<>();
    private BoardController bc;

    private final String SET_SF_POSITIONS = "position startpos move ";
    private final String SET_SF_TIME = "go btime ";

    private final String SF_IS_READY = "isready";
    private final String SF_READY_OK = "readyok";

    public AtomicReference<String> sfResultOutput = new AtomicReference<>("");;


    public Stockfish(BoardController bc) {
        this.bc = bc;
        try {
            startEngine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startEngine() throws IOException {
        Process p = new ProcessBuilder(PATH_TO_STOCKFISH).start();
        InputStream processInputStream = p.getInputStream();
        OutputStream processOutputStream = p.getOutputStream();

        writer = new BufferedWriter(new OutputStreamWriter(processOutputStream));

        reader = new BufferedReader(new InputStreamReader(processInputStream));
    }

    public void sendIsReadyCommand() throws IOException {
        writer.write(SF_IS_READY + "\n");
        writer.flush();
    }

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

    public void output() throws IOException {
        Runnable basic = () ->
        {
            try {
                while (true) {
                    String text = reader.readLine();

                    if (text.contains("bestmove")) {
                        String code = text.split("bestmove ")[1].split(" ")[0];
                        positions.add(code);
                        int old_col = Utils.getInstance().decodeWord(code.charAt(0));
                        int old_row = Utils.getInstance().decodeWord(code.charAt(1));
                        int new_col = Utils.getInstance().decodeWord(code.charAt(2));
                        int new_row = Utils.getInstance().decodeWord(code.charAt(3));

                        bc.setSelectedFigure(old_row, old_col);
                        bc.setSelectedFigureX(old_col);
                        bc.setSelectedFigureY(old_row);
                        bc.moveFigure(new_col, new_row);
                        bc.blackStop();

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
}
