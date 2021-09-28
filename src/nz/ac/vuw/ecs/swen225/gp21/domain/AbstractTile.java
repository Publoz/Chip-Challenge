package nz.ac.vuw.ecs.swen225.gp21.domain;

public abstract class AbstractTile implements Tile{
	protected Actor actor = null;
	

	public Actor getActor() {
		return actor;
	}
	
	/**
	 * Adding an actor to this tile
	 * 
	 * throws IllegalArgumentException if already contains an actor
	 * 
	 * @param actor the actor to add
	 */
	public void addActor(Actor actor) {
		if(this.actor == null) {
			this.actor = actor;
		} else {
			throw new IllegalArgumentException("Tile already contains actor");
		}
	}
	

	public void removeActor() {
		actor = null;
	}
	

	public void removeTreasure() {
		throw new IllegalStateException("This tile cannot contain treasure");
	}
	
	/**
	 * Default behavior, returns based on whether occupying actor
	 * isDeadly is false, otherwise return true.
	 * 
	 * @return a boolean if an actor can move here
	 */
	public boolean canMoveHere() {
		if(actor != null && actor.isDeadly() == false) {
			return false;
		} else {
			return true;
		}
	}
}
