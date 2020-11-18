package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

import java.util.Random;

public interface RandomlyJumpable {
    default void randomJump(Symbol symbol) {
        Random random = new Random();
        WorldController.world.get(symbol.getPosition()).remove(symbol);

        symbol.getPosition().row = random.nextInt(10);
        symbol.getPosition().column = random.nextInt(10);

        WorldController.world.get(symbol.getPosition()).add(symbol);
    }
}
