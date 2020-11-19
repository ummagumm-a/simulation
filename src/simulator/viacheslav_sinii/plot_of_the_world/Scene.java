package simulator.viacheslav_sinii.plot_of_the_world;

import simulator.do_not_change.*;
import simulator.viacheslav_sinii.Simulator;
import simulator.viacheslav_sinii.symbols.*;

import java.util.*;

import static simulator.viacheslav_sinii.plot_of_the_world.Grid.clearTheWorld;

/**
 * This class is used for plotting the world (scene of actions)
 * and its inhabitants to the console. On each iteration this class is used
 * for updating the state of the symbols and the world.
 *
 * @author Sinii Viacheslav
 * @since 2020 -11-09
 */
public class Scene extends WorldController {

    /**
     * Number of days passed since birth of the world.
     */
    private static int day = 0;
    /**
     * The constant PUBERTY_TERM - age when symbols can start to breed.
     */
    public static final int PUBERTY_TERM = 18;
    /**
     * The constant GROWING_TERM - age when SmallCase symbols grow.
     */
    public static final int GROWING_TERM = 25;
    /**
     * The constant LIFE_TERM - age when symbols die.
     */
    public static final int LIFE_TERM = 50;
    // Limit of symbols in the world
    private static final int LIMIT = 120;

    /**
     * Increase the number of days passed.
     */
    public static void increaseDay() {
        day++;
    }

    /**
     * Gets the number of days passed.
     *
     * @return the day
     */
    public static int getDay() {
        return day;
    }

