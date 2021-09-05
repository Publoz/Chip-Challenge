package nz.ac.vuw.ecs.swen225.gp21.domain;

public abstract class AbstractTile implements Tile{
	protected String actor = "";
	
	public String getActor() {
		return actor;
	}
	
	public void addActor(String actor) {
		if(this.actor.equals("")) {
			this.actor = actor;
		} else {
			throw new IllegalArgumentException("Tile already contains actor");
		}
	}
	
	public boolean canMoveHere() {
		if(actor.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
