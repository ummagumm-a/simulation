package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class SymbolCapitalS extends Symbol implements Aggressive, CapitalCase, RandomlyMoveable, RandomlyJumpable {

    boolean isAttacking;
    boolean isJumping;

    Symbol closest = null;
    HashSet<Symbol> blackList = new HashSet<>();
    LinkedList<Symbol> possiblePreys = new LinkedList<>();


    public SymbolCapitalS() {
        idSymbol = Symbol.COUNT_SYMBOLS++;
        sightDistance = 4;
        numberIterationsAlive = 0;
    }

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

    protected int[] calculateDirection() {
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
                        if (symbol instanceof SymbolCapitalP || symbol instanceof SymbolSmallP) {
                            possiblePreys.add(symbol);
                        }
                    }

                }
            }
        }
    }

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

    @Override
    public void jump() {
        Random random = new Random();
        boolean mayJump = random.nextBoolean();
        if (isJumping && mayJump) {
            randomJump(this);
        }
    }

    @Override
    public void move() {
        breed();

        isAttacking = false;
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

    @Override
    public void die() {
        SetsOfSymbols.kill(this);
        WorldController.world.get(this.position).remove(this);
    }

    // Breed
    private void breed() {
        int numberOfChildren = 0;

        for (Symbol symbol :
                WorldController.world.get(this.position)) {
            if (symbol instanceof SymbolCapitalS) {
                if (!this.equals(symbol) && !blackList.contains(symbol)) {
                    numberOfChildren++;
                    blackList.add((SymbolCapitalS) symbol);
                    ((SymbolCapitalS) symbol).blackList.add(this);
                }
            }
        }

        createChildren(numberOfChildren);
    }

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
            createSymbol(adjacentPositions, new SymbolSmallS());
        }

    }

    public void createSymbol(HashMap<Integer, Position> adjacentPositions, Symbol symbol) {
        Random random = new Random();
        int randomPosition = random.nextInt(adjacentPositions.size()) + 1;

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
