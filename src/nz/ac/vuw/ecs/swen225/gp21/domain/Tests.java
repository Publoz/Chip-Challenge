package nz.ac.vuw.ecs.swen225.gp21.domain;
import org.junit.jupiter.api.Test;

public class Tests {

	@Test
	void test1() {
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
		game.drawBoard();
	}
}
