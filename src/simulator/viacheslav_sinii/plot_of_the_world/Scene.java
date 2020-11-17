package simulator.viacheslav_sinii.plot_of_the_world;

import simulator.do_not_change.*;
import simulator.viacheslav_sinii.Simulator;
import simulator.viacheslav_sinii.symbols.*;

import java.util.*;

/**
 * This class is used for plotting the world (scene of actions)
 * and its inhabitants to the console. On each iteration this class is used
 * for updating the state of the symbols and the world.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-09
 */
public class Scene extends WorldController {

    public static int day = 0;

    public Scene() {
        world = new HashMap<>();
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                world.put(new Position(row, column), new LinkedList<>());
            }
        }

        birthOfTheWorld();
//        for (Symbol symbol :
//                SetsOfSymbols.allSymbolsAlive) {
//            LinkedList<Symbol> tmp = new LinkedList<>();
//            tmp.add(symbol);
//            world.put(symbol.getPosition(), tmp);
//        }
    }

    @Override
    public void symbolsMove(List<Symbol> symbols) {
        for (Symbol tmp :
                symbols) {
            tmp.move();
        }
    }

    @Override
    public void symbolsDie(List<Symbol> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getNumberIterationsAlive() == 12) {
                symbols.get(i).die();
                i--;
            }
        }
    }

    @Override
    public void smallCaseUpgrade(List<SmallCase> symbols) {
        int previousSize = symbols.size();
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).upgrade();
            if (previousSize != symbols.size()) {
                i--;
                previousSize = symbols.size();
            }
        }
    }

    @Override
    public void capitalCaseJump(List<CapitalCase> symbols) {
        for (CapitalCase tmp :
                symbols) {
            tmp.jump();
        }
    }

    @Override
    public void passiveEscape(List<Passive> symbols) {
        for (Passive tmp :
                symbols) {
            tmp.escape();
        }
    }

    @Override
    public void passiveBreed(List<Passive> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).moveBreed();
        }
    }

    @Override
    public void aggressiveAttackSmart(List<Aggressive> symbols) {
        for (Aggressive tmp :
                symbols) {
            tmp.attackSmart();
        }
    }

    @Override
    public String plotWorld() {
//        Grid.refresh();
        if (day != 0) {
            symbolsMove(SetsOfSymbols.allSymbolsAlive);
            symbolsDie(SetsOfSymbols.allSymbolsAlive);
            smallCaseUpgrade(SetsOfSymbols.allSmallCaseSymbolsAlive);
//            capitalCaseJump(SetsOfSymbols.allCapitalCaseSymbolsAlive);
//            passiveEscape(SetsOfSymbols.allPassiveSymbolsAlive);
            passiveBreed(SetsOfSymbols.allPassiveSymbolsAlive);
//            aggressiveAttackSmart(SetsOfSymbols.allAggressiveSymbolsAlive);
            assignContent();
        }
        Grid.constructPlot();

        return Grid.plot;
    }

    /** This method iterates through the list of living symbols,
     * finds where they are placed and puts id of a symbol and
     * corresponding character to that position.
     */
    private void assignContent() {
        for (Symbol entry :
                SetsOfSymbols.allSymbolsAlive) {
            Grid.fields[entry.getPosition().row][entry.getPosition().column] = entry;
        }
    }

    /**
     * This method is used for randomly allocating 10 units of each type onto the grid
     * in the beginning of the program.
     */
    private void birthOfTheWorld() {
        /* As we use random, we need to be sure that two units will not be placed
         * into the one cell. Variable occupiedPositions is used to check whether
         * a cell is already occupied. */
        Map<Position, Boolean> occupiedPositions = new HashMap<>();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLS; column++) {
                occupiedPositions.put(new Position(row * MAX_ROWS, column), false);
            }
        }

        Symbol symbol = new SymbolSmallR();
        symbol.setPosition(new Position(5,3));
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

        symbol = new SymbolSmallR();
        symbol.setPosition(new Position(5,5));
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

//        symbol = new SymbolSmallR();
//        symbol.setPosition(new Position(4,4));
//        SetsOfSymbols.add(symbol);
//        WorldController.world.get(symbol.getPosition()).add(symbol);

//        Symbol symbol = new SymbolSmallP();
//        symbol.setPosition(new Position(4,4));
//        WorldController.world.get(symbol.getPosition()).add(symbol);
//        SetsOfSymbols.allSymbolsAlive.add(symbol);
//        SetsOfSymbols.allSmallCaseSymbolsAlive.add((SmallCase) symbol);

//        for (int i = 0; i < 10; i++) {
//            createSymbol(occupiedPositions, new SymbolCapitalP());
//        }

//        for (int i = 0; i < 10; i++) {
//            createSymbol(occupiedPositions, new SymbolCapitalR());
//        }

//        for (int i = 0; i < 10; i++) {
//            createSymbol(occupiedPositions, new SymbolCapitalS());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(occupiedPositions, new SymbolSmallP());
//        }
//
        for (int i = 0; i < 5; i++) {
            createSymbol(occupiedPositions, new SymbolSmallR());
        }
//
        for (int i = 0; i < 5; i++) {
            createSymbol(occupiedPositions, new SymbolSmallS());
        }
    }

    /* In this method we randomly choose an empty cell for allocating the unit.
     * This method is used only in the very beginning. */
    private <T extends Symbol> void createSymbol(Map<Position, Boolean> occupiedPositions, T symbol) {
        Random random = new Random();

        /* We keep choosing random coordinates until an empty cell is found. */
        while (true) {
            int row = random.nextInt(MAX_ROWS);
            int column = random.nextInt(MAX_COLS);
            boolean isOccupied = occupiedPositions.get(new Position(row * 10, column));
            if (!isOccupied) {
                // We assign new symbol to the chosen cell and specify its position.
                symbol.setPosition(new Position(row, column));
                SetsOfSymbols.add(symbol);
                WorldController.world.get(symbol.getPosition()).add(symbol);
                // Specify that the chosen cell is non-empty now.
                occupiedPositions.put(new Position(row * 10, column), true);
                break;
            }
        }
    }

    private <T extends Symbol> void createSymbol(T symbol, Position pos) {
        symbol.setPosition(pos);
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);
    }


}
