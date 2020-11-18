package simulator.viacheslav_sinii;

import simulator.do_not_change.Position;
import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;
import simulator.viacheslav_sinii.symbols.SetsOfSymbols;
import simulator.viacheslav_sinii.symbols.SymbolSmallR;

import java.util.LinkedList;

public class Simulator {

    private static final int numberOfIterations = 1000;

    public static void main(String[] args) throws Exception {
        displayWorld();
    }

    private static void displayWorld() throws Exception {
        Scene scene = new Scene();

        for (int i = 0; i < 1000; i++) {
            clearScreen();
            System.out.println(scene.plotWorld());
            Thread.sleep(1000);
            Scene.day += 1;
        }


    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
