package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.awt.image.BufferedImage;

public interface Actor {

	/**
	 * Allows the actor to move in a specific game
	 * and gives them access to all information
	 * 
	 * @param game the Game the actor is moving in
	 */
	public void move(Game game);
	
	public Position getPos();
	
	public void setPos(Position pos);
	
	public BufferedImage getImage();
	
}
