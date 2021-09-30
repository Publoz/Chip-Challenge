package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The info tile contains tips for the user.

 * @author Paul Ireland - 300475268
 *
 */
public class Info extends AbstractTile {

  private String information;

  /**
   * Constructor for an info tile.

   * @param info the tip to embed
   */
  public Info(String info) {
    this.information = info;
  }

  /**
   * Get the information from the tile.

   * @return the string of the information
   */
  public String getInformation() {
    return information;
  }


  @Override
  public String toString() {
    return "I";
  }

}
