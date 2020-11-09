package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Aggressive;
import simulator.do_not_change.SmallCase;
import simulator.do_not_change.Symbol;

public class SymbolSmallP extends Symbol implements Aggressive, SmallCase {
    public SymbolSmallP() {
        idSymbol = SetsOfSymbols.idCounter++;
    }

    @Override
    public void move() {

    }

    @Override
    public void die() {

    }

    @Override
    public void attackSmart() {

    }

    @Override
    public void upgrade() {

    }
}
