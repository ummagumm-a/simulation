package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

public interface AdvancedSmallCase {
    default void upgradeTo(Symbol symbol, Symbol into) {
        if (symbol.getNumberIterationsAlive() == Scene.growingTerm) {
            into.setPosition(symbol.getPosition());
            WorldController.world.get(symbol.getPosition()).add(into);
            SetsOfSymbols.add(into);
            symbol.die();
            for (int i = 0; i < Scene.growingTerm; i++) {
                into.becomeOlder();
            }
        }
    }
}
