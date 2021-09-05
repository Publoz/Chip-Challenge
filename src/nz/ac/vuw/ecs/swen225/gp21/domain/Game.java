package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Game {
	
	private Tile[][] mazeElements;
	private int level;
	
	
	public Game(Tile[][] elements, int levelNumber) {
		this.mazeElements = elements;
		this.level = levelNumber;
	}
	
	
}
