package simulator.do_not_change;

public abstract class Symbol {

	/**
	 * Unique for each symbol
	 */
	protected int idSymbol;
	protected Position position;
	protected int sightDistance;
	protected int numberIterationsAlive;

	public int getIdSymbol() {
		return idSymbol;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getSightDistance() {
		return sightDistance;
	}

	public int getNumberIterationsAlive() {
		return numberIterationsAlive;
	}

	/**
	 * Increase the number of iterations that symbol has been alive.
	 */
	public void becomeOlder() {
		this.numberIterationsAlive++;
	}

	// Moving randomly within its sightDistance
	public abstract void move();

	// The last words before dying
	public abstract void die();

	@Override
	/**
	 * This number will help to uniquely identify symbols in hash maps and hash
	 * tables. Only the id is considered since the values of the other attributes
	 * might change over time.
	 */
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + Integer.hashCode(idSymbol);
		return hash;
	}

}
