package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Game {
	
	private Tile[][] maze;
	private int level;
	private int timeLeft;
	private int treasureLeft;
	private String[] keys = new String[8];
	private Position chapPos;
	
	
	public Game(Tile[][] maze, int levelNumber, int totalTime) {
		this.maze = maze;
		this.level = levelNumber;
		timeLeft = totalTime;
		treasureLeft = countTreasure();
		chapPos = findChap();
	}
	
	public Position findChap() {
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[0].length; j++) {
				if(maze[i][j].getActor().equals("Chap")) {
					return new Position(i, j);
				}
			}
		}
		throw new IllegalStateException("Cannot find chap");
	}
	
	public int countTreasure() {
		int count = 0;
		for(Tile[] tiles: maze) {
			for(Tile tile: tiles) {
				if(tile.hasTreasure()) {
					count++;
				}
			}
		}
		return count;
	}
	
	
}
