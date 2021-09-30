package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Acid Tile, kills chap on contact.

 * @author Paul Ireland - 300475268
 *
 */
public class Acid extends AbstractTile {

  @Override
  public void addActor(Actor actor) {
    throw new IllegalStateException("Cannot add actor to acid");
  }

  @Override
  public String toString() {
    return "A";
  }
}
