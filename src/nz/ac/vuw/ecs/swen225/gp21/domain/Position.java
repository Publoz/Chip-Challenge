package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Position {
	

	private int row;
	private int col;
	
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public Position movePos(String dir) {
		switch(dir) {
		case "w":
			return new Position(row-1, col);
		case "s":
			return new Position(row+1, col);
		case "a":
			return new Position(row, col-1);
		case "d":
			return new Position(row, col+1);
		default:
			throw new IllegalArgumentException("Invalid direction");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
