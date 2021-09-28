package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
	
	public static final int MAX_KEYS = 8;
	
	
	private Tile[][] maze;
	private int level;
	private int totalTime;
	private long startTime;
	private long totalPauseTime = -1;
	private long startPauseTime;
	private boolean paused = false;
	private int treasureLeft;
	private String[] keys = new String[MAX_KEYS];
	private ArrayList<Actor> actors = new ArrayList<Actor>();

	//private Position chapPos;
	private Chap chap;
	private boolean gameOver = false;
	
	
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
		this.totalTime = totalTime;
		startTime = System.currentTimeMillis();
		treasureLeft = countTreasure();
		chap = new Chap(new Position(row, col));
		//chapPos = new Position(row, col);
		maze[chap.getPos().getRow()][chap.getPos().getCol()].addActor(chap);
		assert(findChap().getRow() == row && findChap().getCol() == col); //check that chap loaded 
																		  //in correctly
		loadActors();
	}
	
	/**
	 * Loads the actors into an arraylist at the start of the game.
	 * 
	 * Skips Chap
	 */
	public void loadActors() {
		for(int row = 0; row < maze.length; row++) {
			for(int col = 0; col < maze[0].length; col++) {
				if(maze[row][col].getActor() != null 
						&& !(maze[row][col].getActor().toString().equals("Chap"))){
					actors.add(maze[row][col].getActor());
				}
			}
		}
		assert(!actors.contains(chap));
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
		
		if(paused || gameOver) {
			return false;
		}
		
		Tile moveToTile = maze[moveTo.getRow()][moveTo.getCol()];
		if(moveToTile.canMoveHere()) {
			return true;
		} else if(moveToTile instanceof Door){
			if(Arrays.asList(keys).contains(((Door)moveToTile).getColour().toLowerCase())){
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
		if(validMove(chap.getPos().movePos(dir))) {
			Position moveToPos = chap.getPos().movePos(dir);
			Tile moveToTile = maze[moveToPos.getRow()][moveToPos.getCol()];
			if(!(moveToTile.getActor() == null)) {
				throw new IllegalArgumentException("Cannot move onto occupied tile");
			}
			
			pickup(moveToTile);
			
			if(moveToTile instanceof Door) {
				removeKey(((Door)moveToTile).getColour().toLowerCase());
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
				gameOver = true;
			}
				
				
			maze[moveToPos.getRow()][moveToPos.getCol()].addActor(chap); //Valid move so update 
			removeChap();
			chap.setPos(moveToPos);
			
			assert(findChap().equals(moveToPos));
			assert(chapInValidPos());
		}
	}
	
	/**
	 * Moves all the actors based on their move.
	 */
	public void updateActors() {
		for(Actor act: actors) {
			act.move(this);
		}
	}
	
	
	
	/**
	 * Checks if chap is currently standing on a valid tile.
	 * 
	 * @return a boolean if chap is on a valid tile
	 */
	public boolean chapInValidPos() {
		Tile on = maze[chap.getPos().getRow()][chap.getPos().getCol()];
		if(on instanceof Door || on instanceof Wall || on instanceof ExitLock) {
			return false;
		} else if(!on.getActor().toString().equals("Chap")) {
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
		if(!maze[chap.getPos().getRow()][chap.getPos().getCol()].getActor().toString().equals("Chap")){
			throw new IllegalStateException("Chap is not in his tile position");
		}
		maze[chap.getPos().getRow()][chap.getPos().getCol()].removeActor();
	}
	
	/**
	 * Finds chap's position in the maze.
	 * 
	 * @return a position for where chap is
	 */
	public Position findChap() {
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[0].length; j++) {
				if(maze[i][j].getActor() == chap) {
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
			for (int col = 0; col < maze[0].length; col++) {
				board += maze[row][col];
				board += "|";
				if (col == maze[0].length - 1) {
					board += "\n";
				}
			}
		}
		return board;
	}
	
	/**
	 * Method to pause the game and stop the clock from going down
	 */
	public void pauseGame() {
		startPauseTime = System.currentTimeMillis();
		paused = true;
	}
	
	/**
	 * Called when the user wants to resume game
	 * updates the time paused
	 */
	public void resumeGame() {
		if(startPauseTime < 0) {
			throw new IllegalStateException("Pause was not initiated");
		}
		totalPauseTime += System.currentTimeMillis() - startPauseTime;
		startPauseTime = -1;
		paused = false;
	}
	
	/**
	 * Gets the maze for this level.
	 */
	public Tile[][] getMaze(){
		return maze;
	}
	
	/**
	 * Returns the time left in seconds.
	 * Takes into account time paused
	 * @return an int of the seconds left
	 */
	public int timeLeft() {
		if(paused) {
			int secondsBeen = (int) ((System.currentTimeMillis() - startTime) / 1000);
			int pause = (int) (totalPauseTime / 1000);
			//need to account that current pause hasn't updated
			int currentPause = (int) ((System.currentTimeMillis() - startPauseTime) / 1000); 
			return ((totalTime - secondsBeen)) + (pause + currentPause);				
		} else {
			int secondsBeen = (int) ((System.currentTimeMillis() - startTime) / 1000);
			int pause = (int) (totalPauseTime / 1000);
			return (totalTime - secondsBeen) + pause;
		}
	}
	
	/**
	 * Get chaps current position.
	 * @return the position of chap
	 */
	public Position getChap() {
		return chap.getPos();
	}

	public int getLevel() {
		return level;
	}
	
	
	public long getStartTime() {
		return startTime;
	}

	public boolean isPaused() {
		return paused;
	}

	/**
	 * Gets the gameOver boolean field.
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	
	public String[] getKeys() {
		return keys;
	}

	
}
