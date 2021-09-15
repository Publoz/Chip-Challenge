package nz.ac.vuw.ecs.swen225.gp21.Persistency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import nz.ac.vuw.ecs.swen225.gp21.domain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XMLSaveLoad {

	public static final String LEVEL_ONE = "./src/nz/ac/vuw/ecs/swen225/gp21/Persistency/levels/level1.xml";

	/**
	 * Opens an XML file, converts it to a new Game object, and returns it.
	 * 
	 * With help from YouTube: https://www.youtube.com/watch?v=H-aTpt4NG-s
	 * 
	 * @param level Identifies which level to be loaded by the load method.
	 * @return loaded level.
	 * 
	 */
	public static Game load(int level) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new File(LEVEL_ONE));

			doc.getDocumentElement().normalize();

			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
			System.out.println("-----------");

			Element gameNode = (Element) doc.getElementsByTagName("Game").item(0);

			int levelNumber = Integer.parseInt(gameNode.getAttribute("levelNumber"));
			int totalTime = Integer.parseInt(gameNode.getAttribute("totalTime"));
			int row = Integer.parseInt(gameNode.getAttribute("row"));
			int col = Integer.parseInt(gameNode.getAttribute("col"));

			List<Tile> tileList = new ArrayList<Tile>();

			Element tilesNode = (Element) gameNode.getElementsByTagName("Tiles").item(0);
			System.out.println("TilesNode: " + tilesNode);
			NodeList allTiles = tilesNode.getChildNodes();
			System.out.println(allTiles.getLength());
			
			for(int x = 0; x < allTiles.getLength(); x++) {
				
				Element column =  allTiles.getElementsByTagName("Items").item(x);
				
				for(int y = 0; y < column.item(y)) {
					
				}
				

			}
			
			for (int i = 0; i < allTiles.getLength(); i++) {
				Node node = allTiles.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					if (element.getNodeName().equals("Free")) {
						String key = element.getAttribute("key");
						boolean treasure = Boolean.parseBoolean(element.getAttribute("treasure"));
						tileList.add(new Free(key, treasure));
					}
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	/**
	 * Saves the given game object to an XML file.
	 * 
	 * With help from YouTube: https://www.youtube.com/watch?v=H-aTpt4NG-s
	 */
	public static void save(Game game) {

	}

	public static void main(String args[]) {
		/**
		 * Tile[][] board = new Tile[2][2]; board[0][0] = new Free("", false);
		 * board[1][0] = new Free("", false); board[0][1] = new Free("", false);
		 * board[1][1] = new Free("", false);
		 * 
		 * Game g = new Game(board, 60, 1, 1, 1); save(g);
		 */

		load(1);
	}

}
