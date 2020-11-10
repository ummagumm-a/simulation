package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    public static List<Symbol> allSymbolsAlive = new LinkedList<>();
    public static List<SmallCase> allSmallCaseSymbolsAlive = new LinkedList<>();
    public static List<CapitalCase> allCapitalCaseSymbolsAlive = new LinkedList<>();
    public static List<Passive> allPassiveSymbolsAlive = new LinkedList<>();
    public static List<Aggressive> allAggressiveSymbolsAlive = new LinkedList<>();

    public static int idCounter = 1;

    /** This method adds a new symbol to the list of all living symbols
     * and to the one that corresponds to the type of the symbol.
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

    /** This method removes a symbol from the list of all living symbols
     * and from the one that corresponds to the type of the symbol.
     * @param symbol The symbol to delete.
     */
    public static <T extends Symbol> void kill(T symbol) {
        allSymbolsAlive.remove(symbol);
        allSmallCaseSymbolsAlive.remove(symbol);
        allCapitalCaseSymbolsAlive.remove(symbol);
        allPassiveSymbolsAlive.remove(symbol);
        allAggressiveSymbolsAlive.remove(symbol);
    }
}
