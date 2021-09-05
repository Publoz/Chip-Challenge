package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Game {
	
	private Tile[][] maze;
	private int level;
	private int timeLeft;
	private int treasureLeft;
	private String[] keys = new String[8];
	private Position chapPos;
	
	
	public Game(Tile[][] maze, int levelNumber, int totalTime, int row, int col) {
		this.maze = maze;
		this.level = levelNumber;
		timeLeft = totalTime;
		treasureLeft = countTreasure();
		chapPos = new Position(row, col);
		maze[chapPos.getRow()][chapPos.getCol()].addActor("Chap");
		assert(findChap().getRow() == row && findChap().getCol() == col); //check that chap loaded in correctly
	}
	
	/**
	 * Returns if an actor can move here
	 * 
	 * @param moveTo the position to move to
	 * @return a boolean if valid
	 */
	public boolean validMove(Position moveTo) {
		if(maze[moveTo.getRow()][moveTo.getCol()].canMoveHere()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Moves chap in a direction
	 * 
	 * Checks if move is valid and then updates the tiles
	 * 
	 * @param dir the direction to move (w, a, s, d)
	 */
	public void moveChap(String dir) {
		if(validMove(chapPos.movePos(dir))) {
			Position moveToPos = chapPos.movePos(dir);
			Tile moveToTile = maze[moveToPos.getRow()][moveToPos.getCol()];
			if(!moveToTile.getActor().equals("")) {
				throw new IllegalArgumentException("Cannot move onto occupied tile");
			}
			
			moveToTile.addActor("Chap");
			removeChap();
			chapPos = moveToPos;
			
			assert(findChap().equals(moveToPos));
		}
	}
	
	/**
	 * Helper function to quickly remove chap from his current position
	 */
	public void removeChap() {
		if(!maze[chapPos.getRow()][chapPos.getCol()].getActor().equals("Chap")){
			throw new IllegalStateException("Chap is not in his tile position");
		}
		maze[chapPos.getRow()][chapPos.getCol()].removeActor();
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
