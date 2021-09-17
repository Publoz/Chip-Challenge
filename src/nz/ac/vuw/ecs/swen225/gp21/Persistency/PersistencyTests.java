package nz.ac.vuw.ecs.swen225.gp21.Persistency;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Wall;

public class PersistencyTests {
	
	@Test
	/**
	 * Testing to ensure Game objects can be repeatedly saved and loaded without error.
	 */
	public void test1() {
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
		

		Game original = new Game(maze, 1, 60, 2, 2);
		String originalString = original.drawBoard();
		
		XMLSaveLoad.save(original, "saved.xml");
		Game loaded = XMLSaveLoad.load("saved.xml");
		System.out.println(originalString);
		
		for(int i = 0; i < 5; i++) {
			loaded = XMLSaveLoad.load("saved.xml");
			XMLSaveLoad.save(loaded, "saved.xml");
		}

		String loadedString = loaded.drawBoard();
		System.out.println(loadedString);
		assert(originalString.equals(loadedString));
	}

}
