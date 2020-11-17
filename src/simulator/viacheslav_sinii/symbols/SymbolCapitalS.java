package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Aggressive;
import simulator.do_not_change.CapitalCase;
import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;

public class SymbolCapitalS extends Symbol implements Aggressive, CapitalCase {
    public SymbolCapitalS() {
        idSymbol = Symbol.COUNT_SYMBOLS++;
    }

    @Override
    public void attackSmart() {

    }

    @Override
    public void jump() {

    }

    @Override
    public void move() {

    }

    @Override
    public void die() {
        SetsOfSymbols.kill(this);
        WorldController.world.get(this.position).remove(this);
    }
}
