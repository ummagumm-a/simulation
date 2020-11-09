package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Aggressive;
import simulator.do_not_change.CapitalCase;
import simulator.do_not_change.Symbol;

public class SymbolCapitalR extends Symbol implements Aggressive, CapitalCase {
    public SymbolCapitalR() {
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
    public void jump() {

    }
}
