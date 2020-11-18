package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.Simulator;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

/**
 * This interface "extends" defines mechanism of move for symbol.
 */
public interface RandomlyMoveable {
    /**
     * Movement in random direction.
     *
     * @param symbol the subject
     */
    default void randomMove(Symbol symbol) {

        int i = Simulator.random.nextInt(5);

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
