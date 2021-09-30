package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Game class is the core of handling the game's logic.
 * Its main field is the maze which Chap navigates and is able
 * to move through with the move methods - moveChap() etc.
 * It also contains important getters that other modules
 * require, such as the getMaze, timeLeft...

 * @author Paul Ireland - 300475268
 *
 */
public class Game {

  /**
   * The number of keys Chap can hold.
   */
  public static final int MAX_KEYS = 4;

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
  private String lastMoveDir = "s";

  // private Position chapPos;
  private Chap chap;
  private boolean gameOver = false;
  private boolean win;

  /**
   * Game constructor.

   * @param maze        the 2d array of tiles
   * @param levelNumber what level this is
   * @param totalTime   the total time in the level
   * @param row         the row chap is in
   * @param col         the col chap is in
   */
  public Game(Tile[][] maze, int levelNumber, int totalTime, int row, int col) {
    this.maze = maze;
    this.level = levelNumber;
    this.totalTime = totalTime;
    startTime = System.currentTimeMillis();
    treasureLeft = countTreasure();
    chap = new Chap(new Position(row, col));
    maze[chap.getPos().getRow()][chap.getPos().getCol()].addActor(chap);
    assert (findChap().getRow() == row && findChap().getCol() == col);
    //check that chap loaded right

    loadActors();
  }

  /**
   * Loads the actors into an arraylist at the start of the game.
   * Skips Chap
   */
  public void loadActors() {
    if (!actors.isEmpty()) {
      throw new IllegalStateException("Can't load in more actors");
    }

    for (int row = 0; row < maze.length; row++) {
      for (int col = 0; col < maze[0].length; col++) {
        if (maze[row][col].getActor() != null
            && !(maze[row][col].getActor().toString().equals("Chap"))) {
          actors.add(maze[row][col].getActor());
        }
      }
    }
    assert (!actors.contains(chap));
  }

