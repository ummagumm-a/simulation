package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.Simulator;

/**
 * This interface "extends" CapitalCase.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-18
 */
public interface RandomlyJumpable {
    /**
     * Change position to the random one.
     *
     * @param symbol the subject
     */
    default void randomJump(Symbol symbol) {
        WorldController.world.get(symbol.getPosition()).remove(symbol);

        symbol.getPosition().row = Simulator.random.nextInt(10);
        symbol.getPosition().column = Simulator.random.nextInt(10);

        WorldController.world.get(symbol.getPosition()).add(symbol);
    }
}
