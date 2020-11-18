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
    public static final int pubertyTerm = 6;
    public static final int growingTerm = 10;
    public static final int lifeTerm = 18;

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
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).move();
            symbols.get(i).becomeOlder();
        }
    }

    @Override
    public void symbolsDie(List<Symbol> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getNumberIterationsAlive() == lifeTerm) {
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

    @Override
    public String plotWorld() {
        if (day != 0) {
            symbolsMove(SetsOfSymbols.allSymbolsAlive);
            capitalCaseJump(SetsOfSymbols.allCapitalCaseSymbolsAlive);
            passiveEscape(SetsOfSymbols.allPassiveSymbolsAlive);
            aggressiveAttackSmart(SetsOfSymbols.allAggressiveSymbolsAlive);
            passiveBreed(SetsOfSymbols.allPassiveSymbolsAlive);
            smallCaseUpgrade(SetsOfSymbols.allSmallCaseSymbolsAlive);
            symbolsDie(SetsOfSymbols.allSymbolsAlive);
            battle();
        }
//        Grid.refresh();
//        assignContent();
        Grid.constructPlot();

        return Grid.plot;
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

//        Symbol symbol = new SymbolCapitalR();
//        symbol.setPosition(new Position(2, 4));
//        SetsOfSymbols.add(symbol);
//        WorldController.world.get(symbol.getPosition()).add(symbol);

        Symbol symbol = new SymbolCapitalP();
        symbol.setPosition(new Position(2,7));
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

        symbol = new SymbolCapitalR();
        symbol.setPosition(new Position(2, 4));
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

        symbol = new SymbolSmallR();
        symbol.setPosition(new Position(2,1));
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

        symbol = new SymbolSmallS();
        symbol.setPosition(new Position(5,8));
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

        symbol = new SymbolSmallR();
        symbol.setPosition(new Position(4,4));
        SetsOfSymbols.add(symbol);
        WorldController.world.get(symbol.getPosition()).add(symbol);

//        Symbol symbol;
//        for (int i = 0; i < 5; i++) {
//            symbol = new SymbolCapitalP();
//            symbol.setPosition(new Position(4, i));
//            WorldController.world.get(symbol.getPosition()).add(symbol);
//            SetsOfSymbols.add(symbol);
//        }


//        for (int i = 0; i < 5; i++) {
//            createSymbol(occupiedPositions, new SymbolCapitalP());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(occupiedPositions, new SymbolCapitalR());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(occupiedPositions, new SymbolCapitalS());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(occupiedPositions, new SymbolSmallP());
//        }
//
//        for (int i = 0; i < 5; i++) {
//            createSymbol(occupiedPositions, new SymbolSmallR());
//        }
//
//        for (int i = 0; i < 5; i++) {
//            createSymbol(occupiedPositions, new SymbolSmallS());
//        }
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
