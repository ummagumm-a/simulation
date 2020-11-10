package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.*;
import simulator.viacheslav_sinii.plot_of_the_world.Grid;
import simulator.viacheslav_sinii.plot_of_the_world.ProperPosition;
import simulator.viacheslav_sinii.plot_of_the_world.Scene;

import java.util.ArrayList;
import java.util.Random;

public class SymbolCapitalP extends Symbol implements Passive, CapitalCase {

    ArrayList<SymbolCapitalP> similarSymbolsWithinSightDistance = new ArrayList<>();
    ArrayList<Symbol> dangerousSymbolsWithinSightDistance = new ArrayList<>();
    ArrayList<Symbol> neutralSymbolsWithinSightDistance = new ArrayList<>();

    double[] destination;
    double denominator = 0.0;

    public SymbolCapitalP() {
        idSymbol = SetsOfSymbols.idCounter++;
        sightDistance = 3;
        numberIterationsAlive = 50;
        position = new ProperPosition(0, 0);
        destination = new double[]{position.row, position.column};
    }

    @Override
    public void move() {
        senseOtherSymbols();
        WorldController.world.get(this.position).remove(this);
        int[] movement = calculateDirection();
        position.row += movement[0];
        position.column += movement[1];
        Grid.fields[position.row][position.column] = this;
        WorldController.world.get(this.position).add(this);

    }

    @Override
    public void die() {
        SetsOfSymbols.kill(this);
//        Grid.fields[this.position.row][this.position.column][1] = ' ';
        WorldController.world.get(this.position).remove(this);
    }

    @Override
    public void jump() {
        if (isSurrounded() || !doesMateExist()) {
            Random random = new Random();
            destination[0] = random.nextInt(WorldController.MAX_ROWS);
            destination[1] = random.nextInt(WorldController.MAX_COLS);
        }
    }

    /* Check whether all adjacent cells are occupied by not-similar symbol */
    private boolean isSurrounded() {
        return (Grid.fields[position.row + 1][position.column] != null
                    && !(Grid.fields[position.row + 1][position.column] instanceof SymbolCapitalP))
                && (Grid.fields[position.row - 1][position.column] != null
                    && !(Grid.fields[position.row - 1][position.column] instanceof SymbolCapitalP))
                && (Grid.fields[position.row][position.column + 1] != null
                    && !(Grid.fields[position.row][position.column + 1] instanceof SymbolCapitalP))
                && (Grid.fields[position.row][position.column - 1] != null
                    && !(Grid.fields[position.row][position.column - 1] instanceof SymbolCapitalP));
    }

    /* Check whether there are similar symbols in the sight distance */
    private boolean doesMateExist() {
        return !similarSymbolsWithinSightDistance.isEmpty();
    }

//    /* Check whether there are symbols which can be killed in the sight distance */
//    private boolean doesPreyExist() {
//        return
//    }

    @Override
    public void escape() {
        for (Symbol symbol :
                dangerousSymbolsWithinSightDistance) {
            calculateDestination(symbol, 3);
        }

        for (Symbol symbol :
                neutralSymbolsWithinSightDistance) {
            calculateDestination(symbol, 1);
        }

    }

    @Override
    public void moveBreed() {
        for (Symbol symbol :
                similarSymbolsWithinSightDistance) {
            calculateDestination(symbol, -2);
        }
    }

    private void senseOtherSymbols() {
        ArrayList<Position> visiblePositions = getCellsWithinSightDistance();
        for (Position pos :
                visiblePositions) {
            Symbol symbolInTheCell = Grid.fields[pos.row][pos.column];
            if (symbolInTheCell instanceof SymbolCapitalP) {
                similarSymbolsWithinSightDistance.add((SymbolCapitalP) symbolInTheCell);
            } else if (symbolInTheCell instanceof SymbolSmallS || symbolInTheCell instanceof SymbolCapitalS) {
                dangerousSymbolsWithinSightDistance.add(symbolInTheCell);
            } else if (symbolInTheCell != null) {
                neutralSymbolsWithinSightDistance.add(symbolInTheCell);
            }
        }
    }

    private ArrayList<Position> getCellsWithinSightDistance() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                if (Math.abs(position.row - row) + Math.abs(position.column - column) <= sightDistance) {
                    positions.add(new ProperPosition(row, column));
                }
            }
        }

        return positions;
    }

    private void calculateDestination(Symbol symbol, int dangerCoefficient) {
        int distance = Math.abs(symbol.getPosition().row - position.row) + Math.abs(symbol.getPosition().column - position.column);
        int coefficient = dangerCoefficient * (sightDistance - distance + 1);
        destination[0] += coefficient * (symbol.getPosition().row - position.row);
        destination[1] += coefficient * (symbol.getPosition().column - position.column);
        denominator += coefficient;
    }

    private int[] calculateDirection() {
        destination[0] /= denominator;
        destination[1] /= denominator;

        double rowGreater = destination[0] - position.row;
        double columnGreater = destination[1] - position.column;

        if (Math.abs(rowGreater) > Math.abs(columnGreater)) {
            if (rowGreater > 0) {
                return new int[]{1, 0};
            } else {
                return new int[]{-1, 0};
            }
        } else {
            if (columnGreater > 0) {
                return new int[]{0, 1};
            } else {
                return new int[]{0, -1};
            }
        }
    }
}
