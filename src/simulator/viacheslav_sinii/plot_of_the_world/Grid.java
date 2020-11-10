package simulator.viacheslav_sinii.plot_of_the_world;

import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.symbols.*;

import static simulator.do_not_change.WorldController.*;

/**
 * This class is our world. It manages the proper output of the grid
 * and contents of its cells to the console.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-09
 */
public class Grid {
    /*
     * This field is 10x10 grid. Each cell is an array consisting of two elements:
     * an id of the symbol that currently is placed here and the character of that symbol.
     */
    public static Symbol[][] fields = new Symbol[WorldController.MAX_ROWS][WorldController.MAX_COLS];
    public static String plot = "";

    /* Initialize the grid with empty cells */
//    static {
//        refresh();
//    }

    /**
     * This method defines the view of the grid in the console.
     * It constructs a box for each cell. This box contains a character
     * which corresponds to the symbol that is currently placed here.
     */
    static void constructPlot() {
        plot = "";
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                plot += "┌───┐";
            }
            plot += "\n";
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                plot += "| " + getCharacterForSymbol(fields[row][column]) + " |";
            }
            plot += "\n";
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                plot += "└───┘";
            }
            plot += "\n";

        }
    }
    
    private static <T extends Symbol> char getCharacterForSymbol(T symbol) {
        if (symbol instanceof SymbolCapitalP) {
            return CAPITAL_P;
        } else if (symbol instanceof SymbolCapitalR) {
            return CAPITAL_R;
        } else if (symbol instanceof SymbolCapitalS) {
            return CAPITAL_S;
        } else if (symbol instanceof SymbolSmallP) {
            return SMALL_P;
        } else if (symbol instanceof SymbolSmallR) {
            return SMALL_R;
        } else if (symbol instanceof SymbolSmallS) {
            return SMALL_S;
        } else {
            return ' ';
        }
    }

    /**
     * This method is needed for refreshing the grid after the previous iteration.
     * It also is used for the very first initialization of the grid.
     * Each cell becomes empty.
     */
//    static void refresh() {
//        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
//            for (int column = 0; column < WorldController.MAX_COLS; column++) {
//                fields[row][column] = ' ';
//            }
//        }
//    }
}
