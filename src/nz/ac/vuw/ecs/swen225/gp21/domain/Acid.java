package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Acid extends AbstractTile{
	
	@Override
	public void addActor(Actor actor) {
		throw new IllegalStateException("Cannot add actor to acid");
	}
	
	@Override
	public String toString() {
		return "A";
	}
}
