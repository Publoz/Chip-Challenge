package nz.ac.vuw.ecs.swen225.gp21.Persistency;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import nz.ac.vuw.ecs.swen225.gp21.domain.Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Wall;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;

public class XMLSaveLoad {

	/**
	 * Opens an XML file, converts it to a new Game object, and returns it.
	 * 
	 * With help from Mkyong:
	 * https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ With help
	 * from Baeldung: https://www.baeldung.com/java-xpath
	 * 
	 * @param level Identifies which level to be loaded by the load method.
	 * @return loaded level.
	 * 
	 */
	public static Game load(int level) {

		try {
			
			File toLoad = new File("./src/nz/ac/vuw/ecs/swen225/gp21/Persistency/levels/level" + level + ".xml");
			
			//Steps required to parse XML:
			FileInputStream fileIS = new FileInputStream(toLoad);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(fileIS);
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			
			//Get the root node (The Game node).
			Node gameNode = (Node) xPath.compile("/Game").evaluate(xmlDocument, XPathConstants.NODE);
			NamedNodeMap gameAttributes = gameNode.getAttributes();

			//Get attributes required for Game object constructor. 
			int levelNumber = Integer.parseInt(gameAttributes.getNamedItem("levelNumber").getTextContent());
			int totalTime = Integer.parseInt(gameAttributes.getNamedItem("totalTime").getTextContent());
			int chapRow = Integer.parseInt(gameAttributes.getNamedItem("row").getTextContent());
			int chapCol = Integer.parseInt(gameAttributes.getNamedItem("col").getTextContent());

			//Get the list of lists of tiles. 
			NodeList allTiles = (NodeList) xPath.compile("/Game/Tiles/Items").evaluate(xmlDocument, XPathConstants.NODESET);

			//Calculate the rows and columns needed in our 2D array of tiles. 
			int levelRows = allTiles.getLength();
			NodeList tempColumn  = (NodeList) xPath.compile("/Game/Tiles/Items[1]/Tile").evaluate(xmlDocument, XPathConstants.NODESET); 
			int levelCols = tempColumn.getLength();
			
			//Initialise empty tiles 2D array. 
			Tile[][] tiles = new Tile[levelRows][levelCols];

			
			//Loop through each column and row of the XML:
			for (int x = 0; x < levelRows; x++) {
				
				//Grab all of the tiles within column at position x:
				NodeList column = (NodeList) xPath.compile("/Game/Tiles/Items[" + (x + 1) + "]").evaluate(xmlDocument,
						XPathConstants.NODESET);
					
				for (int y = 0; y < levelCols; y++) {
					
					//Grab the tile at position (x, y):
					Node tile = (Node) xPath.compile("/Game/Tiles/Items[" + (x + 1) + "]/Tile[" + (y + 1) + "]")
							.evaluate(xmlDocument, XPathConstants.NODE);
					
					//Safety check, to ensure we don't have null pointer exceptions.
					if (tile != null) {
						
						//Grab the attributes from the tile node. These will be used for constructing Tile objects.
						NamedNodeMap tileAttributes = tile.getAttributes();
						String type = tileAttributes.getNamedItem("type").getTextContent();

						
						//Create Tile subclass depending on "type" attribute. 
						if (type.equals("Free")) {
							
							String key = tileAttributes.getNamedItem("key").getTextContent();
							boolean treasure = Boolean
									.parseBoolean(tileAttributes.getNamedItem("treasure").getTextContent());
							
							tiles[x][y] = new Free(key, treasure);
							System.out.println(tiles[x][y].getClass());
							
						} else if (type.equals("Wall")) {
							
							tiles[x][y] = new Wall();
							System.out.println(tiles[x][y].getClass());
							
						} else if (type.equals("Door")) {
							
							String colour = tileAttributes.getNamedItem("colour").getTextContent();
							tiles[x][y] = new Door(colour);
							System.out.println(tiles[x][y].getClass());
							
						}

					}

				}
				
			}
			
			return new Game(tiles, levelNumber, totalTime, chapRow, chapCol);

		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			System.out.println("Error loading level. Please ensure XML syntax is correct.");
		}
		//Load failed, return null. 
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

		System.out.println(load(1).toString());
	}

}
