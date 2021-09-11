package nz.ac.vuw.ecs.swen225.gp21.domain;

public abstract class AbstractTile implements Tile{
	protected String actor = "";
	

	public String getActor() {
		return actor;
	}
	
	/**
	 * Adding an actor to this tile
	 * 
	 * throws IllegalArgumentException if already contains an actor
	 * 
	 * @param actor the actor to add
	 */
	public void addActor(String actor) {
		if(this.actor.equals("")) {
			this.actor = actor;
		} else {
			throw new IllegalArgumentException("Tile already contains actor");
		}
	}
	

	public void removeActor() {
		actor = "";
	}
	

	public void removeTreasure() {
		throw new IllegalStateException("This tile cannot contain treasure");
	}
	
	/**
	 * Default behavior for a free tile, simply returns whether
	 * there is an actor here
	 * 
	 * @return a boolean if an actor can move here
	 */
	public boolean canMoveHere() {
		if(actor.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
