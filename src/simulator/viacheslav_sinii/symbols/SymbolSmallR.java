package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class SymbolSmallR extends Symbol implements Passive, SmallCase {

    public boolean isPaired;
    public boolean isPassive;
    public boolean isEscaping;
    public boolean isBreeding;

    Symbol closest = null;
    HashSet<SymbolSmallR> blackList = new HashSet<>();
    LinkedList<SymbolSmallR> potentialPartners = new LinkedList<>();
    LinkedList<Symbol> enemies = new LinkedList<>();

    public SymbolSmallR() {
        idSymbol = Symbol.COUNT_SYMBOLS++;
        sightDistance = 3;
    }

    @Override
    public void move() {
        isPaired = false;
        isPassive = false;
        isEscaping = false;
        isBreeding = false;
        potentialPartners.clear();
        enemies.clear();

        scan();
        closest = findClosest();

        if (closest instanceof SymbolSmallR) {
            isBreeding = true;
            if (this.position.manhattanDistance(closest.getPosition()) == 1) {
                pairForBreed((SymbolSmallR) closest);
            }
        } else if (closest != null) {
            isEscaping = true;
        } else {
            // TODO: write random move
        }

        becomeOlder();
    }

    private Symbol findClosest() {
        Symbol closestDanger = null;
        Symbol closestPartner = null;
        int minDistanceToDanger = 10;
        int minDistanceToPartner = 10;

        for (SymbolSmallR symbol :
                potentialPartners) {
            if (this.getPosition().manhattanDistance(symbol.getPosition()) <= minDistanceToPartner) {
                closestPartner = symbol;
                minDistanceToPartner = this.getPosition().manhattanDistance(symbol.getPosition());
            }
        }

        for (Symbol symbol :
                enemies) {
            if (this.getPosition().manhattanDistance(symbol.getPosition()) <= minDistanceToPartner) {
                closestDanger = symbol;
                minDistanceToDanger = this.getPosition().manhattanDistance(symbol.getPosition());
            }
        }

        return minDistanceToDanger > minDistanceToPartner ? closestPartner : closestDanger;
    }

    private void scan() {
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                if (row == position.row && column == position.column) {
                    continue;
                }
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
                        if (!this.equals(symbol)) {
                            if (symbol instanceof SymbolSmallR) {
                                if (this.getNumberIterationsAlive() > 6 && symbol.getNumberIterationsAlive() > 6
                                        && !blackList.contains(symbol)) {
                                    potentialPartners.add((SymbolSmallR) symbol);
                                }
                            } else if (symbol != null) {
                                enemies.add(symbol);
                            }
                        }
                    }

                }
            }
        }
    }

    protected int[] calculateDirection() {
        if (isPassive) {
            return new int[]{0, 0};
        }

        int rowGreater = this.position.row - closest.getPosition().row;
        int columnGreater = this.position.column - closest.getPosition().column;

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

    @Override
    public void die() {
        SetsOfSymbols.kill(this);
        WorldController.world.get(this.position).remove(this);
    }

    @Override
    public void escape() {
        if (isEscaping) {

        }
    }

    @Override
    public void moveBreed() {
        if (isBreeding) {
            int[] direction = calculateDirection();
            WorldController.world.get(this.position).remove(this);
            this.position.row += direction[0];
            this.position.column += direction[1];
            cut();
            WorldController.world.get(this.position).add(this);
            breed();
        }
    }

    private void cut() {
        if (position.row > 9) {
            position.row = 9;
        }
        if (position.row < 0) {
            position.row = 0;
        }

        if (position.column > 9) {
            position.column = 9;
        }
        if (position.column < 0) {
            position.column = 0;
        }
    }

    private void breed() {
        int numberOfChildren = 0;

        for (Symbol symbol :
                WorldController.world.get(this.position)) {
            if (symbol instanceof SymbolSmallR) {
                if (!this.equals(symbol) && !blackList.contains(symbol)) {
                    numberOfChildren++;
                    blackList.add((SymbolSmallR) symbol);
                    ((SymbolSmallR) symbol).blackList.add(this);
                }
            }
        }

        createChildren(numberOfChildren);
    }

//    private int numberOfChildren(int n) {
//        int nFact = 1;
//        int n2Fact = 1;
//
//        for (int i = 0; i < n; i++) {
//            nFact *= i;
//        }
//
//        for (int i = 0; i < n - 2; i++) {
//            nFact *= i;
//        }
//
//        return (int) (nFact / (2.0 * n2Fact));
//
//    }

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

    public <T extends Symbol> void createSymbol(HashMap<Integer, Position> adjacentPositions, T symbol) {
        Random random = new Random();
        int randomPosition = random.nextInt(adjacentPositions.size()) + 1;

        symbol.setPosition(adjacentPositions.get(randomPosition));
        WorldController.world.get(adjacentPositions.get(randomPosition)).add(symbol);
        SetsOfSymbols.add(symbol);
    }

    public void pairForBreed(SymbolSmallR partner) {
        isPaired = true;
        if (partner.isPaired) {
            isPassive = !partner.isPassive;
            return;
        }

        partner.isPaired = true;
        Random random = new Random();
        isPassive = random.nextBoolean();
        partner.isPassive = !isPassive;
    }

    @Override
    public void upgrade() {
        if (this.numberIterationsAlive == 10) {
            Symbol symbol = new SymbolCapitalR();
            symbol.setPosition(this.position);
            WorldController.world.get(this.position).add(symbol);
            SetsOfSymbols.allSymbolsAlive.add(symbol);
            SetsOfSymbols.allCapitalCaseSymbolsAlive.add((CapitalCase) symbol);
            this.die();
        }
    }
}
