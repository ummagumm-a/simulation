package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;
import simulator.viacheslav_sinii.Simulator;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

import java.util.*;

/**
 * The class for capital r symbol.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-18
 */public class SymbolCapitalR extends Symbol implements Aggressive, CapitalCase, RandomlyMoveable, RandomlyJumpable {

    boolean isAttacking;
    boolean isJumping;

    Symbol closest = null;
    HashSet<Symbol> blackList = new HashSet<>();
    LinkedList<Symbol> possiblePreys = new LinkedList<>();

    /**
     * Instantiates a new Symbol capital r.
     */
    public SymbolCapitalR() {
        idSymbol = Symbol.COUNT_SYMBOLS++;
        sightDistance = 4;
        numberIterationsAlive = 0;
    }

    /**
     * Here symbol decides what to do. If there are no other symbols nearby, it starts to move or jumps.
     */
    @Override
    public void move() {
        breed();

        isAttacking = false;
        isJumping = false;
        possiblePreys.clear();

        scan();
        closest = findClosest();

        if (closest != null) {
            isAttacking = true;
        } else {
            isJumping = true;
            randomMove(this);
        }
    }

    /**
 * In this method defined mechanism of dying
 */
    @Override
    public void die() {
        // Symbol is deleted from all lists it is in.
        SetsOfSymbols.kill(this);
        // Symbol is deleted from its position in the world.
        WorldController.world.get(this.position).remove(this);
    }

    /** In this method mechanism of moving for attacking is defined */
    @Override
    public void attackSmart() {
        if (isAttacking) {
            int[] direction = calculateDirection();
            WorldController.world.get(this.position).remove(this);
            this.position.row += direction[0];
            this.position.column += direction[1];
            Scene.cut(this);
            WorldController.world.get(this.position).add(this);
        }
    }

    /* In this method method decides in which direction to go. */
    private int[] calculateDirection() {
        int rowGreater = this.position.row - closest.getPosition().row;
        int columnGreater = this.position.column - closest.getPosition().column;

        if (rowGreater == 0 && columnGreater == 0) {
            return new int[]{0, 0};
        }
        if (Math.abs(rowGreater) > Math.abs(columnGreater)) {
            if (rowGreater < 0) {
                return new int[]{1, 0};
            } else {
                return new int[]{-1, 0};
            }
        } else {
            if (columnGreater < 0) {
                return new int[]{0, 1};
            } else {
                return new int[]{0, -1};
            }
        }
    }

    /* In this method symbols scans the world within sight distance for symbols which it's interested in. */
    private void scan() {
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                if (Math.abs(position.row - row) + Math.abs(position.column - column) <= sightDistance) {
                    Position pos = new Position(row, column);
                    LinkedList<Symbol> symbols;
                    try {
                        symbols = WorldController.world.get(pos);
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                    for (Symbol symbol :
                            symbols) {
                        if (symbol instanceof SymbolCapitalS || symbol instanceof SymbolSmallS) {
                            possiblePreys.add(symbol);
                        }
                    }

                }
            }
        }
    }

    /* This method finds the closest symbol within sight distance */
    private Symbol findClosest() {
        Symbol closestPrey = null;
        int minDistanceToPrey = 10;

        for (Symbol symbol :
                possiblePreys) {
            if (this.getPosition().manhattanDistance(symbol.getPosition()) < minDistanceToPrey) {
                closestPrey = symbol;
                minDistanceToPrey = this.getPosition().manhattanDistance(symbol.getPosition());
            }
        }

        return closestPrey;
    }

    /**
     * In this method defined mechanism of jumping.
     */
    @Override
    public void jump() {
        // 50% change to jump somewhere
        boolean mayJump = Simulator.random.nextBoolean();
        if (isJumping && mayJump) {
            randomJump(this);
        }
    }

    // Breed
    /* Method for breeding. Symbol interbreeds with every symbol in the same position.
     * After the breeding partners are added to black lists of each other. */
    private void breed() {
        int numberOfChildren = 0;

        for (Symbol symbol :
                WorldController.world.get(this.position)) {
            if (symbol instanceof SymbolCapitalR) {
                if (!this.equals(symbol) && !blackList.contains(symbol)) {
                    numberOfChildren++;
                    blackList.add(symbol);
                    ((SymbolCapitalR) symbol).blackList.add(this);
                }
            }
        }

        createChildren(numberOfChildren);
    }

    /* Create children in any of adjacent positions */
    private void createChildren(int n) {
        HashMap<Integer, Position> adjacentPositions = new HashMap<>();
        Position pos;
        int enumeration = 1;
        if (getPosition().row > 0) {
            pos = new Position(getPosition().row - 1, getPosition().column);
            adjacentPositions.put(enumeration++, pos);
        }
        if (getPosition().row < 9) {
            pos = new Position(getPosition().row + 1, getPosition().column);
            adjacentPositions.put(enumeration++, pos);
        }
        if (getPosition().column > 0) {
            pos = new Position(getPosition().row, getPosition().column - 1);
            adjacentPositions.put(enumeration++, pos);
        }
        if (getPosition().column < 9) {
            pos = new Position(getPosition().row, getPosition().column + 1);
            adjacentPositions.put(enumeration, pos);
        }

        for (int i = 0; i < n; i++) {
            createSymbol(adjacentPositions, new SymbolSmallR());
        }

    }

    /* New symbol (a child) is creating. After it is born, kid and parent are added to the black lists
     * of each other. Child is created on one of the adjacent positions. */
    private void createSymbol(Map<Integer, Position> adjacentPositions, Symbol symbol) {

        int randomPosition = Simulator.random.nextInt(adjacentPositions.size()) + 1;

        blackList.add(symbol);
        ((SymbolSmallR) symbol).blackList.add(this);
        for (Symbol tmp :
                WorldController.world.get(this.position)) {
            if (tmp instanceof SymbolCapitalR) {
                ((SymbolCapitalR) tmp).blackList.add(symbol);
            }
        }

        symbol.setPosition(adjacentPositions.get(randomPosition));
        WorldController.world.get(symbol.getPosition()).add(symbol);
        SetsOfSymbols.add(symbol);
    }

}
