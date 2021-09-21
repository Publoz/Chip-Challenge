package nz.ac.vuw.ecs.swen225.gp21.domain;

public interface Tile {

	public String toString();
	

	/**
	 * To see whether an actor can move to this tile.
	 * 
	 * @return a boolean if the actor can move here
	 */
	public boolean canMoveHere();	
	
	/**
	 * Returns the actor in this tile.
	 * 
	 * @return a string of the actor name
	 */
	public Actor getActor();
	
	/**
	 * Adds an actor to this tile.
	 * @param actor the actor to add
	 */
	public void addActor(Actor actor);
	
	/**
	 * Remove the actor from this tile.
	 */
	public void removeActor();
	
	/**
	 * Remove the treasure from this tile.
	 */
	public void removeTreasure();
	
	/**
	 * Returns whether this tile has treasure.
	 * 
	 * Default returns false
	 * 
	 * @return a boolean if this has treasure
	 */
	default public boolean hasTreasure() {
		return false;
	}
	
	/**
	 * Returns whether this tile has a key.
	 * 
	 * Default is false
	 * 
	 * @return a boolean if this tile has a key
	 */
	default public boolean hasKey() {
		return false;
	}
	
	/**
	 * Get the key from this tile.
	 * 
	 * Default is return null
	 * 
	 * @return
	 */
	default public String getKey() {
		return null;
	}
	
	
	
}
