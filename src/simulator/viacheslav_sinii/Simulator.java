package simulator.viacheslav_sinii;

import simulator.viacheslav_sinii.plot_of_the_world.Scene;
import simulator.viacheslav_sinii.symbols.SetsOfSymbols;

import java.util.Random;

/**
 * The type Simulator.
 */
public class Simulator {

    private static final int numberOfIterations = 1000;
    private static final int durationOfDay = 100; // Milliseconds

    /**
     * The constant random.
     */
    public static Random random = new Random();

    /**
     * This is the entry point of our program.
     *
     * @param args the input arguments
     * @throws Exception the exception
     * @author Sinii Viacheslav
     * @since 2020 -11-18
     */
    public static void main(String[] args) throws Exception {
        displayWorld();
    }

    /* This method is used to display changes in world to console */
    private static void displayWorld() throws Exception {
        Scene scene = new Scene();

        for (int i = 0; i < numberOfIterations; i++) {
            if (SetsOfSymbols.getAllSymbolsAlive().size() == 0) {
                System.out.println(Scene.getDay());
                break;
            }
            clearScreen();
            System.out.println(scene.plotWorld());
            Thread.sleep(durationOfDay);
            Scene.increaseDay();
        }

    }

    /* This method clears console */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
