package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The tile that sits just before the exit.

 * @author Paul Ireland - 300475268
 *
 */
public class ExitLock extends AbstractTile {

  @Override
  public boolean canMoveHere() {
    return false;
  }

  @Override
  public String toString() {
    return "L";
  }

}
