package simulator.viacheslav_sinii;

import simulator.do_not_change.Symbol;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;
import simulator.viacheslav_sinii.symbols.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Simulator {

    private static final int numberOfIterations = 1000;

    public static void main(String[] args) throws Exception {
        displayWorld();
    }

    private static void displayWorld() throws Exception {
        Scene scene = new Scene();

        for (int i = 0; i < 1000; i++) {
            System.out.println(scene.plotWorld());
            Thread.sleep(60000);
            clearScreen();
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
