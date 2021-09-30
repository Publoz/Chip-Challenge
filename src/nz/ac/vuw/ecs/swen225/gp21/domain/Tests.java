package nz.ac.vuw.ecs.swen225.gp21.domain;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Tests {

	@Test
	void test1() { //We can create a game and it looks correct
		Tile[][] maze = new Tile[5][5];
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(row == 0 || col == 0 || row == maze.length-1 || col == maze.length-1) {
					maze[row][col] = new Wall();
				} else {
					maze[row][col] = new Free();
				}
			}
		}
		
		maze[1][1] = new Free("", true);
		
		Game game = new Game(maze, 1, 60, 2, 2);
		
		
		String answer = "W|W|W|W|W|\n"+
						"W|T|_|_|W|\n"+
						"W|_|C|_|W|\n"+
						"W|_|_|_|W|\n"+
						"W|W|W|W|W|\n";
		
		assert(game.drawBoard().equals(answer));

	}
	
	@Test
	void test2() { //Chap can move
		Tile[][] maze = new Tile[5][5];
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(row == 0 || col == 0 || row == maze.length-1 || col == maze.length-1) {
					maze[row][col] = new Wall();
				} else {
					maze[row][col] = new Free();
				}
			}
		}
		
		maze[1][1] = new Free("", true);
		
		Game game = new Game(maze, 1, 60, 2, 2);
		game.moveChap("a");
		
		
		String answer = "W|W|W|W|W|\n"+
						"W|T|_|_|W|\n"+
						"W|C|_|_|W|\n"+
						"W|_|_|_|W|\n"+
						"W|W|W|W|W|\n";
		
		assert(game.drawBoard().equals(answer));

	}
	
	@Test
	void test3() { //Chap can pickup treasure
		Tile[][] maze = new Tile[5][5];
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(row == 0 || col == 0 || row == maze.length-1 || col == maze.length-1) {
					maze[row][col] = new Wall();
				} else {
					maze[row][col] = new Free();
				}
			}
		}
		
		maze[1][1] = new Free("", true);
		
		Game game = new Game(maze, 1, 60, 2, 2);
		assert(game.countTreasure() == 1);
		
		game.moveChap("a");
		game.moveChap("w");
		
		assert(game.countTreasure() == 0);
		
		
		String answer = "W|W|W|W|W|\n"+
						"W|C|_|_|W|\n"+
						"W|_|_|_|W|\n"+
						"W|_|_|_|W|\n"+
						"W|W|W|W|W|\n";
		
		assert(game.drawBoard().equals(answer));

	}
	
	@Test
	void test4() { //Chap can open exit lock and exit level
		Tile[][] maze = new Tile[5][5];
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(row == 0 || col == 0 || row == maze.length-1 || col == maze.length-1) {
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
		
		String answer = "W|W|E|W|W|\n"+
						"W|W|_|W|W|\n"+
						"W|_|_|_|W|\n"+
						"W|_|_|_|W|\n"+
						"W|W|W|W|W|\n";
		
		assert(game.drawBoard().equals(answer));
		assertEquals(true, game.getGameOver());
		assertEquals(true, game.wonGame());

	}
	
	
	@Test
	void test5() { //Chap can open doors
		Tile[][] maze = new Tile[5][5];
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(row == 0 || col == 0 || row == maze.length-1 || col == maze.length-1) {
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
		
		String answer = "W|W|C|W|W|\n"+
						"W|W|_|W|W|\n"+
						"W|_|_|_|W|\n"+
						"W|_|_|_|W|\n"+
						"W|W|W|W|W|\n";
		
		assertEquals(answer, game.drawBoard());

	}
	
	@Test
	void test6() { //Timing works
		Tile[][] maze = new Tile[5][5];
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(row == 0 || col == 0 || row == maze.length-1 || col == maze.length-1) {
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
		
		while(true) {
			if(game.timeLeft() == 58) {
				assertEquals((int) ((System.currentTimeMillis() - start) / 1000), 2);
				break;
			}
		}
		
		game.pauseGame();
		Long pauseStart = System.currentTimeMillis();
		while(true) {
			if((int)(System.currentTimeMillis() - pauseStart )/1000 % 2 == 0) {
				break;
			}
		}
		assertEquals(game.timeLeft(), 58);
		game.resumeGame();
		assertEquals(game.timeLeft(), 58);
		game.moveChap("w");
		assert(game.timeLeft() > 58);

	}
	
	@Test
	void test7() { //Acid works
		Tile[][] maze = new Tile[5][5];
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(row == 0 || col == 0 || row == maze.length-1 || col == maze.length-1) {
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
		//game.moveChap("w");
	//	game.moveChap("w");
		//game.moveChap("w");
		
		String answer = "W|W|E|W|W|\n"+
						"W|W|L|W|W|\n"+
						"W|_|_|_|W|\n"+
						"W|A|_|_|W|\n"+
						"W|W|W|W|W|\n";
		
		assertEquals(answer, game.drawBoard());
		assertEquals(true, game.getGameOver());
		assertEquals(false, game.wonGame());

	}
	
	@Test
	void test8() { //Position class works
		Position p1 = new Position(1,1);
		Position p2 = new Position(1,1);
		Position p3 = new Position(2,1);
		
		assertEquals(p1, p2);
		assert(!p1.equals(p3));
		assertEquals(p1.movePos("s"), p3);
		
	}
	
	
	
}
