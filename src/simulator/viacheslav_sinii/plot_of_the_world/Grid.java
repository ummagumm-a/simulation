package simulator.viacheslav_sinii.plot_of_the_world;

import simulator.do_not_change.Position;
import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.symbols.*;

import java.util.LinkedList;
import java.util.Map;

/**
 * This class is our world. It manages the proper output of the grid
 * and contents of its cells to the console.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-18
 */
public class Grid {

    private static char[][] charactersOfInnerSymbols = new char[WorldController.MAX_ROWS][WorldController.MAX_COLS];
    private static String plot = "";

    /**
     * Return char two-dimension array - view of the world.
     *
     * @return the plot
     */
    public static String getPlot() {
        return plot;
    }

    private Grid() {}

    /**
     * This method defines the view of the grid in the console.
     * It constructs a box for each cell. This box contains a character
     * which corresponds to the symbol that is currently placed there.
     */
    public static void constructPlot() {
        updateFields();
        plot = "Day: " + Scene.getDay() + "\n";
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                plot += "┌───┐";
            }
            plot += "\n";
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                plot += "| " + charactersOfInnerSymbols[row][column] + " |";
            }
            plot += "\n";
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                plot += "└───┘";
            }
            plot += "\n";
        }
    }

    /* This method assigns the corresponding letter of a symbol that is placed in a cell to each cell */
    private static char getCharacterForSymbol(Symbol symbol) {
        if (symbol instanceof SymbolCapitalP) {
            return WorldController.CAPITAL_P;
        } else if (symbol instanceof SymbolCapitalR) {
            return WorldController.CAPITAL_R;
        } else if (symbol instanceof SymbolCapitalS) {
            return WorldController.CAPITAL_S;
        } else if (symbol instanceof SymbolSmallP) {
            return WorldController.SMALL_P;
        } else if (symbol instanceof SymbolSmallR) {
            return WorldController.SMALL_R;
        } else if (symbol instanceof SymbolSmallS) {
            return WorldController.SMALL_S;
        } else {
            return ' ';
        }
    }

    /* This method updates information about letters of symbols in each cell. */
    private static void updateFields() {
        for (Map.Entry<Position, LinkedList<Symbol>> entry :
                WorldController.world.entrySet()) {
            try {
                charactersOfInnerSymbols[entry.getKey().row][entry.getKey().column]
                        = getCharacterForSymbol(entry.getValue().get(0));
            } catch (IndexOutOfBoundsException e) {
                // In case there are no symbols in a position
                charactersOfInnerSymbols[entry.getKey().row][entry.getKey().column] = ' ';
            }
        }
    }

    /* This method forces the world clear the world from all symbols
     * This is quick fix of some bug. */
    public static void clearTheWorld() {
        for (int i = 0; i < WorldController.MAX_ROWS; i++) {
            for (int j = 0; j < WorldController.MAX_COLS; j++) {
                WorldController.world.get(new Position(i, j)).clear();
            }
        }
    }
}
