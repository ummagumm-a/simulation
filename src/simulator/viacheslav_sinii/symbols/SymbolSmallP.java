package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;

import java.util.Set;

public class SymbolSmallP extends AdvancedSymbol implements Aggressive, SmallCase {
    public SymbolSmallP() {
        idSymbol = Symbol.COUNT_SYMBOLS++;
        numberIterationsAlive = 0;
    }

    @Override
    public void senseOtherSymbols() {

    }

    @Override
    public void move() {


        becomeOlder();
    }

    @Override
    public void die() {
        SetsOfSymbols.kill(this);
        WorldController.world.get(this.position).remove(this);
    }

    @Override
    public void attackSmart() {

    }

    @Override
    public void upgrade() {
        if (this.numberIterationsAlive == 5) {
            Symbol symbol = new SymbolCapitalP();
            symbol.setPosition(this.position);
            WorldController.world.get(this.position).add(symbol);
            SetsOfSymbols.allSymbolsAlive.add(symbol);
            SetsOfSymbols.allCapitalCaseSymbolsAlive.add((CapitalCase) symbol);
            this.die();
        }
    }
}
