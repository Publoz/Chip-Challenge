package nz.ac.vuw.ecs.swen225.gp21.domain;

public class ExitLock extends AbstractTile{

	@Override
	public boolean canMoveHere() {
		return false;
	}
	
	@Override
	public String toString() {
		return "L";
	}

}
