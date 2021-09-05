package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Wall extends AbstractTile{

	@Override
	public boolean canMoveHere() {
		return false;
	}
	
	@Override
	public String toString() {
		return "W";
	}

}
