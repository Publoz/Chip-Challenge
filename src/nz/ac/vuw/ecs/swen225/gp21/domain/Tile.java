package nz.ac.vuw.ecs.swen225.gp21.domain;

public interface Tile {

	public String toString();
	

	/**
	 * To see whether an actor can move to this tile
	 * 
	 * @return a boolean if the actor can move here
	 */
	public boolean canMoveHere();	
	
}
