package nz.ac.vuw.ecs.swen225.gp21.domain;

public interface Tile {

	public String toString();
	

	/**
	 * To see whether an actor can move to this tile
	 * 
	 * @return a boolean if the actor can move here
	 */
	public boolean canMoveHere();	
	
	public String getActor();
	
	public void addActor(String actor);
	
	public void removeActor();
	
	public void removeTreasure();
	
	default public boolean hasTreasure() {
		return false;
	}
	
	default public boolean hasKey() {
		return false;
	}
	
	default public String getKey() {
		return null;
	}
	
	
	
}
