package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Door extends AbstractTile{
	
	private String colour;
	
	public Door(String col) {
		this.colour = col;
	}
	
	public String getColour() {
		return colour;
	}

	@Override
	public boolean canMoveHere() {
		return false;
	}
	
	@Override
	public String toString() {
		return colour;
	}

}
