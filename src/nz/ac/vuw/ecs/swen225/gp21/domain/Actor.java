package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.awt.image.BufferedImage;

/**
 * An actor is a character that can move around and be 
 * stored in Tiles, such as Chap and Spider.

 * @author Paul Ireland - 300475268
 *
 */
public interface Actor {

  /**
   * Allows the actor to move in a specific game and gives them access to all
   * information.

   * @param game the Game the actor is moving in
   */
  public void move(Game game);

  /**
   * Get the position of this actor.

   * @return the position of the actor
   */
  public Position getPos();

  /**
   * Sets the position of this actor.

   * @param pos the position to be set to
   */
  public void setPos(Position pos);

  /**
   * Get the image of this actor.

   * @return the BufferedImage of the actor
   */
  public BufferedImage getImage();

  /**
   * Returns whether this actor will kill chap on contact.

   * @return a boolean if actor is lethal
   */
  public boolean isDeadly();

}
