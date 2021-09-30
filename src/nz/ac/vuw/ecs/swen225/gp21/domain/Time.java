package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Time is a tile that gives the user a time bonus
 * upon stepping on it.

 * @author Paul Ireland - 300475268
 *
 */
public class Time extends AbstractTile {

  int seconds;

  /**
   * Constructor for the Time tile.

   * @param sec the amount of seconds bonus
   */
  public Time(int sec) {
    seconds = sec;
  }

  /**
   * The amount of seconds this bonus is worth.

   * @return the amount of seconds of the bonus
   */
  public int getSeconds() {
    return seconds;
  }

  @Override
  public String toString() {
    return "t";
  }
}
