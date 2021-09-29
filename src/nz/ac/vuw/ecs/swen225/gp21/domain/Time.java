package nz.ac.vuw.ecs.swen225.gp21.domain;

public class Time extends AbstractTile{
	
	int seconds;
	
	public Time(int sec) {
		seconds = sec;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	
	@Override
	public String toString() {
		return "T";
	}
}
