package simulator.do_not_change;

public class Position {
	public int row;
	public int column;

	@Override
	public int hashCode() {
		int prime = 31;
		int hash = 1;
		hash = prime * hash + Integer.hashCode(row);
		hash = prime * hash + Integer.hashCode(column);
		return hash;
	}

}
