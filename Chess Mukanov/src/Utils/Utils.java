package Utils;

/**
 * Trida ktera jen decoduje pozice z pismen a cisel do souradnice
 */
public class Utils {
    private static Utils instance = new Utils();

    private Utils(){}

    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();

        return instance;
    }

    public int decodeWord(char s) {
        int place = 0;
        switch (s) {
            case 'a', '8' -> {
                place = 0;
                return place;
            }
            case 'b', '7' -> {
                place = 1;
                return place;
            }
            case 'c', '6' -> {
                place = 2;
                return place;
            }
            case 'd', '5' -> {
                place = 3;
                return place;
            }
            case 'e', '4' -> {
                place = 4;
                return place;
            }
            case 'f', '3' -> {
                place = 5;
                return place;
            }
            case 'g', '2' -> {
                place = 6;
                return place;
            }
            case 'h', '1' -> {
                place = 7;
                return place;
            }
        }
        return 0;
    }

}
