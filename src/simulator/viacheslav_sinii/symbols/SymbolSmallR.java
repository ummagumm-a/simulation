package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Passive;
import simulator.do_not_change.SmallCase;
import simulator.do_not_change.Symbol;

public class SymbolSmallR extends Symbol implements Passive, SmallCase {
    public SymbolSmallR() {
        idSymbol = SetsOfSymbols.idCounter++;
    }

    @Override
    public void move() {

    }

    @Override
    public void die() {

    }

    @Override
    public void escape() {

    }

    @Override
    public void moveBreed() {

    }

    @Override
    public void upgrade() {

    }
}
