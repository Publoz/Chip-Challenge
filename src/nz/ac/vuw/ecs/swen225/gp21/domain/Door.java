package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * A door is a tile that requires a key to open.

 * @author Paul Ireland - 300475268
 *
 */
public class Door extends AbstractTile {

  private String colour;

  /**
   * Constructor for the door.

   * @param col the colour of the key needed
   */
  public Door(String col) {
    this.colour = col;
  }

  /**
   * Get the colour field.

   * @return the string representing the colour
   */
  public String getColour() {
    return colour;
  }

  @Override
  public boolean canMoveHere() {
    return false;
  }

  @Override
  public String toString() {
    return colour;
  }

}
