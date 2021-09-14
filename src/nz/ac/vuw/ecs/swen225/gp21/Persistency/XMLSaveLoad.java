package nz.ac.vuw.ecs.swen225.gp21.Persistency;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

import nz.ac.vuw.ecs.swen225.gp21.domain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;



public class XMLSaveLoad {	

	/**
	 * Opens an XML file, converts it to a new Game object, and returns it. 
	 * 
	 * With help from YouTube:
	 * https://www.youtube.com/watch?v=H-aTpt4NG-s
	 * @param level  Identifies which level to be loaded by the load method. 
	 * @return	loaded level. 
	 */
	
	public static Game load(int level) {
		try {
			FileInputStream fis = new FileInputStream(new File("./src/nz/ac/vuw/ecs/swen225/gp21/Persistency/levels/level" + level + ".xml"));
			XMLDecoder decoder = new XMLDecoder(fis);
			Game loaded = (Game) decoder.readObject();
			decoder.close();
			fis.close();
			
			System.out.println(loaded.toString());
			return loaded;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Saves the given game object to an XML file. 
	 * 
	 * With help from YouTube:
	 * https://www.youtube.com/watch?v=H-aTpt4NG-s
	 */
	public static void save(Game game) {		
		try {
			FileOutputStream fos = new FileOutputStream(new File("./src/nz/ac/vuw/ecs/swen225/gp21/Persistency/levels/saved.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(game);
			encoder.close();
			fos.close();
			
		} catch(IOException e) {
			System.out.println("Oopsie!");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) {
		Tile[][] board = new Tile[2][2];
		board[0][0] = new Free("", false);
		board[1][0] = new Free("", false);
		board[0][1] = new Free("", false);
		board[1][1] = new Free("", false);
		
		
		Game g = new Game(board, 60, 1, 1, 1);
		save(g);
	}
	
}
