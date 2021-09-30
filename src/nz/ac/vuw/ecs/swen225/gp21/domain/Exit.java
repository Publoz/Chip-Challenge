package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The exit tile is the one chap finishes a level on.

 * @author Paul Ireland - 300475268
 *
 */
public class Exit extends AbstractTile {

  @Override
  public boolean canMoveHere() {
    return true;
  }

  @Override
  public String toString() {
    return "E";
  }

}
