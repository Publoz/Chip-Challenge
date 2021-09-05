package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Free extends AbstractTile{

	private String key = "";
	private boolean treasure = false;
	
	/**
	 * Constructor if this free tile contains something
	 * 
	 * @param key the colour of the key
	 * @param treasure if this tile contains treasure
	 */
	public Free(String key, boolean treasure) {
		this.key = key;
		this.treasure = treasure;
	}
	
	/**
	 * Default constructor for a regular tile
	 */
	public Free() {
		
	}
	
	public boolean hasKey() {
		if(key.equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean hasTreasure() {
		return treasure;
	}
	
	
	@Override
	public boolean canMoveHere() {
		return super.canMoveHere();
	}
	
	public String toString() {
		if(key.equals("") && treasure == false) {
			return "_";
		} else if(treasure == true) {
			return "T";
		} else {
			return key;
		}
		
	}

}
