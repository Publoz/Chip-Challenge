package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Exit extends AbstractTile{

	@Override
	public boolean canMoveHere() {
		return true;
	}
	
	@Override
	public String toString() {
		return "E";
	}

}
