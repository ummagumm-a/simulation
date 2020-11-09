package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used for storing and managing the information
 * about all living symbols and for assigning their ids.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-09
 */
public class SetsOfSymbols {
    /* HashMaps for all living symbols altogether and separately for each type of the symbol
     * Key is the id of a symbol and value is the symbol itself. */
    public static Map<Integer, Symbol> allSymbolsAlive = new HashMap<>();
    public static Map<Integer, SmallCase> allSmallCaseSymbolsAlive = new HashMap<>();
    public static Map<Integer, CapitalCase> allCapitalCaseSymbolsAlive = new HashMap<>();
    public static Map<Integer, Passive> allPassiveSymbolsAlive = new HashMap<>();
    public static Map<Integer, Aggressive> allAggressiveSymbolsAlive = new HashMap<>();

    public static int idCounter = 1;

    /** This method adds a new symbol to the list of all living symbols
     * and to the one that corresponds to the type of the symbol.
     * @param symbol The symbol to add.
     */
    public static void add(Symbol symbol) {
        allSymbolsAlive.put(symbol.getIdSymbol(), symbol);
        if (symbol instanceof SmallCase) {
            allSmallCaseSymbolsAlive.put(symbol.getIdSymbol(), (SmallCase) symbol);
        }
        if (symbol instanceof CapitalCase) {
            allCapitalCaseSymbolsAlive.put(symbol.getIdSymbol(), (CapitalCase) symbol);
        }
        if (symbol instanceof Passive) {
            allPassiveSymbolsAlive.put(symbol.getIdSymbol(), (Passive) symbol);
        }
        if (symbol instanceof Aggressive) {
            allAggressiveSymbolsAlive.put(symbol.getIdSymbol(), (Aggressive) symbol);
        }
    }

    /** This method removes a symbol from the list of all living symbols
     * and from the one that corresponds to the type of the symbol.
     * @param symbol The symbol to delete.
     */
    public static void kill(Symbol symbol) {
        allSymbolsAlive.remove(symbol.getIdSymbol());
        allSmallCaseSymbolsAlive.remove(symbol.getIdSymbol());
        allCapitalCaseSymbolsAlive.remove(symbol.getIdSymbol());
        allPassiveSymbolsAlive.remove(symbol.getIdSymbol());
        allAggressiveSymbolsAlive.remove(symbol.getIdSymbol());
    }
}
