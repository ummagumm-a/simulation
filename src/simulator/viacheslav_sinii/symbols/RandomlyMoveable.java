package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

import java.util.Random;

public interface RandomlyMoveable {
    default void randomMove(Symbol symbol) {
        Random random = new Random();
        int i = random.nextInt(5);

        WorldController.world.get(symbol.getPosition()).remove(symbol);

        switch (i) {
            case 1:
                symbol.getPosition().row -= 1;
                break;
            case 2:
                symbol.getPosition().row += 1;
                break;
            case 3:
                symbol.getPosition().column += 1;
                break;
            case 4:
                symbol.getPosition().column -= 1;
                break;
            default:
                break;
        }

        Scene.cut(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

    }
}
