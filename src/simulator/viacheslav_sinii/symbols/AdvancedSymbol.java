package simulator.viacheslav_sinii.symbols;

import simulator.do_not_change.Position;
import simulator.do_not_change.Symbol;
import simulator.do_not_change.WorldController;
import simulator.viacheslav_sinii.plot_of_the_world.Grid;

import java.util.ArrayList;

public abstract class AdvancedSymbol extends Symbol {
    ArrayList<SymbolCapitalP> similarSymbolsWithinSightDistance = new ArrayList<>();
    ArrayList<Symbol> dangerousSymbolsWithinSightDistance = new ArrayList<>();
    ArrayList<Symbol> neutralSymbolsWithinSightDistance = new ArrayList<>();

    double[] destination;
    double denominator = 0.0;

    public AdvancedSymbol() {
        idSymbol = Symbol.COUNT_SYMBOLS++;
        numberIterationsAlive = 50;
        position = new Position(0, 0);
        destination = new double[]{position.row, position.column};
    }

    @Override
    public void die() {

    }

    public abstract void senseOtherSymbols();

    protected ArrayList<Position> getCellsWithinSightDistance() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int row = 0; row < WorldController.MAX_ROWS; row++) {
            for (int column = 0; column < WorldController.MAX_COLS; column++) {
                if (row == position.row && column == position.column) {
                    continue;
                }
                if (Math.abs(position.row - row) + Math.abs(position.column - column) <= sightDistance) {
                    positions.add(Grid.positions[row][column]);
                }
            }
        }

        return positions;
    }

    protected void calculateDestination(Symbol symbol, int dangerCoefficient) {
        int distance = Math.abs(symbol.getPosition().row - position.row) + Math.abs(symbol.getPosition().column - position.column);
        int coefficient = dangerCoefficient * (sightDistance - distance + 1);
        destination[0] += coefficient * (symbol.getPosition().row - position.row);
        destination[1] += coefficient * (symbol.getPosition().column - position.column);
        denominator += coefficient;
    }

    protected int[] calculateDirection() {
        destination[0] /= denominator;
        destination[1] /= denominator;

        double rowGreater = destination[0];
        double columnGreater = destination[1];

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
