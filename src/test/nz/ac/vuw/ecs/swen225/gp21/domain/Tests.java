package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;

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

/**
 * The tests class that ensures the game logic works properly.

 * @author Paul Ireland - 300475268
 *
 */
public class Tests {

  /**
   *  We can create a game and it looks correct.
   */
  @Test
  void test1() {
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

    maze[1][1] = new Free("", true);

    Game game = new Game(maze, 1, 60, 2, 2);

    String answer = "W|W|W|W|W|\n"
                  + "W|T|_|_|W|\n"
                  + "W|_|C|_|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|W|W|W|W|\n";

    assert (game.drawBoard().equals(answer));

  }

  /**
   *  Chap can move.
   */
  @Test
  void test2() {
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

    maze[1][1] = new Free("", true);

    Game game = new Game(maze, 1, 60, 2, 2);
    game.moveChap("a");

    String answer = "W|W|W|W|W|\n"
                  + "W|T|_|_|W|\n"
                  + "W|C|_|_|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|W|W|W|W|\n";

    assert (game.drawBoard().equals(answer));

  }

  /**
   * Chap can pickup treasure.
   */
  @Test
  void test3() {
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

    maze[1][1] = new Free("", true);

    Game game = new Game(maze, 1, 60, 2, 2);
    assert (game.countTreasure() == 1);

    game.moveChap("a");
    game.moveChap("w");

    assert (game.countTreasure() == 0);

    String answer = "W|W|W|W|W|\n"
                  + "W|C|_|_|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|W|W|W|W|\n";

    assert (game.drawBoard().equals(answer));

  }

  /**
   *  Chap can open exit lock and exit level.
   */
  @Test
  void test4() {
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

    maze[0][2] = new Exit();
    maze[1][2] = new ExitLock();
    maze[1][1] = new Wall();
    maze[1][3] = new Wall();
    maze[3][2] = new Free("", true);

    Game game = new Game(maze, 1, 60, 2, 2);

    game.moveChap("s");
    game.moveChap("w");
    game.moveChap("w");
    game.moveChap("w");

    String answer = "W|W|E|W|W|\n"
                  + "W|W|_|W|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|W|W|W|W|\n";

    assert (game.drawBoard().equals(answer));
    assertEquals(true, game.getGameOver());
    assertEquals(true, game.wonGame());

  }

  /**
   *  Chap can open doors.
   */
  @Test
  void test5() {
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

    maze[0][2] = new Free("", true);
    maze[1][2] = new Door("G");
    maze[1][1] = new Wall();
    maze[1][3] = new Wall();
    maze[3][2] = new Free("G", false);

    Game game = new Game(maze, 1, 60, 2, 2);

    game.moveChap("s");
    game.moveChap("w");
    game.moveChap("w");
    game.moveChap("w");

    String answer = "W|W|C|W|W|\n"
                  + "W|W|_|W|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|W|W|W|W|\n";

    assertEquals(answer, game.drawBoard());

  }

  /**
   * Timing works.
   */
  @Test
  void test6() {
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

    maze[0][2] = new Free("", true);
    maze[1][2] = new Time(2);
    maze[1][1] = new Wall();
    maze[1][3] = new Wall();
    maze[3][2] = new Free("G", false);

    Game game = new Game(maze, 1, 60, 2, 2);
    Long start = game.getStartTime();

    while (true) {
      if (game.timeLeft() == 58) {
        assertEquals((int) ((System.currentTimeMillis() - start) / 1000), 2);
        break;
      }
    }

    game.pauseGame();
    Long pauseStart = System.currentTimeMillis();
    while (true) {
      if ((int) (System.currentTimeMillis() - pauseStart) / 1000 % 2 == 0) {
        break;
      }
    }
    assertEquals(game.timeLeft(), 58);
    game.resumeGame();
    assertEquals(game.timeLeft(), 58);
    game.moveChap("w");
    assert (game.timeLeft() > 58);

  }

  /**
   *  Acid works.
   */
  @Test
  void test7() {
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

    maze[0][2] = new Exit();
    maze[1][2] = new ExitLock();
    maze[1][1] = new Wall();
    maze[1][3] = new Wall();
    maze[3][2] = new Free("", true);
    maze[3][1] = new Acid();

    Game game = new Game(maze, 1, 60, 2, 2);

    game.moveChap("s");
    game.moveChap("a");

    String answer = "W|W|E|W|W|\n"
                  + "W|W|L|W|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|A|_|_|W|\n"
                  + "W|W|W|W|W|\n";

    assertEquals(answer, game.drawBoard());
    assertEquals(true, game.getGameOver());
    assertEquals(false, game.wonGame());

  }

  /**
   * Position class works.
   */
  @Test
  void test8() {
    Position p1 = new Position(1, 1);
    Position p2 = new Position(1, 1);
    Position p3 = new Position(2, 1);

    assertEquals(p1, p2);
    assert (!p1.equals(p3));
    assertEquals(p1.movePos("s"), p3);

  }

  /**
   *  Testing actors can be incorporated with a simple actor class
   *  and testing info tile.
   */
  @Test
  void test9() {
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

    Monster m = new Monster();

    maze[0][2] = new Exit();
    maze[1][2] = new ExitLock();
    maze[1][1] = new Wall();
    maze[1][3] = new Wall();
    maze[3][2] = new Free("", true);
    maze[3][3] = new Info("hi");
    maze[3][1].addActor(m);



    Game game = new Game(maze, 1, 60, 2, 2);

    game.moveChap("s");
    game.moveChap("d");
    Position c = game.getChap();
    assert(game.getMaze()[c.getRow()][c.getCol()] instanceof Info);
    assertEquals("hi", ((Info)game.getMaze()[c.getRow()][c.getCol()]).getInformation());
    game.moveChap("a");
    game.moveChap("a");

    String answer = "W|W|E|W|W|\n"
                  + "W|W|L|W|W|\n"
                  + "W|_|_|_|W|\n"
                  + "W|M|_|I|W|\n"
                  + "W|W|W|W|W|\n";

    assertEquals(answer, game.drawBoard());
    assertEquals(true, game.getGameOver());
    assertEquals(false, game.wonGame());

  }


  /**
   * Simple monster actor class for testing.

   * @author irelanpaul
   */
  class Monster implements Actor{

    @Override
    public void move(Game game) {
      return;

    }

    @Override
    public Position getPos() {
      return null;
    }

    @Override
    public void setPos(Position pos) {
    }

    @Override
    public BufferedImage getImage() {
      return null;
    }

    @Override
    public boolean isDeadly() {
      return true;
    }

    @Override
    public String toString() {
      return "M";
    }

  }

}
