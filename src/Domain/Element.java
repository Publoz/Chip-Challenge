package Domain;

public abstract class Element {

	private boolean isObstacle;
	private boolean isMoveable;
	
	public Element(boolean isObs, boolean isMov) {
		this.isObstacle = isObs;
		this.isMoveable = isMov;
	}
	
	@Override
	public String toString(){
		return super.toString();
	}
}
