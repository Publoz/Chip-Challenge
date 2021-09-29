package nz.ac.vuw.ecs.swen225.gp21.Persistency;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.Position;
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

			assertEquals(originalString, loadedString);
		} catch (IOException e) {
			e.printStackTrace();
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

			maze[1][1] = new Door("b");
			maze[2][1] = new Exit();
			maze[3][1] = new ExitLock();
			maze[4][1] = new Info("Some information");
			maze[5][1] = new Free("b", false);
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
			assertEquals(originalString, loadedString);

		} catch (IOException e) {
			e.printStackTrace();
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
		} catch (IOException e) {
		}
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
		} catch (IOException e) {
		}
	}

	@Test
	/**
	 * Testing loading of level 1.
	 */
	public void test5() {

		try {
			Game toLoad = XMLSaveLoad.load("level1.xml");
			String originalString = toLoad.drawBoard();

			XMLSaveLoad.save(toLoad, "saved.xml");
			toLoad = XMLSaveLoad.load("saved.xml");
			String finalString = toLoad.drawBoard();

			assertEquals(originalString, finalString);

		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	/**
	 * Testing loading of level 2.
	 */
	public void test6() {

		try {
			Game toLoad = XMLSaveLoad.load("level2.xml");
			String originalString = toLoad.drawBoard();
			XMLSaveLoad.save(toLoad, "saved.xml");
			toLoad = XMLSaveLoad.load("saved.xml");
			String finalString = toLoad.drawBoard();
			assertEquals(originalString, finalString);

		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	/**
	 * Testing loading and reloading Spider actor.
	 */
	public void test7() {

		try {
			Class<?> spiderClass = XMLSaveLoad.loadClass();
			Actor spiderObject = (Actor) spiderClass.getDeclaredConstructor(Position.class)
					.newInstance(new Position(0, 0));
			Tile[][] maze = new Tile[1][2];
			Free toAdd = new Free();
			toAdd.addActor(spiderObject);
			maze[0][0] = (toAdd);
			maze[0][1] = new Free();

			Game original = new Game(maze, 1, 1, 0, 1);

			XMLSaveLoad.save(original, "saved.xml");
			Game loaded = XMLSaveLoad.load("saved.xml");

			assertEquals(original.drawBoard(), loaded.drawBoard());

		} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	/**
	 * Testing spider killing the player.
	 */
	public void test8() {

		try {

			Class<?> spiderClass = XMLSaveLoad.loadClass();
			Actor spiderObject = (Actor) spiderClass.getDeclaredConstructor(Position.class)
					.newInstance(new Position(1, 1));
			Tile[][] maze = new Tile[3][4];

			for (int row = 0; row < maze.length; row++) {
				for (int col = 0; col < maze[0].length; col++) {
					if (row == 0 || col == 0 || row == maze.length - 1 || col == maze[0].length - 1) {
						maze[row][col] = new Wall();
					} else {
						maze[row][col] = new Free();
					}
				}
			}

			Free toAdd = new Free();
			toAdd.addActor(spiderObject);
			maze[1][1] = (toAdd);

			Game original = new Game(maze, 1, 1, 1, 2);
			System.out.println(original.drawBoard());
			
			original.getMaze()[1][1].getActor().move(original);
			
			assert(original.getGameOver());
			
		} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	@Test
	/**
	 * Ensuring spider doesn't walk out of into a wall.
	 */
	public void test9() {

		try {

			Class<?> spiderClass = XMLSaveLoad.loadClass();
			Actor spiderObject = (Actor) spiderClass.getDeclaredConstructor(Position.class)
					.newInstance(new Position(0, 0));
			Tile[][] maze = new Tile[3][5];

			for (int row = 0; row < maze.length; row++) {
				for (int col = 0; col < maze[0].length; col++) {
					if (row == 0 || col == 0 || row == maze.length - 1 || col == maze[0].length - 1) {
						maze[row][col] = new Wall();
					} else {
						maze[row][col] = new Free();
					}
				}
			}

			Free toAdd = new Free();
			toAdd.addActor(spiderObject);
			maze[1][1] = (toAdd);
			maze[1][2] = new Wall();
			Game original = new Game(maze, 1, 1, 1, 3);

			//Move spider in random direction. The spider should not move since it is
			//surrounded by walls. 
			
			boolean thrown = false;
			
			try {
				original.getMaze()[1][1].getActor().move(original);
			} catch(IllegalStateException e) {
				thrown = true;
			}
			if(!thrown) {
				fail(); //No valid moves. It should have thrown an IllegalStateException.
			}
				
		} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	@Test
	/**
	 * Ensuring spider moves to the only free tile.
	 */
	public void test10() {

		try {

			
			Tile[][] maze = new Tile[3][6];

			for (int row = 0; row < maze.length; row++) {
				for (int col = 0; col < maze[0].length; col++) {
					if (row == 0 || col == 0 || row == maze.length - 1 || col == maze[0].length - 1) {
						maze[row][col] = new Wall();
					} else {
						maze[row][col] = new Free();
					}
				}
			}

			Class<?> spiderClass = XMLSaveLoad.loadClass();
			Actor spiderObject = (Actor) spiderClass.getDeclaredConstructor(Position.class)
					.newInstance(new Position(1, 1));
			
	
			maze[1][1] = new Free();
			maze[1][1].addActor(spiderObject);
			maze[1][3] = new Wall();
			Game game = new Game(maze, 1, 1, 1, 4);
			String originalBoard = game.drawBoard();
			Actor spider = game.getMaze()[1][1].getActor();
			spider.move(game);
			System.out.println(game.drawBoard());
			spider.move(game);
			System.out.println(game.drawBoard());
			spider.move(game);
			System.out.println(game.drawBoard());
			spider.move(game);
			System.out.println(game.drawBoard());
			//Move spider in random direction. The spider should not move since it is
			//surrounded by walls. 

			assertEquals(originalBoard, game.drawBoard());
	
		} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	
	

}
