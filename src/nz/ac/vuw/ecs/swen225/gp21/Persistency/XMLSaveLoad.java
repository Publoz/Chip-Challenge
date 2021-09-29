package nz.ac.vuw.ecs.swen225.gp21.Persistency;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import nz.ac.vuw.ecs.swen225.gp21.domain.Chap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.net.URLClassLoader;

public class XMLSaveLoad {

	/**
	 * Parses an XML file with the given filename and returns it as a new Game
	 * object.
	 * 
	 * Help from https://mkyong.com/java/how-to-read-xml-file-in-java-jdom-example/
	 * 
	 * @param fileName In the format "name_of_file.xml"
	 * @return The constructed game object.
	 * @throws IOException If input file name is in the incorrect format.
	 */
	public static Game load(String fileName) throws IOException {

		if (!fileName.endsWith(".xml")) {
			throw new IOException("Missing or incorrect file format.");
		}

		Class<?> spiderClass = loadClass();
		String keys = "";

		try {

			// Required steps to load XML document.
			SAXBuilder sax = new SAXBuilder();
			Document doc = sax.build(new File("./src/nz/ac/vuw/ecs/swen225/gp21/Persistency/levels/" + fileName));

			// Get the "Game" element.
			Element root = doc.getRootElement();

			// Get the attributes from the "Game" element.
			int levelNumber = Integer.parseInt(root.getAttributeValue("levelNumber"));
			int totalTime = Integer.parseInt(root.getAttributeValue("totalTime"));
			int chapRow = Integer.parseInt(root.getAttributeValue("row"));
			int chapCol = Integer.parseInt(root.getAttributeValue("col"));

			// Get the parent element for all Tile elements.
			Element tiles = root.getChild("Tiles");
			keys = root.getAttributeValue("keys");
			// Calculate the rows and cols. We assume that the board is rectangular.
			int totalRows = tiles.getChildren().size();
			int totalCols = tiles.getChildren().get(0).getChildren().size();

			// Construct a 2D array using calculated dimensions.
			Tile[][] maze = new Tile[totalRows][totalCols];

			// Loop through tiles element and construct tile objects using its children.
			for (int row = 0; row < totalRows; row++) {
				Element currentItem = tiles.getChildren().get(row);
				for (int col = 0; col < totalCols; col++) {
					Element currentTile = currentItem.getChildren().get(col);

					// Find the currentTiles type and construct different subtypes of Tile depending
					// on that attribute.
					String type = currentTile.getName();
					if (type.equals("Free")) {
						String key = currentTile.getAttributeValue("key");
						boolean treasure = Boolean.parseBoolean(currentTile.getAttributeValue("treasure"));
						maze[row][col] = new Free(key, treasure);

						// Check to see if there is an actor on this tile:
						String actor = currentTile.getAttributeValue("actor");
						if (actor != null && actor.equals("Spider")) {
							Actor toAdd = null;
							try {
								// Load in the actor and add it to the tile.
								toAdd = (Actor) spiderClass.getDeclaredConstructor(Position.class)
										.newInstance(new Position(row, col));
								maze[row][col].addActor(toAdd);
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e) {
								throw new IOException(e);
							}
						}

					} else if (type.equals("Wall")) {
						maze[row][col] = new Wall();
					} else if (type.equals("Door")) {
						String colour = currentTile.getAttributeValue("colour");
						maze[row][col] = new Door(colour);
					} else if (type.equals("Exit")) {
						maze[row][col] = new Exit();
					} else if (type.equals("ExitLock")) {
						maze[row][col] = new ExitLock();
					} else if (type.equals("Info")) {
						String info = currentTile.getAttributeValue("info");
						maze[row][col] = new Info(info);
					} else if (type.equals("Time")) {
						int bonus = Integer.parseInt(currentTile.getAttributeValue("seconds"));
						maze[row][col] = new Time(bonus);
					} else if (type.equals("Acid")) {
						maze[row][col] = new Acid();
					}
				}
			}

			Game toReturn = new Game(maze, levelNumber, totalTime, chapRow, chapCol);

			// Add collected keys to game.
			if (keys != null) {
				for (int i = 0; i < keys.length(); i++) {
					toReturn.addKey("" + keys.charAt(i));
				}
			}

			return toReturn;

		} catch (IOException | JDOMException e) {
			System.out.println("Error loading file." + e);
		}

		// Could not load file. Return null.
		return null;
	}

