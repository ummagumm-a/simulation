package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;
import simulator.viacheslav_sinii.Simulator;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * The class for capital p symbol.
 *
 * @author Sinii Viacheslav
 * @since 2020-11-18
 */
public class SymbolCapitalP extends Symbol implements Passive, CapitalCase, RandomlyMoveable, RandomlyJumpable {
    public boolean isPaired;
    public boolean isPassive;
    public boolean isEscaping;
    public boolean isBreeding;
    public boolean isJumping;

    Symbol closest = null;
    HashSet<Symbol> blackList = new HashSet<>();
    LinkedList<SymbolCapitalP> potentialPartners = new LinkedList<>();
    LinkedList<Symbol> enemies = new LinkedList<>();

    /**
     * Instantiates a new Symbol capital p.
     */
    public SymbolCapitalP() {
        idSymbol = Symbol.COUNT_SYMBOLS++;
        sightDistance = 4;
        numberIterationsAlive = 0;
    }

    /**
     * Here symbol decides what to do. If there are no other symbols nearby, it starts to move or jumps.
     */
    @Override
    public void move() {
        isPaired = false;
        isPassive = false;
        isEscaping = false;
        isBreeding = false;
        isJumping = false;
        potentialPartners.clear();
        enemies.clear();

        scan();
        closest = findClosest();

        if (closest instanceof SymbolCapitalP) {
            isBreeding = true;
            if (this.position.manhattanDistance(closest.getPosition()) == 1) {
                pairForBreed((SymbolCapitalP) closest);
            }
        } else if (closest != null) {
            isEscaping = true;
            isJumping = true;
        } else {
            isJumping = true;
            randomMove(this);
        }
    }

    /* This method finds the closest symbol within sight distance */
    private Symbol findClosest() {
        Symbol closestDanger = null;
        Symbol closestPartner = null;
        int minDistanceToDanger = 10;
        int minDistanceToPartner = 10;

        for (SymbolCapitalP symbol :
                potentialPartners) {
            if (this.getPosition().manhattanDistance(symbol.getPosition()) < minDistanceToPartner) {
                closestPartner = symbol;
                minDistanceToPartner = this.getPosition().manhattanDistance(symbol.getPosition());
            }
        }

        for (Symbol symbol :
                enemies) {
            if (this.getPosition().manhattanDistance(symbol.getPosition()) < minDistanceToDanger) {
                closestDanger = symbol;
                minDistanceToDanger = this.getPosition().manhattanDistance(symbol.getPosition());
            }
        }

        return minDistanceToDanger > minDistanceToPartner ? closestPartner : closestDanger;
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
                        if (!this.equals(symbol)) {
                            if (symbol instanceof SymbolCapitalP) {
                                if (this.getNumberIterationsAlive() > Scene.PUBERTY_TERM && symbol.getNumberIterationsAlive() > Scene.PUBERTY_TERM
                                        && !blackList.contains(symbol)) {
                                    potentialPartners.add((SymbolCapitalP) symbol);
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

    /* In this method method decides in which direction to go. */
    private int[] calculateDirection() {
        int[] direction;
        int rowGreater = this.position.row - closest.getPosition().row;
        int columnGreater = this.position.column - closest.getPosition().column;

        if (isPassive || rowGreater == 0 && columnGreater == 0) {
            return new int[]{0, 0};
        }

        if (Math.abs(rowGreater) > Math.abs(columnGreater)) {
            if (rowGreater < 0) {
                direction = new int[]{1, 0};
            } else {
                direction = new int[]{-1, 0};
            }
        } else {
            if (columnGreater < 0) {
                direction = new int[]{0, 1};
            } else {
                direction = new int[]{0, -1};
            }
        }

        if (isEscaping) {
            direction[0] *= -1;
            direction[1] *= -1;
        }

        return direction;
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

    /**
     * In this method defined mechanism of escaping.
     */
    @Override
    public void escape() {
        if (isEscaping) {
            int[] direction = calculateDirection();
            WorldController.world.get(this.position).remove(this);
            this.position.row += direction[0];
            this.position.column += direction[1];
            Scene.cut(this);
            WorldController.world.get(this.position).add(this);
        }
    }

    /**
     * In this method mechanism of moving to the partner is defined.
     */
    @Override
    public void moveBreed() {
        if (isBreeding) {
            int[] direction = calculateDirection();
            WorldController.world.get(this.position).remove(this);
            this.position.row += direction[0];
            this.position.column += direction[1];
            Scene.cut(this);
            WorldController.world.get(this.position).add(this);
            breed();
        }
    }

    /* Method for breeding. Symbol interbreeds with every symbol in the same position.
     * After the breeding partners are added to black lists of each other. */
    private void breed() {
        int numberOfChildren = 0;

        for (Symbol symbol :
                WorldController.world.get(this.position)) {
            if (symbol instanceof SymbolCapitalP) {
                if (!this.equals(symbol) && !blackList.contains(symbol)) {
                    numberOfChildren++;
                    blackList.add((SymbolCapitalP) symbol);
                    ((SymbolCapitalP) symbol).blackList.add(this);
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
            createSymbol(adjacentPositions, new SymbolSmallP());
        }

    }

    /* New symbol (a child) is creating. After it is born, kid and parent are added to the black lists
     * of each other. Child is created on one of the adjacent positions. */
    private void createSymbol(HashMap<Integer, Position> adjacentPositions, Symbol symbol) {
        int randomPosition = Simulator.random.nextInt(adjacentPositions.size()) + 1;

        blackList.add(symbol);
        ((SymbolSmallP) symbol).blackList.add(this);
        for (Symbol tmp :
                WorldController.world.get(this.position)) {
            if (tmp instanceof SymbolCapitalP) {
                ((SymbolCapitalP) tmp).blackList.add(symbol);
            }
        }

        symbol.setPosition(adjacentPositions.get(randomPosition));
        WorldController.world.get(symbol.getPosition()).add(symbol);
        SetsOfSymbols.add(symbol);
    }

    /* If symbols are in adjacent positions, there is no way they can meet in the same one.
     * So firstly we specify that they are paired with each other.
     * Then we tell one of them to stay in its position and the other to move. */
    private void pairForBreed(SymbolCapitalP partner) {
        isPaired = true;
        if (partner.isPaired) {
            isPassive = !partner.isPassive;
            return;
        }

        partner.isPaired = true;

        isPassive = Simulator.random.nextBoolean();
        partner.isPassive = !isPassive;
    }

}
