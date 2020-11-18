package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

/**
 * This interface "extends" SmallCase.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-18
 */
public interface AdvancedSmallCase {
    /**
     * This method defines mechanism of growing for SmallCase symbols.
     *
     * @param symbol the symbol which grows
     * @param into   grown version of the symbol
     */
    default void upgradeTo(Symbol symbol, Symbol into) {
        if (symbol.getNumberIterationsAlive() == Scene.GROWING_TERM) {
            into.setPosition(symbol.getPosition());
            WorldController.world.get(symbol.getPosition()).add(into);
            SetsOfSymbols.add(into);
            symbol.die();

            // Age of grown symbol should remain the same.
            for (int i = 0; i < Scene.GROWING_TERM; i++) {
                into.becomeOlder();
            }
        }
    }
}
