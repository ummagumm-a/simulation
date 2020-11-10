package simulator.viacheslav_sinii.plot_of_the_world;

import simulator.do_not_change.WorldController;

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
    public static int[][][] fields = new int[WorldController.MAX_ROWS][WorldController.MAX_COLS][2];
    public static String plot = "";

    /* Initialize the grid with empty cells */
    static {
        refresh();
    }

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
                plot += "| " + (char) fields[row][column][1] + " |";
            }
            plot += "\n";
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                plot += "└───┘";
            }
            plot += "\n";

        }
    }

    /**
     * This method is needed for refreshing the grid after the previous iteration.
     * It also is used for the very first initialization of the grid.
     * Each cell becomes empty.
     */
    static void refresh() {
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                fields[row][column][1] = ' ';
            }
        }
    }
}