	/**
	 * Saves the given game object to an XML file.
	 * 
	 * With help from:
	 * https://www.journaldev.com/1211/jdom-write-xml-file-example-object
	 * 
	 * @param game           The object to convert to XML.
	 * @param outputFileName The filename to save it to. (Should be in the format
	 *                       "file_name.xml")
	 * @throws IOException If input file name is in the incorrect format.
	 *
	 */
	public static void save(Game game, String outputFileName) throws IOException {

		if (!outputFileName.endsWith(".xml")) {
			throw new IOException("Missing or incorrect file format.");
		}

		Class<?> spiderClass = loadClass();

		Document doc = new Document();

		// Create our root element and save it to a variable.
		doc.setRootElement(new Element("Game"));
		Element root = doc.getRootElement();

		// Add the game parameters as attributes.
		root.setAttribute("levelNumber", "" + game.getLevel());
		root.setAttribute("totalTime", "" + game.timeLeft());
		root.setAttribute("row", "" + game.findChap().getRow());
		root.setAttribute("col", "" + game.findChap().getCol());
		
		//Add the collected keys as an attribute. 
		String keys = "";
		if (game.getKeys() != null) {
			for (int i = 0; i < game.getKeys().length; i++) {
				String key = game.getKeys()[i];
				if(key != null) {
					keys += key;
				}
			}
		}
		
		root.setAttribute("keys", keys);

		// Create a parent element which will contain all child Tiles.
		Element allTiles = new Element("Tiles");

		Tile[][] maze = game.getMaze();

		// Loop through all indices of the maze:
		for (int row = 0; row < maze.length; row++) {
			// Get the current row of tiles.
			Element items = new Element("Row");
			for (int col = 0; col < maze[row].length; col++) {

				Tile tileObject = maze[row][col];

				// Construct a tileElement to put all information from tileObject into.
				Element tileElement = new Element(tileObject.getClass().getSimpleName());

				// Save different attributes to tileElement depending on its type.
				if (tileObject instanceof Free) {

					tileElement.setAttribute("key", ((Free) tileObject).getKey());
					tileElement.setAttribute("treasure", "" + ((Free) tileObject).hasTreasure());
					// Sets actor attribute to "Spider" if there is one on the tile.
					Actor actorInTile = ((Free) tileObject).getActor();
					if (actorInTile != null
							&& actorInTile.getClass().getSimpleName().equals(spiderClass.getSimpleName())) {
						tileElement.setAttribute("actor", "Spider");
					} else {
						tileElement.setAttribute("actor", "");
					}
				} else if (tileObject instanceof Door) {
					tileElement.setAttribute("colour", ((Door) tileObject).getColour());
				} else if (tileObject instanceof Info) {
					tileElement.setAttribute("info", ((Info) tileObject).getInformation());
				} else if (tileObject instanceof Time) {
					tileElement.setAttribute("seconds", "" + ((Time) tileObject).getSeconds());
				}

				// We can now add tileElement as a child of items.
				items.getChildren().add(tileElement);
			}
			// The row is complete. We can now add it as a child of allTiles.
			allTiles.getChildren().add(items);
		}

		// We can now add the parent tile element as a child of root.
		root.getChildren().add(allTiles);

		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		try {
			xmlOutputter.output(doc,
					new FileOutputStream("./src/nz/ac/vuw/ecs/swen225/gp21/Persistency/levels/" + outputFileName));
		} catch (IOException e) {
			throw new IOException("Error saving XML: " + e);
		}
	}

	/**
	 * Loads in external actor from Level2.jar. Returns a Class object which can be
	 * instantiated.
	 * 
	 * @return Class object of external actor.
	 * @throws IOException If there are any issues loading the Jar file.
	 */
	public static Class loadClass() throws IOException {

		Class<?> clazz;

		// Load in the external spider class from the Jar file.

		try {
			URL url = new URL("jar:file:./src/nz/ac/vuw/ecs/swen225/gp21/Persistency/levels/Level2.jar!/");
			URLClassLoader ucl = new URLClassLoader(new URL[] { url });
			clazz = ucl.loadClass("Spider");

		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new IOException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IOException("External class not found." + e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IOException("IllegalArgumentException: " + e);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new IOException("SecurityException: " + e);
		}

		return clazz;
	}

}
