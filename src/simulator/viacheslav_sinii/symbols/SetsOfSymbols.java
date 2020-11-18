package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is used for storing and managing the information
 * about all living symbols and for assigning their ids.
 *
 * @author Sinii Viacheslav
 * @since 2020 -11-09
 */
public class SetsOfSymbols {
    /* Lists for all living symbols altogether and separately for each type of the symbol */
    private static List<Symbol> allSymbolsAlive = new LinkedList<>();
    private static List<SmallCase> allSmallCaseSymbolsAlive = new LinkedList<>();
    private static List<CapitalCase> allCapitalCaseSymbolsAlive = new LinkedList<>();
    private static List<Passive> allPassiveSymbolsAlive = new LinkedList<>();
    private static List<Aggressive> allAggressiveSymbolsAlive = new LinkedList<>();

    /**
     * Gets all symbols alive.
     *
     * @return the all symbols alive
     */
    public static List<Symbol> getAllSymbolsAlive() {
        return allSymbolsAlive;
    }

    /**
     * Gets all small case symbols alive.
     *
     * @return the all small case symbols alive
     */
    public static List<SmallCase> getAllSmallCaseSymbolsAlive() {
        return allSmallCaseSymbolsAlive;
    }

    /**
     * Gets all capital case symbols alive.
     *
     * @return the all capital case symbols alive
     */
    public static List<CapitalCase> getAllCapitalCaseSymbolsAlive() {
        return allCapitalCaseSymbolsAlive;
    }

    /**
     * Gets all passive symbols alive.
     *
     * @return the all passive symbols alive
     */
    public static List<Passive> getAllPassiveSymbolsAlive() {
        return allPassiveSymbolsAlive;
    }

    /**
     * Gets all aggressive symbols alive.
     *
     * @return the all aggressive symbols alive
     */
    public static List<Aggressive> getAllAggressiveSymbolsAlive() {
        return allAggressiveSymbolsAlive;
    }

    /**
     * This method adds new symbol to the list of all living symbols
     * and to the one that corresponds to the type of the symbol.
     *
     * @param symbol The symbol to add.
     */
    public static void add(Symbol symbol) {
        allSymbolsAlive.add(symbol);
        if (symbol instanceof SmallCase) {
            allSmallCaseSymbolsAlive.add((SmallCase) symbol);
        }
        if (symbol instanceof CapitalCase) {
            allCapitalCaseSymbolsAlive.add((CapitalCase) symbol);
        }
        if (symbol instanceof Passive) {
            allPassiveSymbolsAlive.add((Passive) symbol);
        }
        if (symbol instanceof Aggressive) {
            allAggressiveSymbolsAlive.add((Aggressive) symbol);
        }
    }

    /**
     * This method removes a symbol from the list of all living symbols
     * and from the one that corresponds to the type of the symbol.
     *
     * @param symbol The symbol to delete.
     */
    public static void kill(Symbol symbol) {
        allSymbolsAlive.remove(symbol);
        allSmallCaseSymbolsAlive.remove(symbol);
        allCapitalCaseSymbolsAlive.remove(symbol);
        allPassiveSymbolsAlive.remove(symbol);
        allAggressiveSymbolsAlive.remove(symbol);
    }
}
