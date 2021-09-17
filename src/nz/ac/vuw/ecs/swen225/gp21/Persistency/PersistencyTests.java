package nz.ac.vuw.ecs.swen225.gp21.Persistency;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Wall;

public class PersistencyTests {

	@Test
	/**
	 * Testing basic room with no objects. Ensure Game objects can be repeatedly
	 * saved and loaded without error.
	 */
	public void test1() {
		
		try {
			Tile[][] maze = new Tile[5][5];
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (row == 0 || col == 0 || row == maze.length - 1 || col == maze.length - 1) {
						maze[row][col] = new Wall();
					} else {
						maze[row][col] = new Free();
					}
				}
			}

			Game original = new Game(maze, 1, 60, 2, 2);
			String originalString = original.drawBoard();

			XMLSaveLoad.save(original, "saved.xml");
			Game loaded = XMLSaveLoad.load("saved.xml");
			

			for (int i = 0; i < 5; i++) {
				loaded = XMLSaveLoad.load("saved.xml");
				XMLSaveLoad.save(loaded, "saved.xml");
			}

			String loadedString = loaded.drawBoard();
			
			assert (originalString.equals(loadedString));
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	/**
	 * Testing to ensure Game objects can be repeatedly saved and loaded without
	 * error.
	 */
	public void test2() {

		try {
			Tile[][] maze = new Tile[7][7];
			for (int row = 0; row < 7; row++) {
				for (int col = 0; col < 7; col++) {
					if (row == 0 || col == 0 || row == maze.length - 1 || col == maze.length - 1) {
						maze[row][col] = new Wall();
					} else {
						maze[row][col] = new Free();
					}
				}
			}

			maze[1][1] = new Door("#");
			maze[2][1] = new Exit();
			maze[3][1] = new ExitLock();
			maze[4][1] = new Info("Some information");
			maze[5][1] = new Free("#", false);
			maze[6][1] = new Free("", true);

			Game original = new Game(maze, 1, 60, 2, 2);
			String originalString = original.drawBoard();

			XMLSaveLoad.save(original, "saved.xml");
			Game loaded = XMLSaveLoad.load("saved.xml");

			for (int i = 0; i < 5; i++) {
				loaded = XMLSaveLoad.load("saved.xml");
				XMLSaveLoad.save(loaded, "saved.xml");
			}

			String loadedString = loaded.drawBoard();
			assert (originalString.equals(loadedString));

		} catch (IOException e) {
			fail();
		}
	}
	
	@Test
	/**
	 * Ensuring only files with extension .xml can be loaded.
	 */
	public void test3() {
		try {
			XMLSaveLoad.load("level1");
			fail();
		} catch(IOException e) {}
	}
	
	@Test
	/**
	 * Ensuring files can only be saved to an xml format.
	 */
	public void test4() {
		try {
			Tile[][] maze = new Tile[5][5];
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (row == 0 || col == 0 || row == maze.length - 1 || col == maze.length - 1) {
						maze[row][col] = new Wall();
					} else {
						maze[row][col] = new Free();
					}
				}
			}

			Game game = new Game(maze, 1, 60, 2, 2);
			
			XMLSaveLoad.save(game, "level1");
			fail();
		} catch(IOException e) {}
	}
	
	@Test
	/**
	 * Testing loading of level 1.
	 */
	public void test5() {
		
		try {
			Game toLoad = XMLSaveLoad.load("level1.xml");
			String original = toLoad.drawBoard();
			System.out.println(original);
			XMLSaveLoad.save(toLoad, "saved.xml");
			toLoad = XMLSaveLoad.load("saved.xml");
			String finalString = toLoad.drawBoard();
			System.out.println(finalString);
			assert(original.equals(finalString));

			
		} catch (IOException e) {
			fail();
		}
	}
}
