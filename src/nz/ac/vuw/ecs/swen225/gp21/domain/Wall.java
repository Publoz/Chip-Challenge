package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The Wall tile is a tile that no actor can walk on.

 * @author Paul Ireland - 300475268
 *
 */
public class Wall extends AbstractTile {

  @Override
  public boolean canMoveHere() {
    return false;
  }

  @Override
  public String toString() {
    return "W";
  }

}
