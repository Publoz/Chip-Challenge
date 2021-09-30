package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The free tile is the most important tile.
 * It can contain treasure and keys but most
 * importantly is where the actors are likely found

 * @author Paul Ireland - 300475268
 *
 */
public class Free extends AbstractTile {

  private String key = "";
  private boolean treasure = false;

  /**
   * Constructor if this free tile contains something.

   * @param key the colour of the key, made lowercase
   * @param treasure if this tile contains treasure
   */
  public Free(String key, boolean treasure) {
    this.key = key.toLowerCase();
    this.treasure = treasure;
  }

  /**
   * Default constructor for a regular tile.
   */
  public Free() {

  }

  /**
   * Returns whether this tile has a key.

   * @return a boolean if it contains a key
   */
  public boolean hasKey() {
    if (key.equals("")) {
      return false;
    } else {
      return true;
    }
  }

  public String getKey() {
    return key;
  }

  /**
   * Remove the key from this tile.
   */
  public void removeKey() {
    this.key = "";
  }

  @Override
  public void removeTreasure() {
    if (treasure) {
      treasure = false;
    } else {
      throw new IllegalStateException("This free tile doesn't have treasure");
    }
  }

  @Override
  public boolean hasTreasure() {
    return treasure;
  }

  @Override
  public boolean canMoveHere() {
    return super.canMoveHere();
  }

  @Override
  public String toString() {

    if (actor != null && actor.toString().equals("Chap")) {
      return "C";
    } else if (actor != null) {
      return String.valueOf(actor.toString().charAt(0));
    } else if (key.equals("") && treasure == false) {
      return "_";
    } else if (treasure == true) {
      return "T";
    } else {
      return key;
    }

  }

}
