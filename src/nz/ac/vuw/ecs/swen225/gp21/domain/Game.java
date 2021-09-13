package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.Arrays;

public class Game {
	
	public static final int MAX_KEYS = 8;
	
	private Tile[][] maze;
	private int level;
	private int timeLeft;
	private int treasureLeft;
	private String[] keys = new String[MAX_KEYS];
	private Position chapPos;
	
	
	/**
	 * Game constructor.
	 * 
	 * @param maze the 2d array of tiles
	 * @param levelNumber what level this is
	 * @param totalTime the total time in the level
	 * @param row the row chap is in
	 * @param col the col chap is in
	 */
	public Game(Tile[][] maze, int levelNumber, int totalTime, int row, int col) {
		this.maze = maze;
		this.level = levelNumber;
		timeLeft = totalTime;
		treasureLeft = countTreasure();
		chapPos = new Position(row, col);
		maze[chapPos.getRow()][chapPos.getCol()].addActor("Chap");
		assert(findChap().getRow() == row && findChap().getCol() == col); //check that chap loaded 
																		  //in correctly
	}
	
	/**
	 * Returns a boolean if an actor can move here.
	 * 
	 * Checks if the tile allows movement
	 * Then checks if it is a door we have the key for
	 * Otherwise return false
	 * 
	 * @param moveTo the position to move to
	 * @return a boolean if valid
	 */
	public boolean validMove(Position moveTo) {
		Tile moveToTile = maze[moveTo.getRow()][moveTo.getCol()];
		if(moveToTile.canMoveHere()) {
			return true;
		} else if(moveToTile instanceof Door){
			if(Arrays.asList(keys).contains(((Door)moveToTile).getColour())){
				return true;
			} else {
				return false;
			}
		} else if(moveToTile instanceof ExitLock){
			if(treasureLeft == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Moves chap in a direction.
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
			
			pickup(moveToTile);
			
			if(moveToTile instanceof Door) {
				removeKey(((Door)moveToTile).getColour());
				maze[moveToPos.getRow()][moveToPos.getCol()] = new Free();
			} else if(moveToTile instanceof ExitLock) {
				if(countTreasure() != 0) {
					throw new IllegalStateException("Chap cannot move onto exit lock while"
							+ " treasure is left");
				}
				maze[moveToPos.getRow()][moveToPos.getCol()] = new Free();
			} else if(moveToTile instanceof Exit) {
				if(treasureLeft != 0) {
					throw new IllegalStateException("Chap cannot move onto exit while"
							+ " treasure is left");
				}
				nextLevel();
			}
				
				
			maze[moveToPos.getRow()][moveToPos.getCol()].addActor("Chap"); //Valid move so update 
			removeChap();
			chapPos = moveToPos;
			
			assert(findChap().equals(moveToPos));
			assert(chapInValidPos());
		}
	}
	
	/**
	 * Will implement what happens when chap reaches the exit
	 */
	public void nextLevel() {
		
	}
	
	/**
	 * Checks if chap is currently standing on a valid tile.
	 * 
	 * @return a boolean if chap is on a valid tile
	 */
	public boolean chapInValidPos() {
		Tile on = maze[chapPos.getRow()][chapPos.getCol()];
		if(on instanceof Door || on instanceof Wall || on instanceof ExitLock) {
			return false;
		} else if(!on.getActor().equals("Chap")) {
			throw new IllegalStateException("Chap is on an occupied tile");
		} else {
			return true;
		}
	}
	
	/**
	 * Chap picks up items if there is one on this tile
	 * 
	 * Checks if tile has a key and if so calls addKey
	 * Then checks if the tile has treasure and if so removes it
	 * IllegalStateException is called if the treasureLeft doesn't match 
	 * the amount of treasure left
	 * 
	 * @param moveTo the tile Chap is picking up items from
	 */
	public void pickup(Tile moveTo) {
		if(moveTo.hasKey()) {
			addKey(moveTo.getKey());
			((Free)moveTo).removeKey();
		} else if(moveTo.hasTreasure()) {
			moveTo.removeTreasure();
			treasureLeft--;
			assert(treasureLeft == countTreasure());
			
		}
	}
	
	/**
	 * Adds a key to Chap's inventory.
	 * 
	 * If the key is invalid, or if the keys are full an exception is thrown.
	 * If the key is valid, add it to next free space in inventory.
	 * 
	 * @param key the key to add
	 */
	public void addKey(String key) {
		if(key == null || key.equals("")) {
			throw new IllegalArgumentException("No key to add");
		}
		
		for(int i = 0; i < MAX_KEYS; i++) {
			if(keys[i] == null) {
				keys[i] = key;
				return;
			}
		}
		throw new IllegalStateException("Keys is full");
	}
	
	/**
	 * Removes a key from inventory.
	 * 
	 * Check if we have key and remove first instance
	 * Throw IllegalArgumentException if we don't have key
	 * 
	 * @param key the key to remove
	 */
	public void removeKey(String key) {
		for(int i = 0; i < MAX_KEYS; i++) {
			if(keys[i] != null && keys[i].equals(key)) {
				keys[i] = null;
				return;
			}
		}
		throw new IllegalArgumentException("Key was not in inventory to remove");
	}
	
	/**
	 * Helper function to quickly remove chap from his current position.
	 */
	public void removeChap() {
		if(!maze[chapPos.getRow()][chapPos.getCol()].getActor().equals("Chap")){
			throw new IllegalStateException("Chap is not in his tile position");
		}
		maze[chapPos.getRow()][chapPos.getCol()].removeActor();
	}
	
	/**
	 * Finds chap's position in the maze.
	 * 
	 * @return a position for where chap is
	 */
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
	
	/**
	 * Counts how much treasure is left in the maze.
	 * 
	 * @return an int of the amount of treasure left
	 */
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
	
	/**
	 * Draws the maze on the screen, for testing purposes
	 */
	public String drawBoard() {
		String board = "";
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze.length; col++) {
				board += maze[row][col];
				board += "|";
				if (col == maze.length - 1) {
					board += "\n";
				}
			}
		}
		return board;
	}
	
	
}