  /**
   * Returns a boolean if Chap can move here.
   *
   * <p>Checks if the tile allows movement Then checks if it is a door we have the
   * key for Otherwise return false
   * Also assumes that Chap has moved one tile away

   * @param moveTo the position to move to
   * @return a boolean if valid
   */
  public boolean validMove(Position moveTo) {

    if (paused || gameOver) {
      return false;
    }

    Tile moveToTile = maze[moveTo.getRow()][moveTo.getCol()];
    if (moveToTile.canMoveHere()) {
      return true;
    } else if (moveToTile instanceof Door) {
      if (Arrays.asList(keys).contains(((Door) moveToTile).getColour().toLowerCase())) {
        return true;
      } else {
        return false;
      }
    } else if (moveToTile instanceof ExitLock) {
      if (treasureLeft == 0) {
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
   * <p>Checks if move is valid and then updates the tiles
   * Performs special actions on special tiles

   * @param dir the direction to move (w, a, s, d)
   */
  public void moveChap(String dir) {
    if (validMove(chap.getPos().movePos(dir))) {
      lastMoveDir = dir;
      Position moveToPos = chap.getPos().movePos(dir);
      Tile moveToTile = maze[moveToPos.getRow()][moveToPos.getCol()];
      if (!(moveToTile.getActor() == null)) {
        if (!moveToTile.getActor().isDeadly()) {
          throw new IllegalArgumentException("Cannot move onto occupied tile");
        } else if (moveToTile.getActor().isDeadly()) {
          gameOver = true;
          win = false;
          removeChap();
          return;
        }
      }

      pickup(moveToTile);

      //Moving onto special tiles, need to perform special actions
      if (moveToTile instanceof Door) {
        removeKey(((Door) moveToTile).getColour().toLowerCase());
        maze[moveToPos.getRow()][moveToPos.getCol()] = new Free();
      } else if (moveToTile instanceof ExitLock) {
        if (countTreasure() != 0) {
          throw new IllegalStateException(
              "Chap cannot move onto exit lock while" + " treasure is left");
        }
        maze[moveToPos.getRow()][moveToPos.getCol()] = new Free();
      } else if (moveToTile instanceof Exit) {
        if (treasureLeft != 0) {
          throw new IllegalStateException("Chap cannot move onto exit while" + " treasure is left");
        }
        gameOver = true;
        win = true;
        removeChap();
        return;
      } else if (moveToTile instanceof Acid) {
        gameOver = true;
        win = false;
        removeChap();
        return;
      } else if (moveToTile instanceof Time) {
        totalPauseTime += ((Time) moveToTile).getSeconds() * 1000.0;
        maze[moveToPos.getRow()][moveToPos.getCol()] = new Free();
      }

      maze[moveToPos.getRow()][moveToPos.getCol()].addActor(chap); // Valid move so update
      removeChap();
      chap.setPos(moveToPos);

      assert (findChap().equals(moveToPos));
      assert (chapInValidPos());
    }
  }

  /**
   * Moves all the actors based on their move.
   */
  public void updateActors() {
    if(paused) {
      throw new IllegalStateException("Cannot move actors while paused");
    }
    for (Actor act : actors) {
      act.move(this);
    }
  }

  /**
   * Checks if chap is currently standing on a valid tile.

   * @return a boolean if chap is on a valid tile
   */
  public boolean chapInValidPos() {
    Tile on = maze[chap.getPos().getRow()][chap.getPos().getCol()];
    if (on instanceof Door || on instanceof Wall || on instanceof ExitLock) {
      return false;
    } else if (!on.getActor().toString().equals("Chap")) {
      throw new IllegalStateException("Chap is on an occupied tile");
    } else {
      return true;
    }
  }

  /**
   * Chap picks up items if there is one on this tile.
   *
   * <p>Checks if tile has a key and if so calls addKey Then checks if the tile has
   * treasure and if so removes it IllegalStateException is called if the
   * treasureLeft doesn't match the amount of treasure left

   * @param moveTo the tile Chap is picking up items from
   */
  public void pickup(Tile moveTo) {
    if (moveTo.hasKey()) {
      addKey(moveTo.getKey());
      ((Free) moveTo).removeKey();
    } else if (moveTo.hasTreasure()) {
      moveTo.removeTreasure();
      treasureLeft--;
      assert (treasureLeft == countTreasure());

    }
  }

  /**
   * Adds a key to Chap's inventory.
   *
   * <p>If the key is invalid, or if the keys are full an exception is thrown. If the
   * key is valid, add it to next free space in inventory.

   * @param key the key to add
   */
  public void addKey(String key) {
    if (key == null || key.equals("")) {
      throw new IllegalArgumentException("No key to add");
    }

    for (int i = 0; i < MAX_KEYS; i++) {
      if (keys[i] == null) {
        keys[i] = key;
        return;
      }
    }
    throw new IllegalStateException("Keys is full");
  }

  /**
   * Removes a key from inventory.
   *
   * <p>Check if we have key and remove first instance Throw IllegalArgumentException
   * if we don't have key

   * @param key the key to remove
   */
  public void removeKey(String key) {
    for (int i = 0; i < MAX_KEYS; i++) {
      if (keys[i] != null && keys[i].equals(key)) {
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
    if (!maze[chap.getPos().getRow()][chap.getPos().getCol()].getActor().toString()
        .equals("Chap")) {
      throw new IllegalStateException("Chap is not in his tile position");
    }
    maze[chap.getPos().getRow()][chap.getPos().getCol()].removeActor();
  }

  /**
   * Finds chap's position in the maze.

   * @return a position for where chap is
   */
  public Position findChap() {
    if (gameOver) {
      throw new IllegalStateException("Gameover don't need to find chap");
    }

    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[0].length; j++) {
        if (maze[i][j].getActor() == chap) {
          return new Position(i, j);
        }
      }
    }
    throw new IllegalStateException("Cannot find chap");
  }

  /**
   * Counts how much treasure is left in the maze.

   * @return an integer of the amount of treasure left
   */
  public int countTreasure() {
    int count = 0;
    for (Tile[] tiles : maze) {
      for (Tile tile : tiles) {
        if (tile.hasTreasure()) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Draws the maze on the screen, for testing purposes.

   * @return a String representing the board
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
   * Method to pause the game and stop the clock from going down.
   */
  public void pauseGame() {
    startPauseTime = System.currentTimeMillis();
    paused = true;
  }

  /**
   * Called when the user wants to resume game updates the time paused.
   */
  public void resumeGame() {
    if (!paused) {
      throw new IllegalStateException("Pause was not initiated");
    }
    totalPauseTime += System.currentTimeMillis() - startPauseTime;
    startPauseTime = -1;
    paused = false;
  }

  /**
   * Gets the maze for this level.

   * @return A 2d array of the tiles in the maze
   */
  public Tile[][] getMaze() {
    return maze;
  }

  /**
   * Returns the time left in seconds. Takes into account time paused

   * @return an int of the seconds left
   */
  public int timeLeft() {
    if (paused) {
      int secondsBeen = (int) ((System.currentTimeMillis() - startTime) / 1000);
      int pause = (int) (totalPauseTime / 1000);
      // need to account that current pause hasn't updated
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

  /**
   * Gets the level number.

   * @return the level number
   */
  public int getLevel() {
    return level;
  }

  /**
   * Get when the program started.

   * @return the time when the program started in millis.
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * Gets whether the game is currently paused.

   * @return a boolean if we are paused
   */
  public boolean isPaused() {
    return paused;
  }

  /**
   * Gets the gameOver boolean field.

   * @return a boolean if the game is over
   */
  public boolean getGameOver() {
    return gameOver;
  }

  /**
   * Gets the win field which indicated if the user won.

   * @return the boolean value of whether the level was won
   */
  public boolean wonGame() {
    return win;
  }

  /**
   * Sets the gameOver field and the win field.

   * @param gameOver the value of the gameOver field now
   * @param win      the value of the win field
   */
  public void setGameOver(boolean gameOver, boolean win) {
    this.gameOver = gameOver;
    this.win = win;
  }

  /**
   * Returns the last direction chap moved.

   * @return the String representing his last direction moved
   */
  public String getLastMoveDir() {
    return lastMoveDir;
  }

  /**
   * Get the array of keys chap holds.

   * @return an array of string representing keys
   */
  public String[] getKeys() {
    return keys;
  }

}
