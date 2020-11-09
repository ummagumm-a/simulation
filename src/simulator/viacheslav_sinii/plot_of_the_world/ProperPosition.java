package simulator.viacheslav_sinii.plot_of_the_world;

import simulator.do_not_change.Position;

/**
 * This class is made for convenience of using not changeable class Position.
 * Here we can freely write coordinates using class constructor.
 */
public class ProperPosition extends Position {
    public ProperPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
