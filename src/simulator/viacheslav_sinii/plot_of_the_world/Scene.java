package simulator.viacheslav_sinii.plot_of_the_world;

import simulator.do_not_change.*;
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

    public Scene() {
        birthOfTheWorld();
        assignContent();

        world = new HashMap<>();
        for (Symbol symbol :
                SetsOfSymbols.allSymbolsAlive) {
            LinkedList<Symbol> tmp = new LinkedList<>();
            tmp.add(symbol);
            world.put(symbol.getPosition(), tmp);
        }
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
        for (Symbol tmp :
                symbols) {
            tmp.die();
        }
    }

    @Override
    public void smallCaseUpgrade(List<SmallCase> symbols) {
        for (SmallCase tmp :
                symbols) {
            tmp.upgrade();
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
        for (Passive tmp :
                symbols) {
            tmp.moveBreed();
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
         * into the one cell. Variable isOccupied is used to check whether
         * a cell is already occupied. */
        Map<Integer, Boolean> isOccupied = new HashMap<>();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLS; column++) {
                isOccupied.put(row * 10 + column, false);
            }
        }

        for (int i = 0; i < 10; i++) {
            createSymbol(isOccupied, new SymbolCapitalP());
        }

//        for (int i = 0; i < 10; i++) {
//            createSymbol(isOccupied, new SymbolCapitalR());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(isOccupied, new SymbolCapitalS());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(isOccupied, new SymbolSmallP());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(isOccupied, new SymbolSmallR());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            createSymbol(isOccupied, new SymbolSmallS());
//        }
    }

    /* In this method we randomly choose an empty cell for allocating the unit.
     * This method is used only in the very beginning. */
    private <T extends Symbol> void createSymbol(Map<Integer, Boolean> isOccupied, T symbol) {
        Random random = new Random();

        /* We keep choosing random coordinates until an empty cell is found. */
        while (true) {
            int row = random.nextInt(MAX_ROWS);
            int column = random.nextInt(MAX_COLS);
            if (!isOccupied.get(row * 10 + column)) {
                // We assign new symbol to the chosen cell and specify its position.
                symbol.setPosition(new ProperPosition(row, column));
                SetsOfSymbols.add(symbol);
                // Specify that the chosen cell is non-empty now.
                isOccupied.put(row * 10 + column, true);
                break;
            }
        }
    }

}