    /**
     * Instantiates a new Scene.
     */
    public Scene() {
        // Fill world with empty lists for each possible position.
        world = new HashMap<>();
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                world.put(new Position(row, column), new LinkedList<>());
            }
        }

        birthOfTheWorld();
    }

    /**
     * Invoke method move() for each symbol and increase age of a symbol.
     *
     * @param symbols
     */
    @Override
    public void symbolsMove(List<Symbol> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).move();
            symbols.get(i).becomeOlder();
        }
    }

    /**
     * Invoke method die() for each symbol if reached age of death.
     *
     * @param symbols
     */
    @Override
    public void symbolsDie(List<Symbol> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getNumberIterationsAlive() == LIFE_TERM) {
                symbols.get(i).die();
                i--;
            }
        }
    }

    /**
     * Invoke method upgrade() for each SmallCase symbol.
     *
     * @param symbols
     */
    @Override
    public void smallCaseUpgrade(List<SmallCase> symbols) {
        int previousSize = symbols.size();
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).upgrade();
            // Compensate decrease of size of the list if symbol has been deleted.
            if (previousSize != symbols.size()) {
                i--;
                previousSize = symbols.size();
            }
        }
    }

    /**
     * Invoke method jump() for each CapitalCase symbol.
     *
     * @param symbols
     */
    @Override
    public void capitalCaseJump(List<CapitalCase> symbols) {
        for (CapitalCase tmp :
                symbols) {
            tmp.jump();
        }
    }

    /**
     * Invoke method escape() for each Passive symbol.
     *
     * @param symbols
     */
    @Override
    public void passiveEscape(List<Passive> symbols) {
        for (Passive tmp :
                symbols) {
            tmp.escape();
        }
    }

    /**
     * Invoke method moveBreed() for each Passive symbol.
     *
     * @param symbols
     */
    @Override
    public void passiveBreed(List<Passive> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).moveBreed();
        }
    }

    /**
     * Invoke method attackSmart() for each Aggressive symbol.
     *
     * @param symbols
     */
    @Override
    public void aggressiveAttackSmart(List<Aggressive> symbols) {
        for (Aggressive tmp :
                symbols) {
            tmp.attackSmart();
        }
    }

    /* This method checks whether there are several symbols in one position and manages death of weaker ones */
    private void battle() {
        for (Map.Entry<Position, LinkedList<Symbol>> entry :
                WorldController.world.entrySet()) {
            int numberOfSs = 0;
            int numberOfPs = 0;
            int numberOfRs = 0;

            for (Symbol symbol :
                    entry.getValue()) {
                if (symbol instanceof SymbolCapitalR || symbol instanceof SymbolSmallR) {
                    numberOfRs++;
                }
                if (symbol instanceof SymbolCapitalS || symbol instanceof SymbolSmallS) {
                    numberOfSs++;
                }
                if (symbol instanceof SymbolCapitalP || symbol instanceof SymbolSmallP) {
                    numberOfPs++;
                }
            }

            if (numberOfPs > 0 && numberOfRs > 0 && numberOfSs > 0) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    entry.getValue().get(i).die();
                    i--;
                }
            }

            if (numberOfPs == 0 && numberOfRs > 0 && numberOfSs > 0) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getValue().get(i) instanceof SymbolCapitalS || entry.getValue().get(i) instanceof SymbolSmallS) {
                        entry.getValue().get(i).die();
                        i--;
                    }
                }
            } else if (numberOfPs > 0 && numberOfRs == 0 && numberOfSs > 0) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getValue().get(i) instanceof SymbolCapitalP || entry.getValue().get(i) instanceof SymbolSmallP) {
                        entry.getValue().get(i).die();
                        i--;
                    }
                }
            } else if (numberOfPs > 0 && numberOfRs > 0 && numberOfSs == 0) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getValue().get(i) instanceof SymbolCapitalR || entry.getValue().get(i) instanceof SymbolSmallR) {
                        entry.getValue().get(i).die();
                        i--;
                    }
                }
            }
        }
    }

    /**
     * This method does not give a symbol to go over borders of the world.
     *
     * @param symbol the symbol which position needs to be corrected.
     */
    public static void cut(Symbol symbol) {
        if (symbol.getPosition().row > 9) {
            symbol.getPosition().row = 9;
        }
        if (symbol.getPosition().row < 0) {
            symbol.getPosition().row = 0;
        }

        if (symbol.getPosition().column > 9) {
            symbol.getPosition().column = 9;
        }
        if (symbol.getPosition().column < 0) {
            symbol.getPosition().column = 0;
        }
    }

    /**
     * This method updates world and constructs the updated world.
     *
     * @return plot of the world.
     */
    @Override
    public String plotWorld() {
        correction();
        if (day != 0) action();
        Grid.constructPlot();

        return Grid.getPlot();
    }

    /* If symbols are committing sins too much, God becomes angry and kills all of them */
    private void correction() {
        int population = SetsOfSymbols.getAllSymbolsAlive().size();
        if (population > LIMIT) {

            for (int i = 0; i < population; i++) {
                SetsOfSymbols.getAllSymbolsAlive().get(0).die();
            }
            clearTheWorld();
        }
    }

    /* In this method all interactions and changes in the state of a symbol are performed */
    private void action() {
        symbolsMove(SetsOfSymbols.getAllSymbolsAlive());
        capitalCaseJump(SetsOfSymbols.getAllCapitalCaseSymbolsAlive());
        passiveEscape(SetsOfSymbols.getAllPassiveSymbolsAlive());
        aggressiveAttackSmart(SetsOfSymbols.getAllAggressiveSymbolsAlive());
        passiveBreed(SetsOfSymbols.getAllPassiveSymbolsAlive());
        smallCaseUpgrade(SetsOfSymbols.getAllSmallCaseSymbolsAlive());
        symbolsDie(SetsOfSymbols.getAllSymbolsAlive());
        battle();
    }

    /*
     * This method is used for randomly allocating some amount of units of each type onto the grid
     * in the beginning of the program.
     */
    private void birthOfTheWorld() {
        /* As we use random, we need to be sure that two units will not be placed
         * into the one cell. Variable occupiedPositions is used to check whether
         * a cell is already occupied. */
        Map<Position, Boolean> occupiedPositions = new HashMap<>();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLS; column++) {
                occupiedPositions.put(new Position(row, column), false);
            }
        }

        Symbol symbol;

        for (int i = 0; i < 5; i++) {
            symbol = new SymbolCapitalP();
            createSymbol(occupiedPositions, symbol);
            for (int j = 0; j < Scene.GROWING_TERM; j++) {
                symbol.becomeOlder();
            }
        }

        for (int i = 0; i < 5; i++) {
            symbol = new SymbolCapitalR();
            createSymbol(occupiedPositions, symbol);
            for (int j = 0; j < Scene.GROWING_TERM; j++) {
                symbol.becomeOlder();
            }
        }

        for (int i = 0; i < 5; i++) {
            symbol = new SymbolCapitalS();
            createSymbol(occupiedPositions, symbol);
            for (int j = 0; j < Scene.GROWING_TERM; j++) {
                symbol.becomeOlder();
            }
        }

        for (int i = 0; i < 5; i++) {
            createSymbol(occupiedPositions, new SymbolSmallP());
        }

        for (int i = 0; i < 5; i++) {
            createSymbol(occupiedPositions, new SymbolSmallR());
        }
//
        for (int i = 0; i < 5; i++) {
            createSymbol(occupiedPositions, new SymbolSmallS());
        }
    }

    /* In this method we randomly choose an empty cell for allocating the unit. */
    private void createSymbol(Map<Position, Boolean> occupiedPositions, Symbol symbol) {
        // We keep choosing random coordinates until an empty cell is found.
        while (true) {
            int row = Simulator.random.nextInt(MAX_ROWS);
            int column = Simulator.random.nextInt(MAX_COLS);
            boolean isOccupied = occupiedPositions.get(new Position(row, column));
            if (!isOccupied) {
                // We assign new symbol to the chosen cell and specify its position.
                symbol.setPosition(new Position(row, column));
                SetsOfSymbols.add(symbol);
                WorldController.world.get(symbol.getPosition()).add(symbol);
                // Specify that the chosen cell is not empty now.
                occupiedPositions.put(new Position(row, column), true);
                break;
            }
        }
    }

}
