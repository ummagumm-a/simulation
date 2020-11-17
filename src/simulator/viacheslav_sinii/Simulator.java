package simulator.viacheslav_sinii;

import simulator.do_not_change.Position;
import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;
import simulator.viacheslav_sinii.symbols.SetsOfSymbols;
import simulator.viacheslav_sinii.symbols.SymbolSmallR;

public class Simulator {

    private static final int numberOfIterations = 1000;

    public static void main(String[] args) throws Exception {
        displayWorld();
    }

    private static void displayWorld() throws Exception {
        Scene scene = new Scene();
//        Position pos1 = new ProperPosition(1, 1);
//        Position pos2 = new ProperPosition(1, 1);
//        Position pos3 = new ProperPosition(3, 2);
//        LinkedList<Symbol> list1 = new LinkedList<Symbol>();
//        list1.add(new SymbolCapitalP());
//        WorldController.world.put(pos1, list1);
//        LinkedList<Symbol> list2 = new LinkedList<Symbol>();
//        list2.add(new SymbolCapitalP());
//        WorldController.world.put(pos3, list2);
//        System.out.println(WorldController.world.get(pos2));
//        System.out.println("pos1 == pos2 " + (pos1.hashCode() == pos2.hashCode()));
//        System.out.println("pos1 == pos3 " + (pos1.hashCode() == pos3.hashCode()));


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
