package nz.ac.vuw.ecs.swen225.gp21.Persistency;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Acid;
import nz.ac.vuw.ecs.swen225.gp21.domain.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.Position;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Time;
import nz.ac.vuw.ecs.swen225.gp21.domain.Wall;

public class PersistencyTests {

  /**
   * Testing basic room with no objects. Ensure Game objects can be repeatedly
   * saved and loaded without error.
   */
  @Test
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

  /**
   * Testing to ensure Game objects can be repeatedly saved and loaded without
   * error.
   */
  @Test
  public void test2() {

    try {

      Tile[][] maze = new Tile[10][1];

      for (int i = 0; i < maze.length; i++) {
        maze[i][0] = new Free();
      }

      maze[0][0] = new Free(); // Chap's tile
      maze[1][0] = new Door("b");
      maze[2][0] = new Exit();
      maze[3][0] = new ExitLock();
      maze[4][0] = new Info("Some information");
      maze[5][0] = new Free("b", false);
      maze[6][0] = new Free("", true);
      maze[7][0] = new Wall();
      maze[8][0] = new Time(10);
      maze[9][0] = new Acid();

      Game original = new Game(maze, 1, 60, 0, 0);
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

  /**
   * Ensuring only files with extension .xml can be loaded.
   */
  @Test
  public void test3() {
    try {
      XMLSaveLoad.load("level1"); // Should throw an IOException.
      fail();
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Ensuring files can only be saved to an xml format.
   */
  @Test
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

      XMLSaveLoad.save(game, "level1"); // Should throw an IOException.
      fail();
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Testing loading of level 1.
   */
  @Test
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

  /**
   * Testing loading of level 2.
   */
  @Test
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

  /**
   * Testing loading and reloading Spider actor.
   */
  @Test
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

    } catch (IOException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
      fail();
    }

  }

  /**
   * Testing spider killing the player.
   */
  @Test
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

      original.getMaze()[1][1].getActor().move(original);

      assert (original.getGameOver());

    } catch (IOException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
      fail();
    }

  }

  /**
   * Ensuring spider doesn't walk out of into a wall.
   */
  @Test
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
      Game game = new Game(maze, 1, 1, 1, 3);

      Actor spider = game.getMaze()[1][1].getActor();
      String originalBoard = game.drawBoard();
      
      for(int i = 0; i < 4; i++)  {
        spider.move(game);
      }
      // Spider should stay stationary since it has no where to walk.
      assertEquals(originalBoard, game.drawBoard());

    } catch (IOException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
      fail();
    }

  }

  /**
   * Ensuring spider moves to the only free tile.
   */
  @Test
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

      Actor spider = game.getMaze()[1][1].getActor();
      String originalBoard = game.drawBoard();
      // Spider should move back and forth between the two available squares.
      spider.move(game);
      spider.move(game);
      spider.move(game);
      spider.move(game);

      // Spider should be on the square it started on.
      assertEquals(originalBoard, game.drawBoard());

    } catch (IOException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * Ensures that save files remember when the player picks up keys.
   */
  @Test
  public void test11() {

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

      maze[1][2] = new Free("b", false);
      maze[1][3] = new Door("b");
      Game game = new Game(maze, 1, 1, 1, 1);
      game.moveChap("d");

      XMLSaveLoad.save(game, "saved.xml");
      game = XMLSaveLoad.load("saved.xml");

      game.moveChap("d");

      assertEquals("W|W|W|W|W|W|\n" + "W|_|_|C|_|W|\n" + "W|W|W|W|W|W|\n", game.drawBoard());

    } catch (IllegalArgumentException | SecurityException | IOException e) {
      e.printStackTrace();
      fail();
    }

  }

}
