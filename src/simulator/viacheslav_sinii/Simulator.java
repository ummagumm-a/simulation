package simulator.viacheslav_sinii;

import simulator.do_not_change.Symbol;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;
import simulator.viacheslav_sinii.symbols.*;

import java.io.IOException;
import java.util.Map;

public class Simulator {
    public static void main(String[] args) throws InterruptedException, IOException {
//        SetsOfSymbols symbols = new SetsOfSymbols();
//        symbols.allSymbolsAlive.put(symbols.idCounter, new SymbolCapitalP());
//        Position pos = new Position();
//        pos.row = 5;
//        pos.column = 5;
//        symbols.allSymbolsAlive.get(symbols.idCounter - 1).setPosition(pos);
//        symbols.allSymbolsAlive.put(symbols.idCounter, new SymbolCapitalR());
//        Position pos1 = new Position();
//        pos1.row = 6;
//        pos1.column = 6;
//        symbols.allSymbolsAlive.get(symbols.idCounter - 1).setPosition(pos1);
//        symbols.allSymbolsAlive.put(symbols.idCounter, new SymbolSmallS());
//        Position pos2 = new Position();
//        pos2.row = 7;
//        pos2.column = 7;
//        symbols.allSymbolsAlive.get(symbols.idCounter - 1).setPosition(pos2);
//        display(symbols);

        Scene scene = new Scene();
        for (int iteration = 0; iteration < 1000; iteration++) {
            System.out.println(scene.plotWorld());
            Thread.sleep(2000);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
    }

    private static void display(SetsOfSymbols symbols) {
        for (Map.Entry<Integer, Symbol> tmp:
                symbols.allSymbolsAlive.entrySet()){
            System.out.println(tmp.getKey() + " " + tmp.getValue());
        }
    }

    public static void initialize() {

    }
}
