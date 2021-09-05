package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Info extends AbstractTile{

	private String information;
	
	public Info(String info) {
		this.information = info;
	}
	
	
	@Override
	public boolean canMoveHere() {
		return super.canMoveHere();
	}
	
	@Override 
	public String toString() {
		return "I";
	}

}
