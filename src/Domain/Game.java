package Domain;

public class Game {
	
	private Element[] mazeElements;
	private int level;
	private Position chipPosition;
	
	
	public Game(Element[] elements, int levelNumber,  Position chipPos) {
		this.mazeElements = elements;
		this.level = levelNumber;
		this.chipPosition = chipPos;
	}
	
	
}
